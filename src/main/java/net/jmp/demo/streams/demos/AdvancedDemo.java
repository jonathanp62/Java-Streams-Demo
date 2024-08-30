package net.jmp.demo.streams.demos;

/*
 * (#)AdvancedDemo.java 0.3.0   08/29/2024
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

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import java.util.stream.Stream;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates more advanced concepts.
 *
 * Demonstrations:
 */
public final class AdvancedDemo implements Demo {
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
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate dropWhile. The first three ones
     * are dropped leaving the twos and three.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> dropWhile() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 2, 2, 3, 1);
        final Stream<Integer> results = integers.dropWhile(i -> i == 1);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * Demonstrate takeWhile. The first ones are
     * taken and the twos and three and final one
     * dropped.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Integer&gt;
     */
    private Stream<Integer> takeWhile() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Integer> integers = Stream.of(1, 1, 1, 2, 2, 3, 1);
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
}
