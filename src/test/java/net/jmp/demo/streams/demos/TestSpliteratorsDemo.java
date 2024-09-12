package net.jmp.demo.streams.demos;

/*
 * (#)TestSpliteratorsDemo.java 0.9.0   09/09/2024
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

import java.util.ArrayList;
import java.util.List;

import net.jmp.demo.streams.beans.Article;

import static net.jmp.demo.streams.testutil.TestUtils.*;

import static org.junit.Assert.*;

import org.junit.Test;

public final class TestSpliteratorsDemo {
    @Test
    public void testTryAdvance() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("tryAdvance");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final ArrayList<?> list = castToType(ArrayList.class, o);
        final List<Article> results = listToTypedList(list, Article.class);

        assertNotNull(results);
        assertEquals(35_000, results.size());

        assertEquals("Advanced by Jonathan", results.getFirst().getTitle());
    }

    @Test
    public void testTrySplit() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("trySplit");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final List<List<Article>> results = (List<List<Article>>) method.invoke(demo);

        assertNotNull(results);
        assertEquals(2, results.size());

        final List<Article> list1 = results.get(0);
        final List<Article> list2 = results.get(1);

        assertEquals(17_500, list1.size());
        assertEquals(17_500, list2.size());

        assertEquals("Split1 by Jonathan", list1.getFirst().getTitle());
        assertEquals("Split2 by Jonathan", list2.getFirst().getTitle());
    }

    @Test
    public void testEstimateSize() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("estimateSize");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Long estimateSize = castToType(Long.class, o);

        assertNotNull(estimateSize);
        assertEquals(17_500, (long) estimateSize);
    }

    @Test
    public void testCharacteristics() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("characteristics");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Integer estimateSize = castToType(Integer.class, o);

        assertNotNull(estimateSize);
        assertEquals(16_464, (long) estimateSize);
    }

    @Test
    public void testCustomListSpliterator() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("customListSpliterator");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Integer estimateSize = castToType(Integer.class, o);

        assertNotNull(estimateSize);
        assertEquals(5_050, (long) estimateSize);
    }

    @Test
    public void testCustomListSpliteratorInParallel() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("customListSpliteratorInParallel");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Integer estimateSize = castToType(Integer.class, o);

        assertNotNull(estimateSize);
        assertEquals(5_050, (long) estimateSize);
    }

    @Test
    public void testCustomWordSpliterator() throws Exception {
        final var demo = new SpliteratorsDemo();
        final var method = SpliteratorsDemo.class.getDeclaredMethod("customWordSpliterator");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Integer estimateSize = castToType(Integer.class, o);

        assertNotNull(estimateSize);
        assertEquals(69, (long) estimateSize);
    }
}
