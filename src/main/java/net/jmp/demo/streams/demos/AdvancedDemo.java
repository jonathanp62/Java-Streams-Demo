package net.jmp.demo.streams.demos;

/*
 * (#)AdvancedDemo.java 0.5.0   09/04/2024
 * (#)AdvancedDemo.java 0.4.0   08/30/2024
 * (#)AdvancedDemo.java 0.3.0   08/29/2024
 *
 * @author   Jonathan Parker
 * @version  0.5.0
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

import java.util.List;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import java.util.stream.Stream;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import net.jmp.demo.streams.records.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates more advanced concepts.
 *
 * Demonstrations:
 *   builder(*)
 *   dropWhile(*)
 *   flatMap(*)
 *   generate(*)
 *   iterate(*)
 *   takeWhile(*)
 */
public final class AdvancedDemo implements Demo {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public AdvancedDemo() {
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
            this.dropWhile().forEach(e -> this.logger.info("Drop: {}", e));
            this.takeWhile().forEach(e -> this.logger.info("Take: {}", e));
            this.generate().forEach(e -> this.logger.info("{}", e));
            this.iterateNumbers().forEach(e -> this.logger.info("{}", e));
            this.iterateNumbersWithPredicate().forEach(e -> this.logger.info("{}", e));
            this.buildDishes().forEach(e -> this.logger.info("{}", e));
            this.flatMap().forEach(e -> this.logger.info("{}", e));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate dropWhile. The first three ones
     * are dropped leaving the twos and three.
     * Drop-while is really intended for ordered
     * streams.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> dropWhile() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 2, 2, 3);
        final Stream<Integer> results = integers.dropWhile(i -> i == 1);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * Demonstrate takeWhile. The first ones are
     * taken and the twos and three are dropped.
     * Take-while is really intended for
     * ordered streams.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> takeWhile() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 2, 2, 3);
        final Stream<Integer> results = integers.takeWhile(i -> i == 1);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * Generate a stream of random
     * numbers, limiting it to five
     * elements.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Double&gt;
     */
    private Stream<Double> generate() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Supplier<Double> supplier = Math::random;
        final Stream<Double> stream = Stream.generate(supplier).limit(5);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(stream));
        }

        return stream;
    }

    /**
     * Iterate a stream of integers
     * from 1 to 5.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> iterateNumbers() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> stream = Stream.iterate(1, i -> i + 1).limit(5);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(stream));
        }

        return stream;
    }

    /**
     * Iterate a stream of integers
     * from 1 to 5 using a predicate
     * function instead of limit.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> iterateNumbersWithPredicate() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final UnaryOperator<Integer> next = i -> i + 1;
        final Predicate<Integer> hasNext = i -> i <= 5;
        final Stream<Integer> stream = Stream.iterate(1, hasNext, next);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(stream));
        }

        return stream;
    }

    /**
     * Use a stream builder to
     * build a stream of dishes.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.records.Dish&gt;
     */
    private Stream<Dish> buildDishes() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream.Builder<Dish> builder = Stream.builder();

        builder.add(new Dish("pork", false, 800, DishType.MEAT))
                .add(new Dish("beef", false, 700, DishType.MEAT))
                .add(new Dish("chicken", false, 400, DishType.MEAT))
                .add(new Dish("french fries", true, 530, DishType.OTHER))
                .add(new Dish("rice", true, 350, DishType.OTHER))
                .add(new Dish("seasonal fruit", true, 120, DishType.OTHER))
                .add(new Dish("pizza", true, 550, DishType.OTHER))
                .add(new Dish("prawns", false, 300, DishType.FISH))
                .add(new Dish("salmon", false, 450, DishType.FISH));

        final Stream<Dish> stream = builder.build();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(stream));
        }

        return stream;
    }

    /**
     * Demonstrate flat map.
     *
     * @return  java.util.stream.Stream&lt;java.lang.String&gt;
     */
    private Stream<String> flatMap() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        /*
         * Remember flat mapping involves data structures involving
         *  a collection of collections and that its output is
         *  a stream
         */

        final Stream<String> stream = this.getAllDishes().stream()
                .flatMap(dishes -> dishes.listOfDishes().stream()
                        .map(Dish::name)
                );

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(stream));
        }

        return stream;
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

        final Dishes favoriteDishes = new Dishes("Protein",
                List.of(
                        new Dish("pork", false, 800, DishType.MEAT),
                        new Dish("beef", false, 700, DishType.MEAT),
                        new Dish("chicken", false, 400, DishType.MEAT),
                        new Dish("prawns", false, 300, DishType.FISH),
                        new Dish("salmon", false, 450, DishType.FISH)
                )
        );

        final Dishes regularDishes = new Dishes("Other",
                List.of(
                        new Dish("french fries", true, 530, DishType.OTHER),
                        new Dish("pizza", true, 550, DishType.OTHER),
                        new Dish("rice", true, 350, DishType.OTHER),
                        new Dish("seasonal fruit", true, 120, DishType.OTHER)
                )
        );

        final List<Dishes> allDishes = List.of(favoriteDishes, regularDishes);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(allDishes));
        }

        return allDishes;
    }
}
