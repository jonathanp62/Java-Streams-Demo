package net.jmp.demo.streams.demos;

/*
 * (#)TestBasicsDemo.java   0.2.0   08/25/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.2.0
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

import org.junit.Test;

import static org.junit.Assert.*;

public final class TestBasicsDemo {
    @Test
    public void testGetDishNames() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getDishNames");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Stream<String> stream = (Stream<String>) method.invoke(demo);
        final List<String> dishNames = stream.toList();

        assertNotNull(dishNames);
        assertEquals(9, dishNames.size());
        assertTrue(dishNames.contains("pork"));
        assertTrue(dishNames.contains("beef"));
        assertTrue(dishNames.contains("chicken"));
        assertTrue(dishNames.contains("french fries"));
        assertTrue(dishNames.contains("rice"));
        assertTrue(dishNames.contains("seasonal fruit"));
        assertTrue(dishNames.contains("pizza"));
        assertTrue(dishNames.contains("prawns"));
        assertTrue(dishNames.contains("salmon"));
    }

    @Test
    public void testGetDishNameLengths() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getDishNameLengths");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Stream<Integer> stream = (Stream<Integer>) method.invoke(demo);
        final List<Integer> nameLengths = stream.toList();

        assertNotNull(nameLengths);
        assertEquals(9, nameLengths.size());
        assertEquals(4, (long) nameLengths.get(0));
        assertEquals(4, (long) nameLengths.get(1));
        assertEquals(7, (long) nameLengths.get(2));
        assertEquals(12, (long) nameLengths.get(3));
        assertEquals(4, (long) nameLengths.get(4));
        assertEquals(14, (long) nameLengths.get(5));
        assertEquals(5, (long) nameLengths.get(6));
        assertEquals(6, (long) nameLengths.get(7));
        assertEquals(6, (long) nameLengths.get(8));
    }
}
