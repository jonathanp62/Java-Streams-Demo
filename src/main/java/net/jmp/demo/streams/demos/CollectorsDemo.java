package net.jmp.demo.streams.demos;

/*
 * (#)CollectorsDemo.java   0.10.0  09/24/2024
 * (#)CollectorsDemo.java   0.7.0   09/07/2024
 * (#)CollectorsDemo.java   0.5.0   09/05/2024
 * (#)CollectorsDemo.java   0.4.0   08/30/2024
 *
 * @author   Jonathan Parker
 * @version  0.10.0
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

import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.jmp.demo.streams.records.*;

import net.jmp.demo.streams.util.CollectorsFactory;

import static net.jmp.demo.streams.util.DemoUtils.*;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates collectors.
 *
 * Demonstrations:
 *   Stream.collect() using the following collectors:
 *     averaging...(*)
 *     collectingAndThen(*)
 *     counting(*)
 *     filtering(*)
 *     filtering(*) and groupingBy(*) together
 *     flatMapping(*)
 *     groupingBy(*)
 *     joining(*)
 *     mapping(*)
 *     maxBy(*)
 *     minBy(*)
 *     partitioningBy(*)
 *     reducing(*)
 *     summarizing...(*)
 *     summing...(*)
 *     teeing(*)
 *     toCollection(*) (i.e. LinkedHashSet::new)
 *     toList(*)
 *     toMap(*)
 *     toSet(*)
 *  Stream.collect(*) using custom created collectors
 */
public final class CollectorsDemo implements Demo {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** A capitalizer function. A UnaryOperator&lt;T&gt; is preferred to Function&lt;T, T&gt;. */
    private final UnaryOperator<String> capitalizer = string -> {
        final String firstLetter = string.substring(0, 1).toUpperCase();

        return firstLetter + string.substring(1);
    };

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

