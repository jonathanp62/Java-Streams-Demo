package net.jmp.demo.streams.collectors;

/*
 * (#)ToDequeCollector.java 0.7.0   09/07/2024
 * (#)ToDequeCollector.java 0.4.0   09/04/2024
 *
 * @author   Jonathan Parker
 * @version  0.7.0
 * @since    0.4.0
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

import java.util.*;

import java.util.function.*;

import java.util.stream.Collector;

/**
 * A collector that accumulates the input elements into a new Deque.
 *
 * @param   <T> The type being collected
 */
public final class ToDequeCollector<T> implements Collector<T, List<T>, Deque<T>> {
    /**
     * The default constructor.
     */
    public ToDequeCollector() {
        super();
    }

    /**
     * The supplier.
     *
     * @return  java.util.function.Supplier&lt;java.util.List&lt;T&gt;&gt;
     */
    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    /**
     * The accumulator.
     *
     * @return  java.util.function.BiConsumer&lt;java.util.List&lt;T&gt;&gt;
     */
    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    /**
     * The combiner.
     *
     * @return  java.util.function.BinaryOperator&lt;java.util.List&lt;T&gt;&gt;
     */
    @Override
    public BinaryOperator<List<T>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);

            return list1;
        };
    }

    /**
     * The finisher.
     *
     * @return  java.util.function.Function&lt;java.util.List&lt;T&gt;&gt;, &lt;java.util.Deque&lt;T&gt;&gt;
     */
    @Override
    public Function<List<T>, Deque<T>> finisher() {
        return list -> {
            return new ArrayDeque<>(list);
        };
    }

    /**
     * Return the collector's characteristics.
     *
     * @return  java.util.Set&lt;java.util.stream.Characteristics&gt;
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.UNORDERED));
    }
}
