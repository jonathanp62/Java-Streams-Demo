package net.jmp.demo.streams.util;

/*
 * (#)DemoUtils.java    0.3.0   08/29/2024
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

import net.jmp.demo.streams.records.Dish;
import net.jmp.demo.streams.records.DishType;

import java.lang.reflect.Array;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Stream;

/**
 * Demo utilities.
 */
public final class DemoUtils {
    /**
     * The default constructor.
     */
    private DemoUtils() {
        super();
    }

    /**
     * Method to return a list of dishes.
     *
     * @return  java.util.List&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    public static List<Dish> listOfDishes() {
        return Arrays.asList(
                new Dish("pork", false, 800, DishType.MEAT),
                new Dish("beef", false, 700, DishType.MEAT),
                new Dish("chicken", false, 400, DishType.MEAT),
                new Dish("french fries", true, 530, DishType.OTHER),
                new Dish("rice", true, 350, DishType.OTHER),
                new Dish("seasonal fruit", true, 120, DishType.OTHER),
                new Dish("pizza", true, 550, DishType.OTHER),
                new Dish("prawns", false, 300, DishType.FISH),
                new Dish("salmon", false, 450, DishType.FISH));
    }

    /**
     * Method to return a stream of dishes.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    public static Stream<Dish> streamOfDishes() {
        final Stream.Builder<Dish> builder = Stream.builder();

        builder.accept(new Dish("pork", false, 800, DishType.MEAT));
        builder.accept(new Dish("beef", false, 700, DishType.MEAT));
        builder.accept(new Dish("chicken", false, 400, DishType.MEAT));
        builder.accept(new Dish("french fries", true, 530, DishType.OTHER));
        builder.accept(new Dish("rice", true, 350, DishType.OTHER));
        builder.accept(new Dish("seasonal fruit", true, 120, DishType.OTHER));
        builder.accept(new Dish("pizza", true, 550, DishType.OTHER));
        builder.accept(new Dish("prawns", false, 300, DishType.FISH));
        builder.accept(new Dish("salmon", false, 450, DishType.FISH));

        return builder.build();
    }

    /**
     * Create an array of elements of
     * type T from the array of objects.
     *
     * @param   <T>     The type of element in the list
     * @param   array   java.lang.Object[]
     * @param   clazz   The class of type T
     * @return          T[]
     */
    public static <T> T[] toTypedArray(final Object[] array, final Class<T> clazz) {
        @SuppressWarnings("unchecked")
        final T[] typedArray = (T[]) Array.newInstance(clazz, array.length);

        for (int i = 0; i < array.length; i++) {
            typedArray[i] = clazz.cast(array[i]);
        }

        return typedArray;
    }
}
