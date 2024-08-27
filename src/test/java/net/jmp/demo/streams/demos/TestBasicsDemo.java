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

import java.util.function.Predicate;

import java.util.stream.Stream;

import net.jmp.demo.streams.records.Dish;

import org.junit.Test;

import static org.junit.Assert.*;

public final class TestBasicsDemo {
    @Test
    public void testGetDishNames() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("getDishNames");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = this.cast(Stream.class, o);
        final List<String> dishNames = this.toList(stream, String.class);

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
        final Stream<?> stream = this.cast(Stream.class, o);
        final List<String> dishNames = this.toList(stream, String.class);

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

        final Stream<?> stream = this.cast(Stream.class, method.invoke(new BasicsDemo()));
        final List<Integer> nameLengths = this.toList(stream, Integer.class);

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

        final Stream<?> stream = this.cast(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = this.toList(stream, Dish.class);

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

        final Stream<?> stream = this.cast(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = this.toList(stream, Dish.class);

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

        final Stream<?> stream = this.cast(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = this.toList(stream, Dish.class);

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
        final boolean result = this.cast(Boolean.class, method.invoke(demo, p));

        assertTrue(result);
    }

    @Test
    public void testAnyMatches() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("anyMatches", Predicate.class);

        method.setAccessible(true);

        final Predicate<Dish> p = Dish::vegetarian;
        final boolean result = this.cast(Boolean.class, method.invoke(demo, p));

        assertTrue(result);
    }

    @Test
    public void testNoMatches() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("noMatches", Predicate.class);

        method.setAccessible(true);

        final Predicate<Dish> p = dish -> dish.calories() > 1_000;
        final boolean result = this.cast(Boolean.class, method.invoke(demo, p));

        assertTrue(result);
    }

    @Test
    public void testCountDishes() throws Exception {
        final var demo = new BasicsDemo();
        final var method = BasicsDemo.class.getDeclaredMethod("countDishes");

        method.setAccessible(true);

        final long result = this.cast(Long.class, method.invoke(demo));

        assertEquals(9, result);
    }

    @Test
    public void testSortDishesByCalories() throws Exception {
        final var method = BasicsDemo.class.getDeclaredMethod("sortDishesByCalories");

        method.setAccessible(true);

        final Stream<?> stream = this.cast(Stream.class, method.invoke(new BasicsDemo()));
        final List<Dish> dishes = this.toList(stream, Dish.class);

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

        final Stream<?> stream = this.cast(Stream.class, method.invoke(new BasicsDemo()));
        final List<String> types = this.toList(stream, String.class);

        assertNotNull(types);
        assertEquals(3, types.size());
        assertEquals("FISH", types.get(0));
        assertEquals("MEAT", types.get(1));
        assertEquals("OTHER", types.get(2));
    }

    /**
     * Cast object to an instance of type T.
     *
     * @param   <T>     The type of instance to cast to
     * @param   t       The class of type T
     * @param   object  java.lang.Object
     * @return          T
     */
    private <T> T cast(final Class<T> t, final Object object) {
        return t.cast(object);
    }

    /**
     * Create a list of elements of type T.
     *
     * @param   <T>     The type of element in the list
     * @param   stream  java.util.stream.Stream&lt;?&gt;
     * @param   clazz   The class of type T
     * @return          java.util.List&lt;T&gt;
     */
    private <T> List<T> toList(final Stream<?> stream, final Class<T> clazz) {
        return stream
                .filter(clazz::isInstance)
                .map(clazz::cast)
                .toList();
    }
}