        this.standardCollectors();
        this.customCollectors();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Standard collectors.
     */
    private void standardCollectors() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isInfoEnabled()) {
            final var keyValue = "Key: {}; Value: {}";
            final var joining = "Joining: {}";
            final var vegetarian = "Vegetarian? {}: {}";

            this.toList().forEach(this.logger::info);
            this.toMap().forEach((key, value) -> this.logger.info(keyValue, key, value));
            this.toMapWithMerge().forEach((key, value) -> this.logger.info(keyValue, key, value));
            this.toSet().forEach(this.logger::info);
            this.toSortedSet().forEach(this.logger::info);

            this.logger.info("Average calories: {}", this.averaging());
            this.logger.info("Number of dishes: {}", this.counting());
            this.logger.info("Total calories: {}", this.summing());
            this.logger.info("Calories summary: {}", this.summarizing());
            this.logger.info("Most calories: {}", this.maxBy());
            this.logger.info("Least calories: {}", this.minBy());
            this.logger.info(joining, this.joining());
            this.logger.info(joining, this.joiningWithDelimiter());
            this.logger.info(joining, this.joiningWithPrefixAndSuffix());

            this.filtering().forEach(dish -> this.logger.info("High calorie: {}", dish));
            this.filteringAndGrouping().forEach((key, value) -> this.logger.info("High calorie {}: {}", key, value));

            this.partitioning().forEach((key, value) -> value.forEach(dish -> this.logger.info(vegetarian, key, dish.name())));
            this.partitioningToSum().forEach((key, value) -> this.logger.info(vegetarian, key, value));

            this.groupingToList().forEach((key, value) -> value.forEach(dish -> this.logger.info("List {}: {}", key, dish.name())));
            this.groupingToSet().forEach((key, value) -> value.forEach(dish -> this.logger.info("Set {}: {}", key, dish.name())));
            this.groupingToSum().forEach((key, value) -> this.logger.info("Sum {}: {}", key, value));

            this.mapping().forEach(this.logger::info);
            this.flatMapping().forEach((key, value) -> value.forEach(name -> this.logger.info("Name {}: {}", key, name)));

            this.logger.info("Longest name: {}", this.collectingAndThen());

            this.teeing().forEach((key, value) -> value.forEach(name -> this.logger.info("{}: {}", key, name)));

            this.logger.info("Highest cal: {}", this.reducingToHighestCalorieDish());
            this.logger.info("Lowest cal: {}", this.reducingToLowestCalorieDish());
            this.logger.info("Shortest name: {}", this.reducingToShortestName());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Custom collectors.
     */
    private void customCollectors() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isInfoEnabled()) {
            this.droppingWhile().forEach(i -> this.logger.info("Dropping: {}", i));
            this.takingWhile().forEach(i -> this.logger.info("Taking: {}", i));
            this.limiting().forEach(i -> this.logger.info("Limiting: {}", i));
            this.skipping().forEach(i -> this.logger.info("Skipping: {}", i));
            this.toDeque().forEach(i -> this.logger.info("Deque: {}", i));
            this.distinctifying().forEach(i -> this.logger.info("Distinct: {}", i));
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
                .collect(Collectors.toList());  // Using .toList() creates an ImmutableCollection$ListN instance

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
     * Collect the names of dishes into a sorted set.
     *
     * @return  java.util.Set&lt;java.lang.String&gt;
     */
    private Set<String> toSortedSet() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        /* Using TreeSet instead of default HashSet */

        final Set<String> names = streamOfDishes()
                .map(Dish::name)
                .map(e -> "Sorted : " + e)
                .sorted()
                .collect(Collectors.toCollection(TreeSet::new));

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

    /**
     * Return a list of the names of high calorie dishes.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> filtering() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> names = streamOfDishes()
                .collect(Collectors.filtering(dish -> dish.calories() > 500, Collectors.toList()))
                .stream()
                .map(Dish::name)
                .collect(Collectors.toList());  // Using .toList() creates an ImmutableCollection$ListN instance

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(names));
        }

        return names;
    }

    /**
     * Group the dishes by type and filter
     * for high calorie dishes counting
     * the number for each type.
     *
     * @return  java.util.Map&lt;net.jmp.demo.streams.records.DishType, java.lang.Long&gt;
     */
    private Map<DishType, Long> filteringAndGrouping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        // Count the number of dishes for each type that are high calorie

        final Map<DishType, Long> counts = streamOfDishes()
                .collect(Collectors.groupingBy(Dish::type,
                        Collectors.filtering(dish -> dish.calories() > 500,
                                Collectors.counting()
                        )
                ));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(counts));
        }

        return counts;
    }

    /**
     * Partition the dishes by vegetarian
     * and non-vegetarian status.
     *
     * @return  java.util.Map&lt;java.lang.Boolean, java.util.List&lt;net.jmp.demo.streams.records.Dish&gt;&gt;
     */
    private Map<Boolean, List<Dish>> partitioning() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Map<Boolean, List<Dish>> dishes = streamOfDishes()
                .collect(Collectors.partitioningBy(Dish::vegetarian));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishes));
        }

        return dishes;
    }

    /**
     * Partition the dishes by vegetarian
     * and non-vegetarian status and return
     * the sum of the calories for each.
     *
     * @return  java.util.Map&lt;java.lang.Boolean, java.lang.Integer&gt;
     */
    private Map<Boolean, Integer> partitioningToSum() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Map<Boolean, Integer> sums = streamOfDishes()
                .collect(Collectors.partitioningBy(Dish::vegetarian, Collectors.summingInt(Dish::calories)));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(sums));
        }

        return sums;
    }

    /**
     * Group the dishes by type.
     *
     * @return  java.util.Map&lt;net.jmp.demo.streams.records.DishType, java.util.List&lt;net.jmp.demo.streams.records.Dish&gt;&gt;
     */
    private Map<DishType, List<Dish>> groupingToList() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        // The default downstream collector is a list

        final Map<DishType, List<Dish>> dishes = streamOfDishes()
                .collect(Collectors.groupingBy(Dish::type));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishes));
        }

        return dishes;
    }

    /**
     * Group the dishes by type
     * but use a set rather than
     * a list.
     *
     * @return  java.util.Map&lt;net.jmp.demo.streams.records.DishType, java.util.Set&lt;net.jmp.demo.streams.records.Dish&gt;&gt;
     */
    private Map<DishType, Set<Dish>> groupingToSet() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        // The default downstream collector is a list

        final Map<DishType, Set<Dish>> dishes = streamOfDishes()
                .collect(Collectors.groupingBy(Dish::type, Collectors.toSet()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishes));
        }

        return dishes;
    }

    /**
     * Group the dishes by type
     * summing their calories.
     *
     * @return  java.util.Map&lt;net.jmp.demo.streams.records.DishType, java.lang.Integer&gt;
     */
    private Map<DishType, Integer> groupingToSum() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Map<DishType, Integer> sums = streamOfDishes()
                .collect(Collectors.groupingBy(Dish::type, Collectors.summingInt(Dish::calories)));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(sums));
        }

        return sums;
    }

    /**
     * Apply a mapping function to the name
     * of each dish and return the list.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> mapping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> names = streamOfDishes()
                .map(Dish::name)
                .collect(Collectors.mapping(this.capitalizer, Collectors.toList()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(names));
        }

        return names;
    }

    /**
     * Apply a flat-mapping function to the dishes.
     *
     * @return  java.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.String&gt;&gt;
     */
    private Map<String, List<String>> flatMapping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        /*
         * Remember flat mapping involves data structures involving
         *  a collection of collections and that its output is
         *  a stream
         */

        final List<Dishes> allDishes = this.getAllDishes();

        /*
         * The two Dishes records are grouped by name and
         * then each list of dishes streamed and mapped to
         * its capitalized dish name and finally collected
         * into a list which is aggregated into the final map.
         * The identity function is a means of just using the
         * incoming element from the stream, in this case, the
         * result of the capitalizer function.
         */

        final Map<String, List<String>> dishNames = allDishes.stream()
                .collect(Collectors.groupingBy(Dishes::name,
                        Collectors.flatMapping(dishes -> dishes.listOfDishes().stream()
                                        .map(Dish::name)
                                        .map(this.capitalizer),
                                Collectors.mapping(Function.identity(), Collectors.toList())
                        )
                ));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(dishNames));
        }

        return dishNames;
    }

    /**
     * Get all the dishes.
     *
     * @return  java.util.List&lt;net.jmp.demo.streams.records.Dishes&gt;
     */
    private List<Dishes> getAllDishes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Dishes favoriteDishes = new Dishes("Favorites",
                List.of(
                        new Dish("pork", false, 800, DishType.MEAT),
                        new Dish("beef", false, 700, DishType.MEAT),
                        new Dish("chicken", false, 400, DishType.MEAT),
                        new Dish("rice", true, 350, DishType.OTHER),
                        new Dish("seasonal fruit", true, 120, DishType.OTHER)
                )
        );

        final Dishes regularDishes = new Dishes("Regular",
                List.of(
                        new Dish("french fries", true, 530, DishType.OTHER),
                        new Dish("pizza", true, 550, DishType.OTHER),
                        new Dish("prawns", false, 300, DishType.FISH),
                        new Dish("salmon", false, 450, DishType.FISH)
                )
        );

        final List<Dishes> allDishes = List.of(favoriteDishes, regularDishes);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(allDishes));
        }

        return allDishes;
    }

    /**
     * Adapt a collector to perform an
     * additional finishing transformation.
     *
     * @return  java.lang.String
     */
    private String collectingAndThen() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final boolean result = streamOfDishes()
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        List::isEmpty   // The finisher returns something different
                ));

        assert !result;

        /*
         * Stream all the dishes mapping each dish to its name.
         * Collect those names while capitalizing them into a
         * list.
         * The finisher transformation streams those capitalized
         * names comparing each for the long string length, which
         * is returned.
         */
        final String longestName = streamOfDishes()
                .map(Dish::name)
                .collect(Collectors.collectingAndThen(
                        Collectors.mapping(this.capitalizer, Collectors.toList()),
                        names -> names.stream()
                                .collect(Collectors.maxBy(Comparator.comparing(String::length)))
                                .orElse("?")
                        )
                );

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(longestName));
        }

        return longestName;
    }

    /**
     * Demonstrate a collector that is a
     * composite of two downstream
     * collectors.
     *
     * @return  hava.util.Map&lt;java.lang.String, java.util.List&lt;java.lang.String&gt;&gt;
     */
    private Map<String, List<String>> teeing() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Map<String, List<String>> results = streamOfDishes()
                .collect(Collectors.teeing(
                        Collectors.filtering(dish -> dish.calories() > 500, Collectors.toList()),
                        Collectors.filtering(dish -> dish.calories() <= 500, Collectors.toList()),
                        (high, low) -> {
                            final List<String> highs = new ArrayList<>();
                            final List<String> lows = new ArrayList<>();

                            high.stream().forEach(d -> highs.add(this.capitalizer.apply(d.name())));
                            low.stream().forEach(d -> lows.add(this.capitalizer.apply(d.name())));

                            final Map<String, List<String>> map = new HashMap<>();

                            map.put("High calorie", highs);
                            map.put("Low calorie", lows);

                            return map;
                        }
                ));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * Demonstrate a collector which performs a reduction
     * of its input elements under a specified BinaryOperator.
     *
     * @return  java.lang.String
     */
    private String reducingToHighestCalorieDish() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Optional<Dish> result = streamOfDishes()
                .collect(Collectors.reducing(
                        BinaryOperator.maxBy(Comparator.comparingInt(Dish::calories))
                ));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        assert result.isPresent();

        return this.capitalizer.apply(result.get().name());
    }

    /**
     * Demonstrate a collector which performs a reduction
     * of its input elements under a specified BinaryOperator
     * using the provided identity.
     *
     * @return  java.lang.String
     */
    private String reducingToLowestCalorieDish() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Dish result = streamOfDishes()
                .collect(Collectors.reducing(new Dish("water", true, 0, DishType.OTHER),
                        BinaryOperator.minBy(Comparator.comparingInt(Dish::calories))
                ));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return this.capitalizer.apply(result.name());
    }

    /**
     * Demonstrate a collector which performs a reduction
     * of its input elements under a specified mapping
     * function and BinaryOperator.
     *
     * @return  int
     */
    private int reducingToShortestName() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final int result = streamOfDishes()
                .collect(Collectors.reducing(14,    // The longest name, seasonal fruit
                        d -> d.name().length(),
                        BinaryOperator.minBy(Comparator.comparingInt(i -> i))
                ));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Demonstrate the droppingWhile custom collector.
     *
     * @return  java.util.List&lt;java.lang.Integer&gt;
     */
    private List<Integer> droppingWhile() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        final Predicate<Integer> p = i -> i == 1;
        final List<Integer> list = integers.collect(CollectorsFactory.droppingWhile(p));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(list));
        }

        return list;
    }

    /**
     * Demonstrate the takingWhile custom collector.
     *
     * @return  java.util.List&lt;java.lang.Integer&gt;
     */
    private List<Integer> takingWhile() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        final Predicate<Integer> p = i -> i == 1;
        final List<Integer> list = integers.collect(CollectorsFactory.takingWhile(p));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(list));
        }

        return list;
    }

    /**
     * Demonstrate the limiting custom collector.
     *
     * @return  java.util.List&lt;java.lang.Integer&gt;
     */
    private List<Integer> limiting() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        final List<Integer> list = integers.collect(CollectorsFactory.limiting(7));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(list));
        }

        return list;
    }

    /**
     * Demonstrate the skipping custom collector.
     *
     * @return  java.util.List&lt;java.lang.Integer&gt;
     */
    private List<Integer> skipping() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        final List<Integer> list = integers.collect(CollectorsFactory.skipping(4));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(list));
        }

        return list;
    }

    /**
     * Demonstrate a collector that
     * collects strings into a deque.
     *
     * @return  java.util.Deque&lt;java.lang.String&gt;
     */
    private Deque<String> toDeque() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Deque<String> names = streamOfDishes()
                .map(Dish::name)
                .map(this.capitalizer)
                .collect(CollectorsFactory.toDeque());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(names));
        }

        return names;
    }

    /**
     * Demonstrate the distinctifying custom collector.
     *
     * @return  java.util.Set&lt;java.lang.Integer&gt;
     */
    private Set<Integer> distinctifying() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 1, 2, 2, 2, 3, 3, 4);
        final Set<Integer> list = integers.collect(CollectorsFactory.distinctifying());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(list));
        }

        return list;
    }
}
