package net.jmp.demo.streams.util;

/*
 * (#)SpliteratorUtils.java 0.9.0   09/12/2024
 *
 * @author   Jonathan Parker
 * @version  0.9.0
 * @since    0.9.0
 *
 * MIT License
 *
 * Copyright (c) 2024 Jonathan M. Parker
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Spliterator;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import java.util.concurrent.atomic.AtomicInteger;

import java.util.function.Consumer;

import net.jmp.demo.streams.spliterators.AdvanceCounter;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class for spliterators.
 *
 * @param   <T> The type of element associated with the spliterator
 */
public final class SpliteratorUtils<T> {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** The spliterator. */
    private final Spliterator<T> spliterator;

    /** The action to perform on elements. */
    private final Consumer<? super T> action;

    /** The batch size. */
    private final long batchSize;

    /** A stack of task/spliterator tuples to wait on once all splitting has completed. */
    private final Deque<TaskAndSpliterator<T>> tasksAndSpliterators = new ArrayDeque<>();

    /**
     * The constructor.
     *
     * @param   spliterator java.util.Spliterator&lt;T&gt;
     * @param   action      java.util.function.COnsumer&lt;? super T&gt;
     */
    public SpliteratorUtils(final Spliterator<T> spliterator, final Consumer<? super T> action) {
        super();

        this.spliterator = spliterator;
        this.action = action;

        this.batchSize = this.spliterator.estimateSize() / (ForkJoinPool.getCommonPoolParallelism());
    }

    /**
     * Split and consume. This technique evenly
     * distributes the work across the threads.
     */
    public void splitAndConsumeEvenly() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("estimateSize: {}", this.spliterator.estimateSize());
            this.logger.debug("parallelism: {}", ForkJoinPool.getCommonPoolParallelism());
            this.logger.debug("batchSize: {}", this.batchSize);
            this.logger.debug("Begin splitting and consuming");
        }

        try (final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            final Deque<Spliterator<T>> spliterators = new ArrayDeque<>();

            spliterators.add(this.spliterator);

            int totalSplits = 0;

            while (!spliterators.isEmpty()) {
                Spliterator<T> currentSpliterator = spliterators.pop();
                Spliterator<T> newSpliterator;

                while (currentSpliterator.estimateSize() > this.batchSize &&
                        (newSpliterator = currentSpliterator.trySplit()) != null) {

                    spliterators.push(currentSpliterator);
                    currentSpliterator = newSpliterator;
                }

                totalSplits++;

                final var finalSpliterator = currentSpliterator;

                final TaskAndSpliterator<T> taskAndSpliterator = new TaskAndSpliterator<>(
                        forkJoinPool.submit(() -> finalSpliterator.forEachRemaining(this.action)),
                        finalSpliterator
                );

                this.tasksAndSpliterators.push(taskAndSpliterator);
            }

            this.logger.debug("Total splits: {}", totalSplits);
        }

        this.logger.debug("End splitting and consuming");
        this.logger.debug("Begin waiting for tasks to finish");

        while (!this.tasksAndSpliterators.isEmpty()) {
            final TaskAndSpliterator<T> taskAndSpliterator = this.tasksAndSpliterators.pop();

            taskAndSpliterator.task.join();

            final long count = ((AdvanceCounter) taskAndSpliterator.spliterator).getCount();

            this.logger.debug("Count: {}", count);
        }

        this.logger.debug("End waiting for tasks to finish");

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Split and consume. This technique splits
     * the work by half during each split.
     */
    public void splitAndConsumeUnevenly() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final AtomicInteger totalSplits = new AtomicInteger(0);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("estimateSize: {}", this.spliterator.estimateSize());
            this.logger.debug("parallelism: {}", ForkJoinPool.getCommonPoolParallelism());
            this.logger.debug("batchSize: {}", this.batchSize);
            this.logger.debug("Begin splitting and consuming");
        }

        try (final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            this.recursivelySplitAndConsumeUnevenly(this.spliterator, forkJoinPool, totalSplits);
        }

        this.logger.debug("Total splits: {}", totalSplits.get());

        this.logger.debug("End splitting and consuming");
        this.logger.debug("Begin waiting for tasks to finish");

        while (!this.tasksAndSpliterators.isEmpty()) {
            final TaskAndSpliterator<T> taskAndSpliterator = this.tasksAndSpliterators.pop();

            taskAndSpliterator.task.join();

            final long count = ((AdvanceCounter) taskAndSpliterator.spliterator).getCount();

            this.logger.debug("Count: {}", count);
        }

        this.logger.debug("End waiting for tasks to finish");

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Recursively split the iterator consuming
     * elements on the stream that are remaining.
     *
     * @param   spliterator     java.util.Spliterator&lt;T&gt;
     * @param   forkJoinPool    java.util.concurrent.ForkJoinPool
     * @param   totalSplits     java.util.concurrent.atomic.AtomicInteger
     */
    private void recursivelySplitAndConsumeUnevenly(final Spliterator<T> spliterator,
                                                    final ForkJoinPool forkJoinPool,
                                                    final AtomicInteger totalSplits) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(spliterator, forkJoinPool, totalSplits));
        }

        Spliterator<T> newSpliterator;

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("estimateSize: {}", spliterator.estimateSize());
        }

        while (true) {
            if (spliterator.estimateSize() > this.batchSize &&
                    (newSpliterator = spliterator.trySplit()) != null) {
                this.recursivelySplitAndConsumeUnevenly(newSpliterator, forkJoinPool, totalSplits);
            }

            break;
        }

        totalSplits.incrementAndGet();

        // Save the task to wait on later

        final TaskAndSpliterator<T> taskAndSpliterator = new TaskAndSpliterator<>(
                forkJoinPool.submit(() -> spliterator.forEachRemaining(this.action)),
                spliterator
        );

        this.tasksAndSpliterators.push(taskAndSpliterator);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A record containing the submitted fork-join task
     * and its corresponding spliterator.
     *
     * @param   <T>
     * @param   task        java.util.concurrent.ForkJoinTask&lt;?&gt;
     * @param   spliterator java.util.Spliterator&lt;T&gt;
     */
    record TaskAndSpliterator<T>(
            ForkJoinTask<?> task,
            Spliterator<T> spliterator
    ) {
    }
}
