package net.jmp.demo.streams.demos;

/*
 * (#)BasicsDemo.java   0.2.0   08/25/2024
 * (#)BasicsDemo.java   0.1.0   08/24/2024
 *
 * @author   Jonathan Parker
 * @version  0.2.0
 * @since    0.1.0
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

import java.util.Arrays;
import java.util.List;

import java.util.stream.Stream;

import net.jmp.demo.streams.records.*;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates the basics.
 *
 * Demonstrations:
 *   allMatch()
 *   anyMatch()
 *   collect()
 *   count()
 *   distinct()
 *   empty()
 *   filter(*)
 *   findAny()
 *   findFirst()
 *   forEach(*)
 *   forEachOrdered()
 *   limit()
 *   map(*)
 *   max()
 *   min()
 *   noneMatch()
 *   of()
 *   ofNullable()
 *   skip()
 *   sorted()
 *   toArray()
 *   toList()
 */
public final class BasicsDemo implements Demo {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public BasicsDemo() {
        super();
    }

    /**
     * The demo method.
     */
    @Override
    public void demo() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        this.getDishNames().forEach(name -> this.logger.info("name: {}", name));
        this.getDishNameLengths().forEach(length -> this.logger.info("length: {}", length));
        this.getVegetarianDishes().forEach(dish -> this.logger.info(dish.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Return a stream of dish names. Mapping
     * is demonstrated.
     *
     * @return  java.util.stream.Stream&lt;java.lang.String&gt;
     */
    private Stream<String> getDishNames() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<String> names = this.getDishes().stream()
                .map(Dish::name);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(names));
        }

        return names;
    }

    /**
     * Return a stream of dish name lengths.
     * Two mappings are applied.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> getDishNameLengths() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> lengths = this.getDishes().stream()
                .map(Dish::name)
                .map(String::length);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(lengths));
        }

        return lengths;
    }

    /**
     * Return a stream of vegetarian dishes.
     * A filter (predicate function) is used.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    private Stream<Dish> getVegetarianDishes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Dish> vegetarianDishes = this.getDishes().stream()
                .filter(Dish::vegetarian);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(vegetarianDishes));
        }

        return vegetarianDishes;
    }

    /**
     * Method to return a list of dishes.
     *
     * @return  java.util.List&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    private List<Dish> getDishes() {
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
}
