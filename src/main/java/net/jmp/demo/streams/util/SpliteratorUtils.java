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

import java.util.Spliterator;
import java.util.Stack;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import java.util.function.Consumer;

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

    /** A stack of tasks to wait on once all splitting has completed. */
    private final Stack<ForkJoinTask<?>> tasks = new Stack<>();

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
     * Split and consume.
     */
    public void splitAndConsume() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("estimateSize: {}", this.spliterator.estimateSize());
            this.logger.debug("parallelism: {}", ForkJoinPool.getCommonPoolParallelism());
            this.logger.debug("batchSize: {}", batchSize);
            this.logger.debug("Begin splitting and consuming");
        }

        try (final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool()) {
            this.recursivelySplitAndConsume(this.spliterator, forkJoinPool);
        }

        this.logger.debug("End splitting and consuming");
        this.logger.debug("Begin waiting for tasks to finish");

        while (!this.tasks.empty()) {
            this.tasks.pop().join();
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
     */
    private void recursivelySplitAndConsume(final Spliterator<T> spliterator, final ForkJoinPool forkJoinPool) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(spliterator, forkJoinPool));
        }

        Spliterator<T> newSplit;

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("estimateSize: {}", spliterator.estimateSize());
        }

        while (true) {
            if (spliterator.estimateSize() > this.batchSize &&
                    (newSplit = spliterator.trySplit()) != null) {
                this.recursivelySplitAndConsume(newSplit, forkJoinPool);
            }

            break;
        }

        // Save the task to wait on later

        this.tasks.push(forkJoinPool.submit(() -> spliterator.forEachRemaining(this.action)));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
