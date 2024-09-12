package net.jmp.demo.streams.spliterators;

/*
 * (#)ListSpliterator.java  0.9.0   09/11/2024
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

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;

import java.util.function.Consumer;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A list spliterator.
 *
 * @param   <T> The type of element
 */

// @todo This class should extends CustomSpliterator

public final class ListSpliterator<T> implements Spliterator<T> {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** The list of elements. */
    private final List<T> list;

    /** The current index. */
    private int currentIndex;

    /** The count. */
    private int count;

    /**
     * The constructor.
     *
     * @param   list    java.util.List&lt;T&gt;
     */
    public ListSpliterator(final List<T> list) {
        super();

        this.list = Objects.requireNonNull(list);
        this.currentIndex = 0;

        this.logger.debug("Initial list size: {}", list.size());
    }

    /**
     * If a remaining element exists: performs the given action on it,
     * returning true; else returns false.
     *
     * @param   action  java.util.function.Consumer&lt;? super T&gt;
     * @return          boolean
     */
    @Override
    public boolean tryAdvance(final Consumer<? super T> action) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(action));
        }

        final String threadName = this.getThreadName();

        boolean result = false;

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("{} currentIndex: {}", threadName, this.currentIndex);
            this.logger.debug("{} listSize: {}", threadName, this.list.size());
        }

        if (this.currentIndex < this.list.size()) {
            final T item = this.list.get(this.currentIndex);

            this.logger.debug("{} value: {}", threadName, item);

            action.accept(item);

            ++this.count;
            ++this.currentIndex;

            this.logger.debug("{} currentIndex: {}", threadName, this.currentIndex);

            result = true;
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * If this spliterator can be partitioned, returns a Spliterator
     * covering elements, that will, upon return from this method,
     * not be covered by this Spliterator.
     *
     * @return  net.jmp.demo.streams.spliterators.ListSpliterator&lt;T&gt;
     */
    @Override
    public ListSpliterator<T> trySplit() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String threadName = this.getThreadName();
        final int currentSize = this.list.size() - this.currentIndex;

        this.logger.debug("{} currentSize: {}", threadName, currentSize);

        if (currentSize < 2) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(exitWith(null));
            }

            return null;
        }

        this.logger.debug("{} currentIndex: {}", threadName, this.currentIndex);

        final int splitIndex = this.currentIndex + currentSize / 2;

        this.logger.debug("{} splitIndex: {}", threadName, splitIndex);

        final ListSpliterator<T> spliterator = new ListSpliterator<>(this.list.subList(this.currentIndex, splitIndex));

        this.currentIndex = splitIndex;

        this.logger.debug("{} currentIndex: {}", threadName, this.currentIndex);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(spliterator));
        }

        return spliterator;
    }

    /**
     * Returns an estimate of the number of elements that would be encountered
     * by a forEachRemaining(java.util.function.Consumer<? super T>) traversal,
     * or returns Long.MAX_VALUE if infinite, unknown, or too expensive to compute.
     *
     * @return  long
     */
    @Override
    public long estimateSize() {
        return (this.list.size() - this.currentIndex);
    }

    /**
     * Returns a set of characteristics of this Spliterator and its elements.
     *
     * @return  int
     */
    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL;
    }

    /**
     * Return the count.
     *
     * @return  int
     */
    public int getCount() {
        return this.count;
    }

    /**
     * Return a bracketed thread name.
     *
     * @return  java.lang.String
     */
    private String getThreadName() {
        return String.format("[%s]",Thread.currentThread().getName());
    }
}
