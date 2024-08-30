package net.jmp.demo.streams.demos;

/*
 * (#)TestBasicsDemo.java   0.3.0   08/29/2024
 * (#)TestBasicsDemo.java   0.2.0   08/25/2024
 *
 * @author   Jonathan Parker
 * @version  0.3.0
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

import java.util.function.Predicate;

import java.util.stream.Stream;

import net.jmp.demo.streams.records.Dish;

import static net.jmp.demo.streams.testutil.TestUtils.*;

import org.junit.Test;

import static org.junit.Assert.*;

public final class TestBasicsDemo {
    @Test
    public void testGetDishNames() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getDishNames");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<String> dishNames = streamToTypedList(stream, String.class);

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
    public void testGetDishNamesSorted() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getDishNamesSorted");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<String> dishNames = streamToTypedList(stream, String.class);

        assertNotNull(dishNames);
        assertEquals(9, dishNames.size());
        assertEquals("beef", dishNames.get(0));
        assertEquals("chicken", dishNames.get(1));
        assertEquals("french fries", dishNames.get(2));
        assertEquals("pizza", dishNames.get(3));
        assertEquals("pork", dishNames.get(4));
        assertEquals("prawns", dishNames.get(5));
        assertEquals("rice", dishNames.get(6));
        assertEquals("salmon", dishNames.get(7));
        assertEquals("seasonal fruit", dishNames.get(8));
    }

    @Test
    public void testGetDishNameLengths() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("getDishNameLengths");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> nameLengths = streamToTypedList(stream, Integer.class);

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

    @Test
    public void testGetVegetarianDishes() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("getVegetarianDishes");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = streamToTypedList(stream, Dish.class);

        assertNotNull(dishes);
        assertEquals(4, dishes.size());
        assertEquals("french fries", dishes.get(0).name());
        assertEquals("rice", dishes.get(1).name());
        assertEquals("seasonal fruit", dishes.get(2).name());
        assertEquals("pizza", dishes.get(3).name());
    }

    @Test
    public void testLimitDishes() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("limitDishes");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = streamToTypedList(stream, Dish.class);

        assertNotNull(dishes);
        assertEquals(3, dishes.size());
        assertEquals("pork", dishes.get(0).name());
        assertEquals("beef", dishes.get(1).name());
        assertEquals("chicken", dishes.get(2).name());
    }

    @Test
    public void testSkipDishes() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("skipDishes");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = streamToTypedList(stream, Dish.class);

        assertNotNull(dishes);
        assertEquals(4, dishes.size());
        assertEquals("french fries", dishes.get(0).name());
        assertEquals("rice", dishes.get(1).name());
        assertEquals("pizza", dishes.get(2).name());
        assertEquals("salmon", dishes.get(3).name());
    }

    @Test
    public void testAllMatches() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("allMatches", Predicate.class);

        method.setAccessible(true);

        final Predicate<Dish> p = dish -> dish.calories() < 1_000;
        final boolean result = castToType(Boolean.class, method.invoke(demo, p));

        assertTrue(result);
    }

    @Test
    public void testAnyMatches() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("anyMatches", Predicate.class);

        method.setAccessible(true);

        final Predicate<Dish> p = Dish::vegetarian;
        final boolean result = castToType(Boolean.class, method.invoke(demo, p));

        assertTrue(result);
    }

    @Test
    public void testNoMatches() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("noMatches", Predicate.class);

        method.setAccessible(true);

        final Predicate<Dish> p = dish -> dish.calories() > 1_000;
        final boolean result = castToType(Boolean.class, method.invoke(demo, p));

        assertTrue(result);
    }

    @Test
    public void testCountDishes() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("countDishes");

        method.setAccessible(true);

        final long result = castToType(Long.class, method.invoke(demo));

        assertEquals(9, result);
    }

    @Test
    public void testSortDishesByCalories() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("sortDishesByCalories");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = streamToTypedList(stream, Dish.class);

        assertNotNull(dishes);
        assertEquals(9, dishes.size());
        assertEquals("seasonal fruit", dishes.get(0).name());
        assertEquals("prawns", dishes.get(1).name());
        assertEquals("rice", dishes.get(2).name());
        assertEquals("chicken", dishes.get(3).name());
        assertEquals("salmon", dishes.get(4).name());
        assertEquals("french fries", dishes.get(5).name());
        assertEquals("pizza", dishes.get(6).name());
        assertEquals("beef", dishes.get(7).name());
        assertEquals("pork", dishes.get(8).name());
    }

    @Test
    public void testDistinctDishTypes() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("distinctDishTypes");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<String> types = streamToTypedList(stream, String.class);

        assertNotNull(types);
        assertEquals(3, types.size());
        assertEquals("FISH", types.get(0));
        assertEquals("MEAT", types.get(1));
        assertEquals("OTHER", types.get(2));
    }

    @Test
    public void testEmpty() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("empty");

        method.setAccessible(true);

        final boolean result = castToType(Boolean.class, method.invoke(demo));

        assertTrue(result);
    }

    @Test
    public void testFindAny() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("findAny");

        method.setAccessible(true);

        final boolean result = castToType(Boolean.class, method.invoke(demo));

        assertTrue(result);
    }

    @Test
    public void testFindFirstName() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("findFirstName");

        method.setAccessible(true);

        final String result = castToType(String.class, method.invoke(demo));

        assertNotNull(result);
        assertEquals("pork", result);
    }

    @Test
    public void testGetNameOfHighestCalorieDish() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getNameOfHighestCalorieDish");

        method.setAccessible(true);

        final String result = castToType(String.class, method.invoke(demo));

        assertNotNull(result);
        assertEquals("pork", result);
    }

    @Test
    public void testGetNameOfLowestCalorieDish() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getNameOfLowestCalorieDish");

        method.setAccessible(true);

        final String result = castToType(String.class, method.invoke(demo));

        assertNotNull(result);
        assertEquals("seasonal fruit", result);
    }

    @Test
    public void testOneElement() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("oneElement");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> elements = streamToTypedList(stream, Integer.class);

        assertNotNull(elements);
        assertEquals(1, elements.size());
        assertEquals(1, (long) elements.getFirst());
    }

    @Test
    public void testThreeElements() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("threeElements");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> elements = streamToTypedList(stream, Integer.class);

        assertNotNull(elements);
        assertEquals(3, elements.size());
        assertEquals(1, (long) elements.get(0));
        assertEquals(2, (long) elements.get(1));
        assertEquals(3, (long) elements.get(2));
    }

    @Test
    public void testOfNullableAndNotEmpty() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("ofNullableAndNotEmpty");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> elements = streamToTypedList(stream, Integer.class);

        assertNotNull(elements);
        assertEquals(1, elements.size());
        assertEquals(1, (long) elements.getFirst());
    }

    @Test
    public void testOfNullableAndEmpty() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("ofNullableAndEmpty");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final boolean result = stream.findAny().isEmpty();

        assertTrue(result);
    }

    @Test
    public void testConcatenateTwoStreams() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("concatenateTwoStreams");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> elements = streamToTypedList(stream, Integer.class);

        assertNotNull(elements);
        assertEquals(6, elements.size());
        assertEquals(1, (long) elements.get(0));
        assertEquals(2, (long) elements.get(1));
        assertEquals(3, (long) elements.get(2));
        assertEquals(4, (long) elements.get(3));
        assertEquals(5, (long) elements.get(4));
        assertEquals(6, (long) elements.get(5));
    }

    @Test
    public void testPeek() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("peek");

        method.setAccessible(true);

        final Stream<?> stream = castToType(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> elements = streamToTypedList(stream, Integer.class);

        assertNotNull(elements);
        assertEquals(5, elements.size());
        assertEquals(11, (long) elements.get(0));
        assertEquals(22, (long) elements.get(1));
        assertEquals(33, (long) elements.get(2));
        assertEquals(44, (long) elements.get(3));
        assertEquals(55, (long) elements.get(4));
    }

    @Test
    public void testForEachOrdered() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("forEachOrdered");

        method.setAccessible(true);

        final String result = castToType(String.class, method.invoke(new BasicsDemo()));
        final String expected = "1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100";

        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    public void testToArray() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("toArray");

        method.setAccessible(true);

        final String[] result = castToType(String[].class, method.invoke(new BasicsDemo()));

        assertNotNull(result);
        assertEquals(9, result.length);
        assertEquals("pork", result[0]);
        assertEquals("beef", result[1]);
        assertEquals("chicken", result[2]);
        assertEquals("french fries", result[3]);
        assertEquals("rice", result[4]);
        assertEquals("seasonal fruit", result[5]);
        assertEquals("pizza", result[6]);
        assertEquals("prawns", result[7]);
        assertEquals("salmon", result[8]);
    }

    @Test
    public void testToArrayUsingConstructorReference() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("toArrayUsingConstructorReference");

        method.setAccessible(true);

        final String[] result = castToType(String[].class, method.invoke(new BasicsDemo()));

        assertNotNull(result);
        assertEquals(9, result.length);
        assertEquals("pork", result[0]);
        assertEquals("beef", result[1]);
        assertEquals("chicken", result[2]);
        assertEquals("french fries", result[3]);
        assertEquals("rice", result[4]);
        assertEquals("seasonal fruit", result[5]);
        assertEquals("pizza", result[6]);
        assertEquals("prawns", result[7]);
        assertEquals("salmon", result[8]);
    }

    @Test
    public void testToArrayUsingLambda() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("toArrayUsingLambda");

        method.setAccessible(true);

        final String[] result = castToType(String[].class, method.invoke(new BasicsDemo()));

        assertNotNull(result);
        assertEquals(9, result.length);
        assertEquals("pork", result[0]);
        assertEquals("beef", result[1]);
        assertEquals("chicken", result[2]);
        assertEquals("french fries", result[3]);
        assertEquals("rice", result[4]);
        assertEquals("seasonal fruit", result[5]);
        assertEquals("pizza", result[6]);
        assertEquals("prawns", result[7]);
        assertEquals("salmon", result[8]);
    }

    @Test
    public void testToArrayUsingGenerator() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("toArrayUsingGenerator");

        method.setAccessible(true);

        final String[] result = castToType(String[].class, method.invoke(new BasicsDemo()));

        assertNotNull(result);
        assertEquals(9, result.length);
        assertEquals("pork", result[0]);
        assertEquals("beef", result[1]);
        assertEquals("chicken", result[2]);
        assertEquals("french fries", result[3]);
        assertEquals("rice", result[4]);
        assertEquals("seasonal fruit", result[5]);
        assertEquals("pizza", result[6]);
        assertEquals("prawns", result[7]);
        assertEquals("salmon", result[8]);
    }
}
