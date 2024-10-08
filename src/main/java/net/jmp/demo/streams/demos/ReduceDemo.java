package net.jmp.demo.streams.demos;

/*
 * (#)ReduceDemo.java   0.10.0  09/24/2024
 * (#)ReduceDemo.java   0.8.0   09/07/2024
 *
 * @author   Jonathan Parker
 * @version  0.10.0
 * @since    0.8.0
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

import java.util.function.BinaryOperator;

import java.util.stream.Stream;

import net.jmp.demo.streams.util.DemoUtils;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates reduction.
 */
public final class ReduceDemo implements Demo {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public ReduceDemo() {
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
            final var sum = "Sum: {}";
            final var product = "Product: {}";

            this.logger.info("Join: {}", this.join());
            this.logger.info(sum, this.sum());
            this.logger.info(product, this.product());

            this.logger.info(sum, this.sumWithIdentity());
            this.logger.info(product, this.productWithIdentity());

            this.logger.info("Calories: {}", this.sumWithBiFunction());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Join two strings using reduce
     * and return the result. This
     * example uses only a binary
     * operator.
     *
     * @return  java.lang.String
     */
    private String join() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<String> words = Stream.of("Hello", ", ", "world!");
        final String result = words.reduce((word1, word2) -> word1 + word2).orElse("");

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Sum a stream of integers using
     * reduce and return the result.
     * This example uses only a binary
     * operator.
     *
     * @return  java.lang.Integer
     */
    private Integer sum() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 2, 3, 5, 8, 13, 21, 34);
        final Integer result = this.reduceIntegers(integers, Integer::sum);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Multiply a stream of integers using
     * reduce and return the result.
     * This example uses only a binary
     * operator.
     *
     * @return  java.lang.Integer
     */
    private Integer product() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 2, 3, 5, 8, 13, 21, 34);
        final Integer result = this.reduceIntegers(integers, (int1, int2) -> int1 * int2);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Sum a stream of integers using
     * reduce with an identity and
     * return the result. This example
     * uses both an identity and a binary
     * operator.
     *
     * @return  java.lang.Integer
     */
    private Integer sumWithIdentity() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 2, 3, 5, 8, 13, 21, 34);
        final Integer result = this.reduceIntegersWithIdentity(integers, 10, Integer::sum);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Multiply a stream of integers using
     * reduce with an identity and return
     * the result.This example uses both an
     * identity and a binary operator.
     *
     * @return  java.lang.Integer
     */
    private Integer productWithIdentity() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 2, 3, 5, 8, 13, 21, 34);
        final Integer result = this.reduceIntegersWithIdentity(integers, 3, (int1, int2) -> int1 * int2);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Sum the calories from a stream of
     * dishes using reduce with an identity,
     * a BiFunction, and a combiner, and
     * return the result. This example
     * uses an identity, a BiFunction, and
     * a binary operator combiner.
     *
     * @return  java.lang.Integer
     */
    private Integer sumWithBiFunction() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Integer result = DemoUtils.streamOfDishes()
                .reduce(0, (partialSum, dish) -> partialSum + dish.calories(), Integer::sum);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Reduce the stream of integers using
     * the binary operator function.
     *
     * @param   integers    java.util.stream.Stream&lt;java.lang.Integer&gt;
     * @param   accumulator java.util.function.BinaryOperator&lt;java.lang.Integer&gt;
     * @return              java.lang.Integer
     */
    private Integer reduceIntegers(final Stream<Integer> integers, final BinaryOperator<Integer> accumulator) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(integers, accumulator));
        }

        final Integer result = integers.reduce(accumulator).orElse(0);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Reduce the stream of integers using
     * the binary operator function.
     *
     * @param   integers    java.util.stream.Stream&lt;java.lang.Integer&gt;
     * @param   identity    java.lang.Integer
     * @param   accumulator java.util.function.BinaryOperator&lt;java.lang.Integer&gt;
     * @return              java.lang.Integer
     */
    private Integer reduceIntegersWithIdentity(final Stream<Integer> integers,
                                               final Integer identity,
                                               final BinaryOperator<Integer> accumulator) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(integers, identity, accumulator));
        }

        final Integer result = integers.reduce(identity, accumulator);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }
}
