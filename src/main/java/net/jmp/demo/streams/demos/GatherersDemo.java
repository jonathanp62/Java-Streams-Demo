package net.jmp.demo.streams.demos;

/*
 * (#)GatherersDemo.java    0.7.0   09/05/2024
 *
 * @author   Jonathan Parker
 * @version  0.7.0
 * @since    0.7.0
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

import java.math.BigDecimal;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import java.util.function.Function;

import java.util.stream.Gatherers;
import java.util.stream.Stream;

import net.jmp.demo.streams.gatherers.*;

import net.jmp.demo.streams.records.Money;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates gatherers.
 */
public final class GatherersDemo implements Demo {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public GatherersDemo() {
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

        this.standardGatherers();
        this.customGatherers();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate the standard gatherers
     * provided by the language.
     */
    private void standardGatherers() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isInfoEnabled()) {
            this.slidingWindow().forEach(window -> this.logger.info("Sliding: {}", window));
            this.fixedWindow().forEach(window -> this.logger.info("Fixed: {}", window));
            this.scan().forEach(item -> this.logger.info("Scan: {}", item));

            this.logger.info("Fold: {}", this.fold());

            this.mapConcurrent().forEach(item -> this.logger.info("Map: {}", item));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate custom-built gatherers.
     */
    private void customGatherers() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Money> money = Stream.of(
                new Money(BigDecimal.valueOf(12), Currency.getInstance("PLN")),
                new Money(BigDecimal.valueOf(11), Currency.getInstance("EUR")),
                new Money(BigDecimal.valueOf(15), Currency.getInstance("PLN"))
        );

        this.customDistinctBy(money);
        this.customReduceByGatherer(money);
        this.customMaxByGatherer(money);
        this.customMinByGatherer(money);
        this.customMapNotNullGatherer();
        this.customFindFirstGatherer(money);
        this.customFindLastGatherer(money);
        this.customGatherAndThen();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Sliding window. Demonstrate a gatherer that
     * gathers elements into windows, encounter-ordered
     * groups of elements, of a given size, where each
     * subsequent window includes all elements of the
     * previous window except for the least recent, and
     * adds the next element in the stream.
     *
     * @return  java.util.List&lt;java.util.List&lt;java.lang.String&gt;&gt;
     */
    private List<List<String>> slidingWindow() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> countries = List.of("India", "Poland", "UK", "Australia", "USA", "Netherlands");

        final List<List<String>> windows = countries
                .stream()
                .gather(Gatherers.windowSliding(3))
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(windows));
        }

        return windows;
    }

    /**
     * Fixed window. Demonstrate a gatherer that gathers
     * elements into windows, encounter-ordered groups of
     * elements, of a fixed size.
     *
     * @return  java.util.List&lt;java.util.List&lt;java.lang.String&gt;&gt;
     */
    private List<List<String>> fixedWindow() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> composers = List.of("Mozart", "Bach", "Beethoven", "Mahler", "Bruckner", "Liszt", "Chopin", "Telemann", "Vivaldi");

        final List<List<String>> windows = composers
                .stream()
                .gather(Gatherers.windowFixed(2))
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(windows));
        }

        return windows;
    }

    /**
     * Scan. Demonstrate a gatherer that performs a prefix scan,
     * an incremental accumulation, using the provided functions.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> scan() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<String> numbers = Stream.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9
        ).gather(
                Gatherers.scan(() -> "", (string, number) -> string + number)
        ).toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(numbers));
        }

        return numbers;
    }

    /**
     * Fold. Demonstrate a gatherer that performs an ordered,
     * reduction-like, transformation for scenarios where
     * no combiner-function can be implemented, or for
     * reductions which are intrinsically order-dependent.
     *
     * @return  java.lang.String
     */
    private String fold() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String numbers = Stream.of(
                1, 2, 3, 4, 5, 6, 7, 8, 9
        ).gather(
                Gatherers.fold(() -> "", (string, number) -> string + number)
        ).findFirst().get();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(numbers));
        }

        return numbers;
    }

    /**
     * Map concurrent. An operation which executes a function
     * concurrently with a configured level of max concurrency,
     * using virtual threads.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> mapConcurrent() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

        final Function<Integer, String> toString = String::valueOf;

        final List<String> strings = numbers
                .stream()
                .gather(Gatherers.mapConcurrent(2, toString))
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(strings));
        }

        return strings;
    }

    /**
     * A custom distinct-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private void customDistinctBy(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

//        money.stream()
//                .gather(GatherersFactory.distinctBy(Money::currency))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A custom reduce-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private void customReduceByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

//        money.stream()
//                .gather(GatherersFactory.reduceBy(Money::currency, Money::add))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A custom max-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private void customMaxByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

//        money.stream()
//                .parallel()
//                .gather(GatherersFactory.maxBy(Money::amount))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A custom min-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private void customMinByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

//        money.stream()
//                .parallel()
//                .gather(GatherersFactory.minBy(Money::amount))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A custom map not-null gatherer.
     */
    private void customMapNotNullGatherer() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Money> money = this.getMoneyWithNulls();

//        money.stream()
//                .gather(GatherersFactory.mapNotNull(m -> m.multiply(BigDecimal.TWO)))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A custom find-first gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private void customFindFirstGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

//        money.stream()
//                .gather(GatherersFactory.findFirst(m -> m.currency().equals(Currency.getInstance("PLN"))))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * A custom find-last gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private void customFindLastGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

//        money.stream()
//                .gather(GatherersFactory.findLast(m -> m.currency().equals(Currency.getInstance("PLN"))))
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Try two gatherers using andThen.
     */
    private void customGatherAndThen() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Money> money = this.getMoneyWithNulls();

//        // Combine two gatherers using andThen()
//
//        final MapNotNullGatherer<Money, Money> mapNotNullGatherer = new MapNotNullGatherer<>(m -> m.multiply(BigDecimal.TWO));
//        final ReduceByGatherer<Money, Currency> reducerGatherer = new ReduceByGatherer<>(Money::currency, Money::add);
//
//        final Gatherer<Money, ?, ? super Money> gatherers = mapNotNullGatherer.andThen(reducerGatherer);
//
//        money.stream()
//                .gather(gatherers)
//                .forEach(e -> this.logger.info(e.toString()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Return a stream of money with nulls interspersed.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private Stream<Money> getMoneyWithNulls() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        // Cannot add nulls in List.of()

        final List<Money> money = Arrays.asList(
                null,
                new Money(BigDecimal.valueOf(12), Currency.getInstance("PLN")),
                null,
                new Money(BigDecimal.valueOf(11), Currency.getInstance("EUR")),
                null,
                new Money(BigDecimal.valueOf(15), Currency.getInstance("PLN")),
                null
        );

        final Stream<Money> stream = money.stream();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(stream));
        }

        return stream;
    }
}
