package net.jmp.demo.streams.util;

/*
 * (#)TestDemoUtils.java    0.3.0   08/29/2024
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

import static net.jmp.demo.streams.util.DemoUtils.*;

import static org.junit.Assert.*;

import net.jmp.demo.streams.records.Dish;

import org.junit.Test;

public final class TestDemoUtils {
    @Test
    public void testListOfDishes() throws Exception {
        final List<Dish> dishes = listOfDishes();

        assertNotNull(dishes);
        assertEquals(9, dishes.size());
        assertEquals("pork", dishes.get(0).name());
        assertEquals("beef", dishes.get(1).name());
        assertEquals("chicken", dishes.get(2).name());
        assertEquals("french fries", dishes.get(3).name());
        assertEquals("rice", dishes.get(4).name());
        assertEquals("seasonal fruit", dishes.get(5).name());
        assertEquals("pizza", dishes.get(6).name());
        assertEquals("prawns", dishes.get(7).name());
        assertEquals("salmon", dishes.get(8).name());
    }

    @Test
    public void testStreamOfDishes() throws Exception {
        final List<Dish> dishes = streamOfDishes().toList();

        assertNotNull(dishes);
        assertEquals(9, dishes.size());
        assertEquals("pork", dishes.get(0).name());
        assertEquals("beef", dishes.get(1).name());
        assertEquals("chicken", dishes.get(2).name());
        assertEquals("french fries", dishes.get(3).name());
        assertEquals("rice", dishes.get(4).name());
        assertEquals("seasonal fruit", dishes.get(5).name());
        assertEquals("pizza", dishes.get(6).name());
        assertEquals("prawns", dishes.get(7).name());
        assertEquals("salmon", dishes.get(8).name());
    }

    @Test
    public void testToTypedArray() {
        final Object[] array1 = new Object[] { "a", "b", "c" };
        final String[] strings = toTypedArray(array1, String.class);

        assertNotNull(strings);
        assertEquals(3, strings.length);
        assertEquals("a", strings[0]);
        assertEquals("b", strings[1]);
        assertEquals("c", strings[2]);

        final Object[] array2 = new Object[] { 1, 2, 3 };
        final Integer[] integers = toTypedArray(array2, Integer.class);

        assertNotNull(integers);
        assertEquals(3, integers.length);
        assertEquals(1, (long) integers[0]);
        assertEquals(2, (long) integers[1]);
        assertEquals(3, (long) integers[2]);
    }
}
