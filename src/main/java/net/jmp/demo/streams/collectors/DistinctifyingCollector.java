package net.jmp.demo.streams.collectors;

/*
 * (#)DistinctifyingCollector.java  0.4.0   09/04/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
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
 * An implementation of the stream
 * distinct as a collector.
 *
 * @param   <T> The type being collected
 */
public final class DistinctifyingCollector<T> implements Collector<T, Set<T>, Set<T>> {
    /**
     * The default constructor.
     */
    public DistinctifyingCollector() {
        super();
    }

    /**
     * Return an instance of the distinctifying collector.
     *
     * @param   <T>     The type being collected
     * @return          net.jmp.demo.streams.collectors.DistinctifyingCollector
     */
    public static <T> DistinctifyingCollector<T> distinctifying() {
        return new DistinctifyingCollector<>();
    }

    /**
     * The supplier.
     *
     * @return  java.util.function.Supplier&lt;java.util.Set&lt;T&gt;&gt;
     */
    @Override
    public Supplier<Set<T>> supplier() {
        return HashSet::new;
    }

    /**
     * The accumulator.
     *
     * @return  java.util.function.BiConsumer&lt;java.util.Set&lt;T&gt;&gt;
     */
    @Override
    public BiConsumer<Set<T>, T> accumulator() {
        return Set::add;
    }

    /**
     * The combiner.
     *
     * @return  java.util.function.BinaryOperator&lt;java.util.Set&lt;T&gt;&gt;
     */
    @Override
    public BinaryOperator<Set<T>> combiner() {
        return (set1, set2) -> {
            set1.addAll(set2);

            return set1;
        };
    }

    /**
     * The finisher.
     *
     * @return  java.util.function.Function&lt;java.util.Set&lt;T&gt;&gt;, &lt;java.util.Set&lt;T&gt;&gt;
     */
    @Override
    public Function<Set<T>, Set<T>> finisher() {
        return Function.identity();
    }

    /**
     * Return the collector's characteristics.
     *
     * @return  java.util.Set&lt;java.util.stream.Characteristics&gt;
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
