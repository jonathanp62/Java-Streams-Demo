package net.jmp.demo.streams.demos;

/*
 * (#)TestReduceDemo.java   0.8.0   09/07/2024
 *
 * @author   Jonathan Parker
 * @version  0.8.0
 * @since    0.8.0
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

import static net.jmp.demo.streams.testutil.TestUtils.castToType;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestReduceDemo {
    @Test
    public void testJoin() throws Exception {
        final var demo = new ReduceDemo();
        final var method = ReduceDemo.class.getDeclaredMethod("join");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String string = castToType(String.class, o);

        assertNotNull(string);

        assertEquals("Hello, world!", string);
    }

    @Test
    public void testSum() throws Exception {
        final var demo = new ReduceDemo();
        final var method = ReduceDemo.class.getDeclaredMethod("sum");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Integer integer = castToType(Integer.class, o);

        assertNotNull(integer);

        assertEquals(87, (long) integer);
    }

    @Test
    public void testProduct() throws Exception {
        final var demo = new ReduceDemo();
        final var method = ReduceDemo.class.getDeclaredMethod("product");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Integer integer = castToType(Integer.class, o);

        assertNotNull(integer);

        assertEquals(2227680, (long) integer);
    }
}
