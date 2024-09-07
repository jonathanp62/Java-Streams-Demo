package net.jmp.demo.streams.util;

/*
 * (#)CollectorsFactory.java    0.7.0   09/07/2024
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

import java.util.function.Predicate;

import net.jmp.demo.streams.collectors.*;

/**
 * A factory class for collectors.
 */
public final class CollectorsFactory {
    /**
     * The default constructor.
     */
    private CollectorsFactory() {
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
     * Return an instance of the dropping-while collector.
     *
     * @param   <T>         The type being collected
     * @param   predicate   java.util.function.Predicate&lt;T&gt;
     * @return              net.jmp.demo.streams.collectors.DroppingWhileCollector
     */
    public static <T> DroppingWhileCollector<T> droppingWhile(final Predicate<? super T> predicate) {
        return new DroppingWhileCollector<>(predicate);
    }

    /**
     * Return an instance of the limiting collector.
     *
     * @param   <T>     The type being collected
     * @param   limit   long
     * @return          net.jmp.demo.streams.collectors.LimitingCollector
     */
    public static <T> LimitingCollector<T> limiting(final long limit) {
        return new LimitingCollector<>(limit);
    }

    /**
     * Return an instance of the skipping collector.
     *
     * @param   <T>     The type being collected
     * @param   skip    long
     * @return          net.jmp.demo.streams.collectors.SkippingCollector
     */
    public static <T> SkippingCollector<T> skipping(final long skip) {
        return new SkippingCollector<>(skip);
    }

    /**
     * Return an instance of the taking-while collector.
     *
     * @param   <T>         The type being collected
     * @param   predicate   java.util.function.Predicate&lt;T&gt;
     * @return              net.jmp.demo.streams.collectors.TakingWhileCollector
     */
    public static <T> TakingWhileCollector<T> takingWhile(final Predicate<? super T> predicate) {
        return new TakingWhileCollector<>(predicate);
    }

    /**
     * Return an instance of the toDeque collector.
     *
     * @param   <T>     The type being collected
     * @return          net.jmp.demo.streams.collectors.ToDequeCollector
     */
    public static <T> ToDequeCollector<T> toDeque() {
        return new ToDequeCollector<>();
    }
}
