package net.jmp.demo.streams.testutil;

/*
 * (#)TestUtils.java    0.3.0   08/29/2024
 *
 * @author   Jonathan Parker
 * @version  0.3.0
 * @since    0.3.0
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

import java.util.stream.Stream;

public final class TestUtils {
    /**
     * The default constructor.
     */
    private TestUtils() {
        super();
    }

    /**
     * Cast object to an instance of type T.
     *
     * @param   <T>     The type of instance to cast to
     * @param   t       The class of type T
     * @param   object  java.lang.Object
     * @return          T
     */
    public static <T> T castToType(final Class<T> t, final Object object) {
        return t.cast(object);
    }

    /**
     * Create a list of elements of type T from
     * a stream of wildcard-typed objects.
     *
     * @param   <T>     The type of element in the list
     * @param   stream  java.util.stream.Stream&lt;?&gt;
     * @param   clazz   The class of type T
     * @return          java.util.List&lt;T&gt;
     */
    public static <T> List<T> toTypedList(final Stream<?> stream, final Class<T> clazz) {
        return stream
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }
}
