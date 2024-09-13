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

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A utility class for spliterators.
 */
public final class SpliteratorUtils {
    /**
     * The default constructor.
     */
    private SpliteratorUtils() {
        super();
    }

    /**
     * Split the work evenly for distribution across the threads.
     *
     * @param   <T>         The type of element in the spliterator
     * @param   spliterator java.util.Spliterator&lt;T&gt;
     * @param   action      java.util.function.Consumer&lt;? super T&gt;
     * @param   supplier    java.util.function.Supplier&lt;? extends T&gt;
     * @return              T
     */
    public static <T> T splitAndConsumeEvenly(final Spliterator<T> spliterator,
                                                 final Consumer<? super T> action,
                                                 final Supplier<? extends T> supplier) {
        final SplitAndConsumeUtils<T> splitAndConsume = new SplitAndConsumeUtils<>(spliterator, action);

        return splitAndConsume.splitAndConsumeEvenly(supplier);
    }

    /**
     * Split the work unevenly (halving) for
     * distribution across the threads.
     *
     * @param   <T>         The type of element in the spliterator
     * @param   spliterator java.util.Spliterator&lt;T&gt;
     * @param   action      java.util.function.Consumer&lt;? super T&gt;
     */
    public static <T> void splitAndConsumeUnevenly(final Spliterator<T> spliterator, final Consumer<? super T> action) {
        final SplitAndConsumeUtils<T> splitAndConsume = new SplitAndConsumeUtils<>(spliterator, action);

        splitAndConsume.splitAndConsumeUnevenly();
    }
}
