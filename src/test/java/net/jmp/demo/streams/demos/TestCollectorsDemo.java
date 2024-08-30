package net.jmp.demo.streams.demos;

/*
 * (#)TestCollectorsDemo.java   0.4.0   08/30/2024
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

import java.util.stream.Collectors;

import static net.jmp.demo.streams.testutil.TestUtils.castToType;

import static org.junit.Assert.*;

import org.junit.Test;

public final class TestCollectorsDemo {
    @Test
    public void testToList() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("toList");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final ArrayList<?> arrayList = castToType(ArrayList.class, o);
        final List<String> dishNames = arrayListToTypedList(arrayList, String.class);

        assertNotNull(dishNames);
        assertEquals(9, dishNames.size());
        assertTrue(dishNames.contains("List: pork"));
        assertTrue(dishNames.contains("List: beef"));
        assertTrue(dishNames.contains("List: chicken"));
        assertTrue(dishNames.contains("List: french fries"));
        assertTrue(dishNames.contains("List: rice"));
        assertTrue(dishNames.contains("List: seasonal fruit"));
        assertTrue(dishNames.contains("List: pizza"));
        assertTrue(dishNames.contains("List: prawns"));
        assertTrue(dishNames.contains("List: salmon"));
    }

    @Test
    public void testToSet() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("toSet");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final HashSet<?> hashSet = castToType(HashSet.class, o);
        final Set<String> dishNames = hashSetToTypedSet(hashSet, String.class);

        assertNotNull(dishNames);
        assertEquals(9, dishNames.size());
        assertTrue(dishNames.contains("Set : pork"));
        assertTrue(dishNames.contains("Set : beef"));
        assertTrue(dishNames.contains("Set : chicken"));
        assertTrue(dishNames.contains("Set : french fries"));
        assertTrue(dishNames.contains("Set : rice"));
        assertTrue(dishNames.contains("Set : seasonal fruit"));
        assertTrue(dishNames.contains("Set : pizza"));
        assertTrue(dishNames.contains("Set : prawns"));
        assertTrue(dishNames.contains("Set : salmon"));
    }

    /**
     * Create a list of elements of type T from
     * an ArrayList of wildcard-typed objects.
     *
     * @param   <T>         The type of element in the list
     * @param   arrayList   java.util.ArrayList&lt;?&gt;
     * @param   clazz       The class of type T
     * @return              java.util.List&lt;T&gt;
     */
    private static <T> List<T> arrayListToTypedList(final ArrayList<?> arrayList, final Class<T> clazz) {
        Objects.requireNonNull(arrayList, () -> "ArrayList<?> arrayList is null");
        Objects.requireNonNull(clazz, () -> "Class<T> clazz");

        return arrayList
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }

    /**
     * Create a set of elements of type T from
     * a HashSet of wildcard-typed objects.
     *
     * @param   <T>         The type of element in the list
     * @param   hashSet     java.util.HashSet&lt;?&gt;
     * @param   clazz       The class of type T
     * @return              java.util.Set&lt;T&gt;
     */
    private static <T> Set<T> hashSetToTypedSet(final HashSet<?> hashSet, final Class<T> clazz) {
        Objects.requireNonNull(hashSet, () -> "HashSet<?> hashSet is null");
        Objects.requireNonNull(clazz, () -> "Class<T> clazz");

        return hashSet
                .stream()
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .collect(Collectors.toSet());
    }
}
