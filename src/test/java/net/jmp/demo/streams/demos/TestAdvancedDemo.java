package net.jmp.demo.streams.demos;

/*
 * (#)TestAdvancedDemo.java 0.3.0   08/29/2024
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

import net.jmp.demo.streams.records.Dish;

import static net.jmp.demo.streams.testutil.TestUtils.castToType;
import static net.jmp.demo.streams.testutil.TestUtils.toTypedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestAdvancedDemo {
    @Test
    public void testDropWhile() throws Exception {
        final var demo = new AdvancedDemo();
        final var method = AdvancedDemo.class.getDeclaredMethod("dropWhile");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Integer> results = toTypedList(stream, Integer.class);

        assertNotNull(results);
        assertEquals(4, results.size());
        assertEquals(Integer.valueOf(2), results.get(0));
        assertEquals(Integer.valueOf(2), results.get(1));
        assertEquals(Integer.valueOf(3), results.get(2));
        assertEquals(Integer.valueOf(1), results.get(3));
    }

    @Test
    public void testTakeWhile() throws Exception {
        final var demo = new AdvancedDemo();
        final var method = AdvancedDemo.class.getDeclaredMethod("takeWhile");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Integer> results = toTypedList(stream, Integer.class);

        assertNotNull(results);
        assertEquals(3, results.size());
        assertEquals(Integer.valueOf(1), results.get(0));
        assertEquals(Integer.valueOf(1), results.get(1));
        assertEquals(Integer.valueOf(1), results.get(2));
    }

    @Test
    public void testGenerate() throws Exception {
        final var demo = new AdvancedDemo();
        final var method = AdvancedDemo.class.getDeclaredMethod("generate");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Double> results = toTypedList(stream, Double.class);

        assertNotNull(results);
        assertEquals(5, results.size());
        assertTrue(results.get(0) > 0 && results.get(0) < 1);
        assertTrue(results.get(1) > 0 && results.get(1) < 1);
        assertTrue(results.get(2) > 0 && results.get(2) < 1);
        assertTrue(results.get(3) > 0 && results.get(3) < 1);
        assertTrue(results.get(4) > 0 && results.get(4) < 1);
    }

    @Test
    public void testIterateNumbers() throws Exception {
        final var demo = new AdvancedDemo();
        final var method = AdvancedDemo.class.getDeclaredMethod("iterateNumbers");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Integer> results = toTypedList(stream, Integer.class);

        assertNotNull(results);
        assertEquals(5, results.size());
        assertEquals(Integer.valueOf(1), results.get(0));
        assertEquals(Integer.valueOf(2), results.get(1));
        assertEquals(Integer.valueOf(3), results.get(2));
        assertEquals(Integer.valueOf(4), results.get(3));
        assertEquals(Integer.valueOf(5), results.get(4));
    }

    @Test
    public void testIterateNumbersWithPredicate() throws Exception {
        final var demo = new AdvancedDemo();
        final var method = AdvancedDemo.class.getDeclaredMethod("iterateNumbersWithPredicate");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Integer> results = toTypedList(stream, Integer.class);

        assertNotNull(results);
        assertEquals(5, results.size());
        assertEquals(Integer.valueOf(1), results.get(0));
        assertEquals(Integer.valueOf(2), results.get(1));
        assertEquals(Integer.valueOf(3), results.get(2));
        assertEquals(Integer.valueOf(4), results.get(3));
        assertEquals(Integer.valueOf(5), results.get(4));
    }

    @Test
    public void testBuildDishes() throws Exception {
        final var demo = new AdvancedDemo();
        final var method = AdvancedDemo.class.getDeclaredMethod("buildDishes");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Dish> results = toTypedList(stream, Dish.class);

        assertNotNull(results);
        assertEquals(9, results.size());
        assertEquals("pork", results.get(0).name());
        assertEquals("beef", results.get(1).name());
        assertEquals("chicken", results.get(2).name());
        assertEquals("french fries", results.get(3).name());
        assertEquals("rice", results.get(4).name());
        assertEquals("seasonal fruit", results.get(5).name());
        assertEquals("pizza", results.get(6).name());
        assertEquals("prawns", results.get(7).name());
        assertEquals("salmon", results.get(8).name());
    }
}
