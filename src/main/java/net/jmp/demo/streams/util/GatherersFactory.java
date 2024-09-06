package net.jmp.demo.streams.util;

/*
 * (#)DemoGatherers.java    0.7.0   09/06/2024
 *
 * @author   Jonathan Parker
 * @version  0.7.0
 * @since    0.7.0
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

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import net.jmp.demo.streams.gatherers.*;

/**
 * A factory class for gatherers.
 */
public final class GatherersFactory {
    /**
     * The default constructor.
     */
    private GatherersFactory() {
        super();
    }

    /**
     * A distinct-by gatherer.
     *
     * @param   selector    java.util.function.Function&lt;T, A&gt;
     * @return              net.jmp.demo.java22.gatherers.DistinctByGatherer&lt;T, A&gt;
     * @param   <T>         The type of input elements to the gathering operation
     * @param   <A>         The potentially mutable state type of the gathering operation
     */
    public static <T, A> DistinctByGatherer<T, A> distinctBy(final Function<T, A> selector) {
        return new DistinctByGatherer<>(selector);
    }

    /**
     * A reduce-by gatherer.
     *
     * @param   selector    java.util.function.Function&lt;T, A&gt;
     * @param   reducer     java.util.function.BiFunction&lt;T, T, T&gt;
     * @return              net.jmp.demo.java22.gatherers.ReduceByGatherer&lt;T, A&gt;
     * @param   <T>         The type of input elements to the gathering operation
     * @param   <A>         The potentially mutable state type of the gathering operation
     */
    public static <T, A> ReduceByGatherer<T, A> reduceBy(final Function<T, A> selector,
                                                         final BiFunction<T, T, T> reducer) {
        return new ReduceByGatherer<>(selector, reducer);
    }

    /**
     * A max-by gatherer.
     *
     * @param   selector    java.util.function.Function&lt;T, C&gt;
     * @return              net.jmp.demo.java22.gatherers.MaxByGatherer&lt;T, C&gt;
     * @param   <T>         The type of input elements to the gathering operation
     * @param   <C>         A type that extends Comparable; T must extend Comparable
     */
    public static <T, C extends Comparable<C>> MaxByGatherer<T, C> maxBy(final Function<T, C> selector) {
        return new MaxByGatherer<>(selector);
    }

    /**
     * A min-by gatherer.
     *
     * @param   selector    java.util.function.Function&lt;T, C&gt;
     * @return              net.jmp.demo.java22.gatherers.MinByGatherer&lt;T, C&gt;
     * @param   <T>         The type of input elements to the gathering operation
     * @param   <C>         A type that extends Comparable; T must extend Comparable
     */
    public static <T, C extends Comparable<C>> MinByGatherer<T, C> minBy(final Function<T, C> selector) {
        return new MinByGatherer<>(selector);
    }

    /**
     * A map not null gatherer.
     *
     * @param   mapper  java.util.function.Function&lt;T, R&gt;
     * @return          net.jmp.demo.java22.gatherers.MapNotNullGatherer&lt;T, R&gt;
     * @param   <T>     The type of input elements to the gathering operation
     * @param   <R>     The type of output elements from the gatherer operation
     */
    public static <T, R> MapNotNullGatherer<T, R> mapNotNull(final Function<T, R> mapper) {
        return new MapNotNullGatherer<>(mapper);
    }

    /**
     * A find first gatherer.
     *
     * @param   predicate   java.util.function.Predicate&lt;T&gt;
     * @return              net.jmp.demo.java22.gatherers.FindFirstGatherer&lt;T&gt;
     * @param   <T>         The type of input elements to the gathering operation
     */
    public static <T> FindFirstGatherer<T> findFirst(final Predicate<T> predicate) {
        return new FindFirstGatherer<>(predicate);
    }

    /**
     * A find last gatherer.
     *
     * @param   predicate   java.util.function.Predicate&lt;T&gt;
     * @return              net.jmp.demo.java22.gatherers.FindFirstGatherer&lt;T&gt;
     * @param   <T>         The type of input elements to the gathering operation
     */
    public static <T> FindLastGatherer<T> findLast(final Predicate<T> predicate) {
        return new FindLastGatherer<>(predicate);
    }
}
