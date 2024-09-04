package net.jmp.demo.streams.demos;

/*
 * (#)MultiMapDemo.java 0.6.0   09/04/2024
 *
 * @author   Jonathan Parker
 * @version  0.6.0
 * @since    0.6.0
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

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates map-multi operation.
 */
public final class MapMultiDemo implements Demo {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public MapMultiDemo() {
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
            this.filterAndMap().forEach(integer -> this.logger.info("filterAndMap: {}", integer));
            this.mapMulti().forEach(integer -> this.logger.info("mapMulti: {}", integer));
            this.mapMultiToDouble().forEach(integer -> this.logger.info("mapMulti2Dbl: {}", integer));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * An example that uses filter and
     * map and will be re-implemented
     * using multiMap.
     *
     * @return  java.util.stream.Stream&lt;java.lang.Double&gt;
     */
    private Stream<Double> filterAndMap() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);
        final double percentage = .01;

        final Stream<Double> evenDoubles = integers.stream()
                .filter(integer -> integer % 2 == 0)
                .<Double>map(integer -> ((double) integer * (1 + percentage)));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(evenDoubles));
        }

        return evenDoubles;
    }

    /**
     * An example that uses multiMap
     * instead of filter and map as
     * above. MapMulti is more direct,
     * requiring fewer intermediate
     * strean operations (like filter).
     *
     * @return  java.util.stream.Stream&lt;java.lang.Double&gt;
     */
    private Stream<Double> mapMulti() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);
        final double percentage = .01;

        final Stream<Double> oddDoubles = integers.stream()
                .<Double>mapMulti((integer, consumer) -> {
                    if (integer % 2 != 0) {
                        consumer.accept((double) integer * (1 + percentage));
                    }
                });

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(oddDoubles));
        }

        return oddDoubles;
    }

    /**
     * An example that uses multiMap
     * and returns a double stream.
     *
     * @return  java.util.stream.DoubleStream
     */
    private DoubleStream mapMultiToDouble() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = List.of(1, 2, 3, 4, 5, 6);
        final double percentage = .01;

        final DoubleStream doubles = integers.stream()
                .mapMultiToDouble((integer, consumer) -> {
                    consumer.accept((double) integer * (integer + percentage));
                });

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(doubles));
        }

        return doubles;
    }
}
