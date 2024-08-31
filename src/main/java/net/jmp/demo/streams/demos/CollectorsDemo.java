package net.jmp.demo.streams.demos;

/*
 * (#)CollectorsDemo.java   0.4.0   08/30/2024
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

import java.util.function.Function;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import net.jmp.demo.streams.records.Dish;
import net.jmp.demo.streams.records.DishType;

import static net.jmp.demo.streams.util.DemoUtils.*;
import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates collectors.
 *
 * Demonstrations:
 *   Stream.collect() using the following collectors:
 *     averaging...(*)
 *     collectingAndThen()
 *     counting(*)
 *     filtering()
 *     flatMapping()
 *     groupingBy()
 *     joining(*)
 *     mapping()
 *     maxBy(*)
 *     minBy(*)
 *     partitioningBy()
 *     reducing()
 *     summarizing...(*)
 *     summing...(*)
 *     teeing()
 *     toCollection() (i.e. LinkedHashSet::new)
 *     toConcurrentMap()
 *     toList(*)
 *     toMap(*)
 *     toSet(*)
 *     toUnmodifiableList()
 *     toUnmodifiableMap()
 *     toUnmodifiableSet()
 *  Stream.collect() using a custom created collector
 */
public final class CollectorsDemo implements Demo {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public CollectorsDemo() {
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

        if (this.logger.isInfoEnabled()) {
            this.toList().forEach(this.logger::info);
            this.toMap().forEach((key, value) -> this.logger.info("Key: {}; Value: {}", key, value));
            this.toMapWithMerge().forEach((key, value) -> this.logger.info("Key: {}; Value: {}", key, value));
            this.toSet().forEach(this.logger::info);

            this.logger.info("Average calories: {}", this.averaging());
            this.logger.info("Number of dishes: {}", this.counting());
            this.logger.info("Total calories: {}", this.summing());
            this.logger.info("Calories summary: {}", this.summarizing());
            this.logger.info("Most calories: {}", this.maxBy());
            this.logger.info("Least calories: {}", this.minBy());
            this.logger.info("Joining: {}", this.joining());
            this.logger.info("Joining: {}", this.joiningWithDelimiter());
            this.logger.info("Joining: {}", this.joiningWithPrefixAndSuffix());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Collect the names of dishes into a list.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> toList() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        /* An ArrayList is the default implementation */

        final List<String> names = streamOfDishes()
                .map(Dish::name)
                .map(e -> "List: " + e)
                .collect(Collectors.toList());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(names));
        }

        return names;
    }

    /**
     * Collect the upper-cased names and
     * calories of the dishes into a map.
     *
     * @return  java.util.Map&lt;java.lang.String, java.lang.Integer&gt;
     */
    private Map<String, Integer> toMap() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Function<Dish, String> keyMapper = dish -> dish.name().toUpperCase();
        final Function<Dish, Integer> valueMapper = Dish::calories;

        /* A HashMap is the default implementation */

        final Map<String, Integer> map = streamOfDishes()
                .collect(Collectors.toMap(keyMapper, valueMapper));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(map));
        }

        return map;
    }

    /**
     * Collect the dish types and dish
     * objects of the dishes into a map.
     *
     * @return  java.util.Map&lt;java.lang.String, java.lang.Integer&gt;
     */
    private Map<DishType, Dish> toMapWithMerge() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        /*
         * A hashmap is the default implementation
         * but in this case a TreeMap will be used.
         */

        final Map<DishType, Dish> map = streamOfDishes()
                .collect(Collectors.toMap(Dish::type,   // Key - Dish type
                        Function.identity(),            // Value - Dish
                        (existing, replacement) -> existing,
                        TreeMap::new));

        /*
         * The above merge function retains the first value
         * associated with the key and an IllegalStateException
         * is avoided.
         */

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(map));
        }

        return map;
    }

    /**
     * Collect the names of dishes into a set.
     *
     * @return  java.util.Set&lt;java.lang.String&gt;
     */
    private Set<String> toSet() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        /* A HashSet is the default implementation */

        final Set<String> names = streamOfDishes()
                .map(Dish::name)
                .map(e -> "Set : " + e)
                .collect(Collectors.toSet());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(names));
        }

        return names;
    }

    /**
     * Return the average calories of the dishes.
     *
     * @return  double
     */
    private double averaging() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final double average = streamOfDishes()
                .collect(Collectors.averagingInt(Dish::calories));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(average));
        }

        return average;
    }

    /**
     * Return the number of dishes.
     *
     * @return  long
     */
    private long counting() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final long count = streamOfDishes()
                .collect(Collectors.counting());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(count));
        }

        return count;
    }

    /**
     * Return the sum of the calories in the dishes.
     *
     * @return  int
     */
    private int summing() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final int sum = streamOfDishes()
                .collect(Collectors.summingInt(Dish::calories));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(sum));
        }

        return sum;
    }

    /**
     * Return the summary of the calories in the dishes.
     *
     * @return  java.util.IntSummaryStatistics
     */
    private IntSummaryStatistics summarizing() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final IntSummaryStatistics summary = streamOfDishes()
                .collect(Collectors.summarizingInt(Dish::calories));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(summary));
        }

        return summary;
    }

    /**
     * Return the name of the dish with the most calories.
     *
     * @return  java.lang.String
     */
    private String maxBy() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Collector<Dish, ?, Optional<Dish>> collectorMax = Collectors.maxBy(Comparator.comparing(Dish::calories));

        final Optional<Dish> dish = streamOfDishes()
                .collect(collectorMax);

        final String name = dish.map(Dish::name).orElseThrow(() -> new RuntimeException("No dish returned"));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(name));
        }

        return name;
    }

    /**
     * Return the name of the dish with the least calories.
     *
     * @return  java.lang.String
     */
    private String minBy() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Collector<Dish, ?, Optional<Dish>> collectorMin = Collectors.minBy(Comparator.comparing(Dish::calories));

        final Optional<Dish> dish = streamOfDishes()
                .collect(collectorMin);

        final String name = dish.map(Dish::name).orElseThrow(() -> new RuntimeException("No dish returned"));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(name));
        }

        return name;
    }

    /**
     * Return the names of the dishes joined together.
     *
     * @return  java.lang.String
     */
    private String joining() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String joined = streamOfDishes()
                .map(Dish::name)
                .collect(Collectors.joining());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(joined));
        }

        return joined;
    }

    /**
     * Return the names of the dishes
     * joined together with a delimiter.
     *
     * @return  java.lang.String
     */
    private String joiningWithDelimiter() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String joined = streamOfDishes()
                .map(Dish::name)
                .collect(Collectors.joining(", "));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(joined));
        }

        return joined;
    }

    /**
     * Return the names of the dishes
     * joined together with a delimiter
     * applying a prefix and suffix.
     *
     * @return  java.lang.String
     */
    private String joiningWithPrefixAndSuffix() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String joined = streamOfDishes()
                .map(Dish::name)
                .collect(Collectors.joining(", ", "<", ">"));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(joined));
        }

        return joined;
    }
}
