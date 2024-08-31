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

import net.jmp.demo.streams.records.Dish;
import net.jmp.demo.streams.records.DishType;

import static net.jmp.demo.streams.testutil.TestUtils.*;

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
        final List<String> dishNames = listToTypedList(arrayList, String.class);

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
    public void testToMap() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("toMap");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final HashMap<?, ?> hashMap = castToType(HashMap.class, o);
        final Map<String, Integer> map = mapToTypedMap(hashMap, String.class, Integer.class);

        assertNotNull(map);
        assertEquals(9, map.size());
        assertEquals(800, (long) map.get("PORK"));
        assertEquals(700, (long) map.get("BEEF"));
        assertEquals(400, (long) map.get("CHICKEN"));
        assertEquals(530, (long) map.get("FRENCH FRIES"));
        assertEquals(350, (long) map.get("RICE"));
        assertEquals(120, (long) map.get("SEASONAL FRUIT"));
        assertEquals(550, (long) map.get("PIZZA"));
        assertEquals(300, (long) map.get("PRAWNS"));
        assertEquals(450, (long) map.get("SALMON"));
    }

    @Test
    public void toMapWithMerge() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("toMapWithMerge");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final TreeMap<?, ?> treeMap = castToType(TreeMap.class, o);
        final Map<DishType, Dish> map = mapToTypedMap(treeMap, DishType.class, Dish.class);

        assertNotNull(map);
        assertEquals(3, map.size());
        assertEquals("prawns", map.get(DishType.FISH).name());
        assertEquals("pork", map.get(DishType.MEAT).name());
        assertEquals("french fries", map.get(DishType.OTHER).name());
    }

    @Test
    public void testToSet() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("toSet");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final HashSet<?> hashSet = castToType(HashSet.class, o);
        final Set<String> dishNames = setToTypedSet(hashSet, String.class);

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

    @Test
    public void testAveraging() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("averaging");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final double average = castToType(Double.class, o);

        assertEquals(466.6666666666667, average, 0.001);
    }

    @Test
    public void testCounting() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("counting");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final long count = castToType(Long.class, o);

        assertEquals(9, count);
    }

    @Test
    public void testSumming() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("summing");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final int sum = castToType(Integer.class, o);

        assertEquals(4200, sum);
    }

    @Test
    public void testSummarizing() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("summarizing");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final IntSummaryStatistics summary = castToType(IntSummaryStatistics.class, o);

        assertEquals(9, summary.getCount());
        assertEquals(4200, summary.getSum());
        assertEquals(120, summary.getMin());
        assertEquals(800, summary.getMax());
        assertEquals(466.6666666666667, summary.getAverage(), 0.001);
    }
}