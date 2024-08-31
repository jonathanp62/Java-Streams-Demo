package net.jmp.demo.streams.testutil;

/*
 * (#)TestUtils.java    0.4.0   08/30/2024
 * (#)TestUtils.java    0.3.0   08/29/2024
 *
 * @author   Jonathan Parker
 * @version  0.4.0
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

import java.util.*;

import java.util.stream.Collectors;
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
        Objects.requireNonNull(t, () -> "Class<T> t is null");
        Objects.requireNonNull(object, () -> "Object object is null");

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
    public static <T> List<T> streamToTypedList(final Stream<?> stream, final Class<T> clazz) {
        Objects.requireNonNull(stream, () -> "Stream<?> stream is null");
        Objects.requireNonNull(clazz, () -> "Class<T> clazz");

        return stream
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    /**
     * Create a list of elements of type T from
     * a List of wildcard-typed objects.
     *
     * @param   <T>         The type of element in the list
     * @param   list        java.util.List&lt;?&gt;
     * @param   clazz       The class of type T
     * @return              java.util.List&lt;T&gt;
     */
    public static <T> List<T> listToTypedList(final List<?> list, final Class<T> clazz) {
        Objects.requireNonNull(list, () -> "List<?> list is null");
        Objects.requireNonNull(clazz, () -> "Class<T> clazz");

        return list
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    /**
     * Create a map of elements of types K and V
     * from a Map of wildcard-typed objects.
     *
     * @param   <K>         The type of key element in the map
     * @param   <V>         The type of value element in the map
     * @param   map         java.util.Map&lt;?, ?&gt;
     * @param   keyClazz    The class of key type K
     * @param   valueClazz  The class of value type V
     * @return              java.util.Map&lt;K, V&gt;
     */
    public static <K, V> Map<K, V> mapToTypedMap(final Map<?, ?> map, final Class<K> keyClazz, final Class<V> valueClazz) {
        Objects.requireNonNull(map, () -> "Map<?, ?> map is null");
        Objects.requireNonNull(keyClazz, () -> "Class<T> keyClazz");
        Objects.requireNonNull(valueClazz, () -> "Class<T> valueClazz");

        final Map<K, V> typedMap = new HashMap<>();

        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            typedMap.put(keyClazz.cast(entry.getKey()), valueClazz.cast(entry.getValue()));
        }

        return typedMap;
    }

    /**
     * Create a set of elements of type T from
     * a Set of wildcard-typed objects.
     *
     * @param   <T>         The type of element in the list
     * @param   set         java.util.Set&lt;?&gt;
     * @param   clazz       The class of type T
     * @return              java.util.Set&lt;T&gt;
     */
    public static <T> Set<T> setToTypedSet(final Set<?> set, final Class<T> clazz) {
        Objects.requireNonNull(set, () -> "Set<?> set is null");
        Objects.requireNonNull(clazz, () -> "Class<T> clazz");

        return set
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toSet());
    }
}
