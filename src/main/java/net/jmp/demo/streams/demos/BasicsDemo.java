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

import static java.util.Comparator.comparing;

import java.util.List;

import java.util.Optional;
import java.util.function.Predicate;

import java.util.stream.Stream;

import net.jmp.demo.streams.records.*;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates the basics.
 *
 * Demonstrations:
 *   allMatch(*)
 *   anyMatch(*)
 *   count(*)
 *   distinct(*)
 *   empty(*)
 *   filter(*)
 *   findAny(*)
 *   findFirst(*)
 *   forEach(*)
 *   forEachOrdered()
 *   limit(*)
 *   map(*)
 *   max(*)
 *   min(*)
 *   noneMatch(*)
 *   of()
 *   ofNullable()
 *   skip(*)
 *   sorted(*)
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

        this.getDishNames().forEach(name -> this.logger.info("Name: {}", name));
        this.getDishNamesSorted().forEach(name -> this.logger.info("Sorted name: {}", name));
        this.getDishNameLengths().forEach(length -> this.logger.info("Length: {}", length));
        this.getVegetarianDishes().forEach(dish -> this.logger.info("Vegetarian: {}", dish));
        this.limitDishes().forEach(dish -> this.logger.info("Limit: {}", dish));
        this.skipDishes().forEach(dish -> this.logger.info("Skip: {}", dish));

        if (this.allMatches(dish -> dish.calories() < 1_000)) {
            this.logger.info("All dishes match the predicate");
        }

        if (this.anyMatches(Dish::vegetarian)) {
            this.logger.info("Some dishes match the predicate");
        }

        if (this.noMatches(dish -> dish.calories() > 1_000)) {
            this.logger.info("No dishes match the predicate");
        }

        this.logger.info("There are {} dishes", this.countDishes());

        this.sortDishesByCalories().forEach(dish -> this.logger.info("By calories: {}", dish));
        this.distinctDishTypes().forEach(type -> this.logger.info("Type: {}", type));

        this.logger.info("Stream empty?: {}", this.empty());
        this.logger.info("Find any?: {}", this.findAny());
        this.logger.info("Find first: {}", this.findFirstName());
        this.logger.info("Most calories: {}", this.getNameOfHighestCalorieDish());
        this.logger.info("Least calories: {}", this.getNameOfLowestCalorieDish());

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
     * Return a stream of sorted dish names.
     * Mapping is also demonstrated.
     *
     * @return  java.util.stream.Stream&lt;java.lang.String&gt;
     */
    private Stream<String> getDishNamesSorted() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<String> names = this.getDishes().stream()
                .map(Dish::name)
                .sorted();

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
     * Limit the stream of high calorie dishes.
     * A filter (predicate function) is used.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    private Stream<Dish> limitDishes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Dish> dishes = this.getDishes().stream()
                .filter(dish -> dish.calories() > 300)
                .limit(3);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishes));
        }

        return dishes;
    }

    /**
     * Skip the first few elements in the stream of high calorie dishes.
     * A filter (predicate function) is used.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    private Stream<Dish> skipDishes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Dish> dishes = this.getDishes().stream()
                .filter(dish -> dish.calories() > 300)
                .skip(3);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishes));
        }

        return dishes;
    }

    /**
     * Return true if all dishes match the predicate.
     *
     * @param   predicate   java.util.function.Predicate&lt;? super Dish&gt;
     * @return              boolean
     */
    private boolean allMatches(final Predicate<? super Dish> predicate) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(predicate));
        }

        final boolean result = this.getDishes().stream()
                .allMatch(predicate);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Return true if any of the dishes match the predicate.
     *
     * @param   predicate   java.util.function.Predicate&lt;? super Dish&gt;
     * @return              boolean
     */
    private boolean anyMatches(final Predicate<? super Dish> predicate) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(predicate));
        }

        final boolean result = this.getDishes().stream()
                .anyMatch(predicate);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Return true if none of the dishes match the predicate.
     *
     * @param   predicate   java.util.function.Predicate&lt;? super Dish&gt;
     * @return              boolean
     */
    private boolean noMatches(final Predicate<? super Dish> predicate) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(predicate));
        }

        final boolean result = this.getDishes().stream()
                .noneMatch(predicate);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Return a count of the dishes.
     *
     * @return  long
     */
    private long countDishes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long result = this.getDishes().stream().count();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Sort the dishes in the stream by calories.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Dish&gt;
     */
    private Stream<Dish> sortDishesByCalories() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Dish> dishes = this.getDishes().stream()
                .sorted(comparing(Dish::calories));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishes));
        }

        return dishes;
    }

    /**
     * Return a stream of sorted distinct dish types.
     *
     * @return  java.util.stream.Stream&lt;java.lang.String&gt;
     */
    private Stream<String> distinctDishTypes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<String> types = this.getDishes().stream()
                .map(Dish::type)
                .map(DishType::name)
                .distinct()
                .sorted();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(types));
        }

        return types;
    }

    /**
     * Return true if the stream is empty.
     *
     * @return  boolean
     */
    private boolean empty() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Object> stream = Stream.empty();
        final boolean result = stream.count() == 0;

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Return true if findAny returns a value.
     *
     * @return  boolean
     */
    private boolean findAny() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Optional<Dish> dish = this.getDishes().stream().findAny();
        final boolean result = dish.isPresent();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Return the name of the first element in the stream.
     *
     * @return  java.lang.String
     */
    private String findFirstName() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        String result = null;

        final Optional<Dish> dish = this.getDishes().stream().findFirst();

        if (dish.isPresent()) {
            result = dish.get().name();
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Get the name of the dish with the most calories.
     *
     * @return  java.lang.String
     */
    private String getNameOfHighestCalorieDish() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Optional<Dish> dish = this.getDishes().stream()
                .max(comparing(Dish::calories));

        final String result = dish.map(Dish::name).orElse(null);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Get the name of the dish with the least calories.
     *
     * @return  java.lang.String
     */
    private String getNameOfLowestCalorieDish() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Optional<Dish> dish = this.getDishes().stream()
                .min(comparing(Dish::calories));

        final String result = dish.map(Dish::name).orElse(null);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
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
