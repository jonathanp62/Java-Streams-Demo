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

    public void testToSortedSet() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("toSortedSet");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final TreeSet<?> hashSet = castToType(TreeSet.class, o);
        final Set<String> dishNames = setToTypedSet(hashSet, String.class);
        final String[] names = dishNames.toArray(new String[0]);

        assertNotNull(dishNames);
        assertEquals(9, dishNames.size());
        assertNotNull(names);
        assertEquals(9, names.length);

        assertEquals("Sorted : beef", names[0]);
        assertEquals("Sorted : chicken", names[1]);
        assertEquals("Sorted : french fries", names[2]);
        assertEquals("Sorted : pizza", names[3]);
        assertEquals("Sorted : pork", names[4]);
        assertEquals("Sorted : prawns", names[5]);
        assertEquals("Sorted : rice", names[6]);
        assertEquals("Sorted : salmon", names[7]);
        assertEquals("Sorted : seasonal fruit", names[8]);
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

    @Test
    public void testMaxBy() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("maxBy");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String name = castToType(String.class, o);

        assertNotNull(name);
        assertEquals("pork", name);
    }

    @Test
    public void testMinBy() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("minBy");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String name = castToType(String.class, o);

        assertNotNull(name);
        assertEquals("seasonal fruit", name);
    }

    @Test
    public void testJoining() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("joining");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String joined = castToType(String.class, o);

        assertNotNull(joined);
        assertEquals("porkbeefchickenfrench friesriceseasonal fruitpizzaprawnssalmon", joined);
    }

    @Test
    public void testJoiningWithDelimiter() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("joiningWithDelimiter");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String joined = castToType(String.class, o);

        assertNotNull(joined);
        assertEquals("pork, beef, chicken, french fries, rice, seasonal fruit, pizza, prawns, salmon", joined);
    }

    @Test
    public void testJoiningWithPrefixAndSuffix() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("joiningWithPrefixAndSuffix");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String joined = castToType(String.class, o);

        assertNotNull(joined);
        assertEquals("<pork, beef, chicken, french fries, rice, seasonal fruit, pizza, prawns, salmon>", joined);
    }

    @Test
    public void testFiltering() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("filtering");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final ArrayList<?> arrayList = castToType(ArrayList.class, o);
        final List<String> dishNames = listToTypedList(arrayList, String.class);

        assertNotNull(dishNames);
        assertEquals(4, dishNames.size());
        assertTrue(dishNames.contains("pork"));
        assertTrue(dishNames.contains("beef"));
        assertTrue(dishNames.contains("french fries"));
        assertTrue(dishNames.contains("pizza"));
    }

    @Test
    public void testFilteringAndGrouping() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("filteringAndGrouping");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Map<DishType, Long> map = (Map<DishType, Long>) method.invoke(demo);

        assertNotNull(map);
        assertEquals(3, map.size());

        final long otherCalories = map.get(DishType.OTHER);
        final long meatCalories = map.get(DishType.MEAT);
        final long fishCalories = map.get(DishType.FISH);

        assertEquals(2, otherCalories);
        assertEquals(2, meatCalories);
        assertEquals(0, fishCalories);
    }

    @Test
    public void testPartitioning() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("partitioning");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Map<Boolean, List<Dish>> map = (Map<Boolean, List<Dish>>) method.invoke(demo);

        assertNotNull(map);
        assertEquals(2, map.size());

        final List<Dish> vegetarianDishes = map.get(true);
        final List<Dish> nonVegetarianDishes = map.get(false);

        assertNotNull(vegetarianDishes);
        assertEquals(4, vegetarianDishes.size());
        assertNotNull(nonVegetarianDishes);
        assertEquals(5, nonVegetarianDishes.size());

        assertEquals("french fries", vegetarianDishes.get(0).name());
        assertEquals("rice", vegetarianDishes.get(1).name());
        assertEquals("seasonal fruit", vegetarianDishes.get(2).name());
        assertEquals("pizza", vegetarianDishes.get(3).name());

        assertEquals("pork", nonVegetarianDishes.get(0).name());
        assertEquals("beef", nonVegetarianDishes.get(1).name());
        assertEquals("chicken", nonVegetarianDishes.get(2).name());
        assertEquals("prawns", nonVegetarianDishes.get(3).name());
        assertEquals("salmon", nonVegetarianDishes.get(4).name());
    }

    @Test
    public void testPartitioningToSum() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("partitioningToSum");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Map<Boolean, Integer> map = (Map<Boolean, Integer>) method.invoke(demo);

        assertNotNull(map);
        assertEquals(2, map.size());

        final int vegetarianCalories = map.get(true);
        final int nonVegetarianCalories = map.get(false);

        assertEquals(1550, vegetarianCalories);
        assertEquals(2650, nonVegetarianCalories);
    }

    @Test
    public void testGroupingToList() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("groupingToList");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Map<DishType, List<Dish>> map = (Map<DishType, List<Dish>>) method.invoke(demo);

        assertNotNull(map);
        assertEquals(3, map.size());

        final List<Dish> otherDishes = map.get(DishType.OTHER);
        final List<Dish> meatDishes = map.get(DishType.MEAT);
        final List<Dish> fishDishes = map.get(DishType.FISH);

        assertNotNull(otherDishes);
        assertEquals(4, otherDishes.size());
        assertNotNull(meatDishes);
        assertEquals(3, meatDishes.size());
        assertNotNull(fishDishes);
        assertEquals(2, fishDishes.size());

        assertEquals("french fries", otherDishes.get(0).name());
        assertEquals("rice", otherDishes.get(1).name());
        assertEquals("seasonal fruit", otherDishes.get(2).name());
        assertEquals("pizza", otherDishes.get(3).name());

        assertEquals("pork", meatDishes.get(0).name());
        assertEquals("beef", meatDishes.get(1).name());
        assertEquals("chicken", meatDishes.get(2).name());

        assertEquals("prawns", fishDishes.get(0).name());
        assertEquals("salmon", fishDishes.get(1).name());
    }

    @Test
    public void testGroupingToSet() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("groupingToSet");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Map<DishType, Set<Dish>> map = (Map<DishType, Set<Dish>>) method.invoke(demo);

        assertNotNull(map);
        assertEquals(3, map.size());

        final Set<Dish> otherDishes = map.get(DishType.OTHER);
        final Set<Dish> meatDishes = map.get(DishType.MEAT);
        final Set<Dish> fishDishes = map.get(DishType.FISH);

        assertNotNull(otherDishes);
        assertEquals(4, otherDishes.size());
        assertNotNull(meatDishes);
        assertEquals(3, meatDishes.size());
        assertNotNull(fishDishes);
        assertEquals(2, fishDishes.size());

        final Dish[] otherArray = otherDishes.toArray(new Dish[0]);

        Arrays.sort(otherArray, Comparator.comparing(Dish::name));

        assertEquals("french fries", otherArray[0].name());
        assertEquals("pizza", otherArray[1].name());
        assertEquals("rice", otherArray[2].name());
        assertEquals("seasonal fruit", otherArray[3].name());

        final Dish[] meatArray = meatDishes.toArray(new Dish[0]);

        Arrays.sort(meatArray, Comparator.comparing(Dish::name));

        assertEquals("beef", meatArray[0].name());
        assertEquals("chicken", meatArray[1].name());
        assertEquals("pork", meatArray[2].name());

        final Dish[] fishArray = fishDishes.toArray(new Dish[0]);

        Arrays.sort(fishArray, Comparator.comparing(Dish::name));

        assertEquals("prawns", fishArray[0].name());
        assertEquals("salmon", fishArray[1].name());
    }

    @Test
    public void testGroupingToSum() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("groupingToSum");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Map<DishType, Integer> map = (Map<DishType, Integer>) method.invoke(demo);

        assertNotNull(map);
        assertEquals(3, map.size());

        final int otherCalories = map.get(DishType.OTHER);
        final int meatCalories = map.get(DishType.MEAT);
        final int fishCalories = map.get(DishType.FISH);

        assertEquals(1550, otherCalories);
        assertEquals(1900, meatCalories);
        assertEquals(750, fishCalories);
    }

    @Test
    public void testMapping() throws Exception {
        final var demo = new CollectorsDemo();
        final var method = CollectorsDemo.class.getDeclaredMethod("mapping");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final ArrayList<?> arrayList = castToType(ArrayList.class, o);
        final List<String> dishNames = listToTypedList(arrayList, String.class);

        assertNotNull(dishNames);
        assertEquals(9, dishNames.size());
        assertTrue(dishNames.contains("Pork"));
        assertTrue(dishNames.contains("Beef"));
        assertTrue(dishNames.contains("Chicken"));
        assertTrue(dishNames.contains("French fries"));
        assertTrue(dishNames.contains("Rice"));
        assertTrue(dishNames.contains("Seasonal fruit"));
        assertTrue(dishNames.contains("Pizza"));
        assertTrue(dishNames.contains("Prawns"));
        assertTrue(dishNames.contains("Salmon"));
    }

}
