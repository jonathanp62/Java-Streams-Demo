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

/**
 * A list spliterator.
 *
 * @param   <T> The type of element
 */
public final class ListSpliterator<T> implements Spliterator<T> {
    /** The list of elements. */
    private final List<T> list;

    /** The current index. */
    private int currentIndex;

    /**
     * The constructor.
     *
     * @param   list    java.util.List&lt;T&gt;
     */
    public ListSpliterator(final List<T> list) {
        this.list = Objects.requireNonNull(list);
        this.currentIndex = 0;
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
        return false;
    }

    /**
     * If this spliterator can be partitioned, returns a Spliterator
     * covering elements, that will, upon return from this method,
     * not be covered by this Spliterator.
     *
     * @return  java.util.Spliterator&lt;T&gt;
     */
    @Override
    public Spliterator<T> trySplit() {
        return null;
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
        return 0;
    }

    /**
     * Returns a set of characteristics of this Spliterator and its elements.
     *
     * @return  int
     */
    @Override
    public int characteristics() {
        return 0;
    }
}
