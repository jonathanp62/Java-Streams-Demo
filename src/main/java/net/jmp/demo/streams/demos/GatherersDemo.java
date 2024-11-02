package net.jmp.demo.streams.demos;

/*
 * (#)GatherersDemo.java    0.12.0  10/28/2024
 * (#)GatherersDemo.java    0.11.0  10/26/2024
 * (#)GatherersDemo.java    0.10.0  09/24/2024
 * (#)GatherersDemo.java    0.7.0   09/05/2024
 *
 * @author   Jonathan Parker
 * @version  0.12.0
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

import java.util.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import java.util.stream.*;

import net.jmp.demo.streams.gatherers.MapNotNullGatherer;
import net.jmp.demo.streams.gatherers.ReduceByGatherer;

import net.jmp.demo.streams.records.Money;
import net.jmp.demo.streams.records.Offer;

import net.jmp.demo.streams.util.GatherersFactory;

import static net.jmp.util.logging.LoggerUtils.*;

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

        if (this.logger.isInfoEnabled()) {
            this.customDistinctByGatherer(this.getMoney()).forEach(e -> this.logger.info("DistinctBy: {}", e));
            this.customReduceByGatherer(this.getMoney()).forEach(e -> this.logger.info("ReduceBy: {}", e));

            this.logger.info("MaxBy: {}", this.customMaxByGatherer(this.getMoney()));
            this.logger.info("MinBy: {}", this.customMinByGatherer(this.getMoney()));

            this.customMinByGatherer(this.getMoney());
            this.customMapNotNullGatherer().forEach(e -> this.logger.info("NotNull: {}", e));

            this.logger.info("First: {}", this.customFindFirstGatherer(this.getMoney()));
            this.logger.info("Last: {}", this.customFindLastGatherer(this.getMoney()));

            this.customGatherAndThen().forEach(e -> this.logger.info("AndThen: {}", e));
            this.customOfSequential().forEach(e -> this.logger.info("Sequential: {}", e));

            this.logger.info("Accumulator: {}", this.customOf());

            this.customComposed().forEach(e -> this.logger.info("Composed: {}", e));

            this.logger.info("PrefixScan: {}", this.prefixScan());
            this.logger.info("ReversePrefixScan: {}", this.reversePrefixScan());
            this.logger.info("Limiting: {}", this.tryLimitingAndRange());
            this.logger.info("Ranges: {}", this.tryRanges());
        }

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
     * @return          java.util.List&lt;java.lang.String&gt;
     */
    private List<String> customDistinctByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

        final List<String> results = money.gather(GatherersFactory.distinctBy(Money::currency))
                .map(Money::currency)
                .map(Currency::toString)
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A custom reduce-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     * @return          java.util.List&lt;java.lang.String&gt;
     */
    private List<String> customReduceByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

        final List<String> results = money.gather(GatherersFactory.reduceBy(Money::currency, Money::add))
                .map(Record::toString)
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A custom max-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     * @return          java.lang.String
     */
    private String customMaxByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

        final String result = money
                .gather(GatherersFactory.maxBy(Money::amount))
                .map(Record::toString)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this.getFirstElementFromList()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * A custom min-by gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     * @return          java.lang.String
     */
    private String customMinByGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

        final String result = money
                .gather(GatherersFactory.minBy(Money::amount))
                .map(Record::toString)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this.getFirstElementFromList()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * A custom map not-null gatherer.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> customMapNotNullGatherer() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Money> money = this.getMoneyWithNulls();

        final List<String> results = money.gather(GatherersFactory.mapNotNull(m -> m.multiply(BigDecimal.TWO)))
                .map(Record::toString)
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A custom find-first gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     * @return          java.lang.String
     */
    private String customFindFirstGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

        final String result = money.gather(GatherersFactory.findFirst(m -> m.currency().equals(Currency.getInstance("PLN"))))
                .map(Record::toString)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this.getFirstElementFromList()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * A custom find-last gatherer.
     *
     * @param   money   java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     * @return          java.lang.String
     */
    private String customFindLastGatherer(final Stream<Money> money) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(money));
        }

        assert money != null;

        final Function<List<String>, String> getElement = List::getFirst;

        final String result = money.gather(GatherersFactory.findLast(m -> m.currency().equals(Currency.getInstance("PLN"))))
                .map(Record::toString)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this.getFirstElementFromList()));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Try two gatherers using andThen.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     */
    private List<String> customGatherAndThen() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Money> money = this.getMoneyWithNulls();

        // Combine two gatherers using andThen()

        final MapNotNullGatherer<Money, Money> mapNotNullGatherer = new MapNotNullGatherer<>(m -> m.multiply(BigDecimal.TWO));
        final ReduceByGatherer<Money, Currency> reducerGatherer = new ReduceByGatherer<>(Money::currency, Money::add);

        final Gatherer<Money, ?, Money> gatherers = mapNotNullGatherer.andThen(reducerGatherer);

        final List<String> results = money.gather(gatherers)
                .map(Record::toString)
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * Demonstrate creating a gatherer using ofSequential.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     * @since   0.11.0
     */
    private List<String> customOfSequential() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Offer grandChildOffer1 = new Offer("GP1", List.of());
        final Offer grandChildOffer2 = new Offer("GP1", List.of());
        final Offer grandChildOffer3 = new Offer("GP2", List.of());
        final Offer grandChildOffer4 = new Offer("GP3", List.of());
        final Offer childOffer1 = new Offer("CP1", List.of(grandChildOffer1, grandChildOffer4));
        final Offer childOffer2 = new Offer("CP2", List.of(grandChildOffer2, grandChildOffer3));
        final Offer offer = new Offer("P1", List.of(childOffer1, childOffer2));

        final List<Offer> distinctGrandChildOffers = getAllDistinctGrandChildOffersByProductCode(offer);

        final List<String> distinctGrandChildProductCodes = distinctGrandChildOffers
                .stream()
                .map(Offer::productCode)
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(distinctGrandChildProductCodes));
        }

        return distinctGrandChildProductCodes;
    }

    /**
     * Return a list of distinct grandchild offers by product code.
     *
     * @param   offer   net.jmp.demo.streams.records.Offer
     * @return          java.util.List&lt;net.jmp.demo.streams.records.Offer&gt;
     * @since           0.11.0
     */
    private List<Offer> getAllDistinctGrandChildOffersByProductCode(final Offer offer) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(offer));
        }

        final List<Offer> offers = offer.childOffers()
                .stream()
                .flatMap(childOffer -> childOffer.childOffers().stream())
                .gather(distinctByProductCodeGatherer())
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(offers));
        }

        return offers;
    }

    /**
     * A gatherer that produces a stream of offers and
     * produces a stream of grandchild offers distinct
     * by product code.
     *
     * @return  java.util.stream.Gatherer&lt;net.jmp.demo.streams.records.Offer, java.util.List&lt;net.jmp.demo.streams.records.Offer&gt;,net.jmp.demo.streams.records.Offer&gt;
     * @since   0.11.0
     */
    private Gatherer<Offer, List<Offer>, Offer> distinctByProductCodeGatherer() {
        return Gatherer.ofSequential(
                // Initializer
                ArrayList::new,

                // Integrator
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    if (!hasProductWithSameProductCode(state, element)) {
                        state.add(element);
                    }

                    return true;
                }),

                // Finisher
                (state, downstream) -> {
                    if (!state.isEmpty() && !downstream.isRejecting()) {
                        state.forEach(downstream::push);
                    }
                }
        );
    }

    /**
     * Return true if the element matches any in the state list.
     *
     * @param   state   java.util.List&lt;net.jmp.demo.streams.records.Offer&gt;
     * @param   element net.jmp.demo.streams.records.Offer
     * @return          boolean
     * @since           0.11.0
     */
    private boolean hasProductWithSameProductCode(final List<Offer> state, final Offer element) {
        return state.stream().anyMatch(offer -> offer.productCode().equals(element.productCode()));
    }

    /**
     * Demonstrate creating a gatherer using of.
     *
     * @return  int
     * @since   0.11.0
     */
    private int customOf() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final int numberOfItems = 1_000;

        final List<Integer> integers = IntStream.rangeClosed(1, numberOfItems)
                .boxed()
                .toList();

        // A gatherer MUST have a combiner function for a stream to be processed in parallel.
        // The default combiner turns off parallelization.

        final int result = integers.stream()
                .parallel()
                .gather(accumulate(numberOfItems))
                .findFirst()
                .orElse(0);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(result));
        }

        return result;
    }

    /**
     * A gatherer that accumulates a stream of integers into a single value.
     * This gatherer implements the combiner function and so can be parallelized.
     *
     * @param   numberOfItems   int
     * @return                  int
     * @since                   0.11.0
     */
    private Gatherer<Integer, List<Integer>, Integer> accumulate(final int numberOfItems) {
        return Gatherer.of(
                // Initializer
                () -> new ArrayList<>(numberOfItems),

                // Integrator
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    state.add(element);

                    return true;
                }),

                // Combiner
                (left, right) -> {
                    int accumulator = 0;

                    for (final var element : left) {
                        accumulator += element;
                    }

                    for (final var element : right) {
                        accumulator += element;
                    }

                    final List<Integer> result = new ArrayList<>(1);

                    result.add(accumulator);

                    return result;
                },

                // Finisher
                (state, downstream) -> {
                    if (!state.isEmpty() && !downstream.isRejecting()) {
                        downstream.push(state.getFirst());
                    }
                }
        );
    }

    /**
     * More custom composed gatherers.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     * @since   0.12.0
     */
    private List<String> customComposed() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Gatherer<Integer, ?, Integer> increment = this.map(i -> i + 1);
        final Gatherer<Integer, ?, String> asString = this.map(Object::toString);
        final Gatherer<Integer, ?, String> incrementThenAsString = increment.andThen(asString);

        final List<Integer> integers = List.of(1, 2, 3);
        final List<String> results = integers
                .stream()
                .gather(incrementThenAsString)
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A mapping gatherer that only requires an integrator.
     *
     * @param   <T>     The type of element
     * @param   <R>     The return type
     * @param   mapper  java.util.function.Function&lt;? super T, ? extends R&gt;
     * @return          java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since           0.12.0
     */
    private <T, R> Gatherer<T, ?, R> map(final Function <? super T, ? extends R> mapper) {
        return Gatherer.of(
                // Integrator (the return value of downstream.push() is returned)
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> downstream.push(mapper.apply(element))
        ));
    }

    /**
     * Demonstrate a prefix scan gatherer.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     * @since   0.12.0
     */
    private List<String> prefixScan() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Supplier<String> supplier = () -> "";
        final BiFunction<String, Integer, String> function = (string, number) -> string + number;

        final List<String> results = Stream.of(9, 8, 7, 6, 5, 4, 3, 2, 1)
                .gather(this.scan(supplier, function))
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A prefix scan gatherer.
     *
     * @param   <T>         The type of element
     * @param   <R>         The return type
     * @param   supplier    java,util.function.Supplier&lt;R&gt;
     * @param   scanner     java.util.function.BiFunction&lt;? super R, ? super T, ? extends R&gt;
     * @return              java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since               0.12.0
     */
    private <T, R> Gatherer<T, ?, R> scan(final Supplier<R> supplier,
                                          final BiFunction<? super R, ? super T, ? extends R> scanner) {
        class State {
            /** The value of the current state. */
            R currentState = supplier.get();
        }

        return Gatherer.<T, State, R>ofSequential(
            // Initializer
            State::new,

            // Integrator
            Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                state.currentState = scanner.apply(state.currentState, element);

                return downstream.push(state.currentState);
            })
        );
    }

    /**
     * Demonstrate a reverse prefix scan gatherer.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     * @since   0.12.0
     */
    private List<String> reversePrefixScan() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Supplier<String> supplier = () -> "";
        final BiFunction<String, Integer, String> function = (string, number) -> string + number;

        final List<String> results = Stream.of(9, 8, 7, 6, 5, 4, 3, 2, 1)
                .gather(this.reverseScan(supplier, function))
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A reverse prefix scan gatherer.
     *
     * @param   <T>         The type of element
     * @param   <R>         The return type
     * @param   supplier    java,util.function.Supplier&lt;R&gt;
     * @param   scanner     java.util.function.BiFunction&lt;? super R, ? super T, ? extends R&gt;
     * @return              java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since               0.12.0
     */
    private <T, R> Gatherer<T, ?, R> reverseScan(final Supplier<R> supplier,
                                                 final BiFunction<? super R, ? super T, ? extends R> scanner) {
        class State {
            /** The list of elements. */
            final List<R> elements = new ArrayList<>();

            /** The current value of the state. */
            R current = supplier.get();
        }

        return Gatherer.<T, State, R>ofSequential(
                // Initializer
                State::new,

                // Integrator
                Gatherer.Integrator.of((state, element, downstream) -> {
                    state.current = scanner.apply(state.current, element);

                    state.elements.add(state.current);

                    return true;
                }),

                // Finisher
                (state, downstream) -> {
                    Collections.reverse(state.elements);

                    for (final R element : state.elements) {
                        if (!downstream.isRejecting()) {
                            downstream.push(element);
                        }
                    }
                }
        );
    }

    /**
     * Demonstrate a limiting gatherer. This method also
     * demonstrates using multiple gatherers in a single
     * operation.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     * @since   0.12.0
     */
    private List<String> tryLimitingAndRange() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Supplier<String> supplier = () -> "";
        final BiFunction<String, Integer, String> function = (string, number) -> string + number;

        final List<String> results = Stream.of(9, 8, 7, 6, 5, 4, 3, 2, 1)
                .gather(this.reverseScan(supplier, function)
                        .andThen(this.limiting(5))
                        .andThen(this.range(1))
                )
                // Is the same as:
//                .gather(this.reverseScan(supplier, function))
//                .gather(this.limiting(5))
//                .gather(this.range(1))
                .toList();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A gatherer that limits the number of
     * items processed to the specified value.
     *
     * @param   <T>     The type of element
     * @param   limit   int
     * @return          java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since           0.12.0
     */
    private <T> Gatherer<T, ?, T> limiting(final int limit) {
        class State {
            /** The number of items processed so far. */
            int count;
        }

        return Gatherer.<T, State, T>ofSequential(
                // The initializer
                State::new,

                // The integrator
                Gatherer.Integrator.of((state, element, downstream) -> {
                    state.count += 1;

                    if (state.count > limit) {
                        return false;
                    } else {
                        return downstream.push(element);
                    }
                })
        );
    }

    /**
     * A gatherer that returns the range of
     * elements starting with the start index
     * (zero based) and continuing to the
     * end of the stream.
     *
     * @param   <T>     The type of element
     * @param   start   int
     * @return          java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since           0.12.0
     */
    private <T> Gatherer<T, ?, T> range(final int start) {
        class State {
            /** The number of elements processed so far. */
            int count;

            /** A list of elements to push downstream. */
            final List<T> elements = new ArrayList<>();
        }

        if (start < 0) {
            throw new IllegalArgumentException("Start must be zero or greater");
        }

        return Gatherer.<T, State, T>ofSequential(
                // The initializer
                State::new,

                // The integrator
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    if (state.count++ >= start) {
                        state.elements.add(element);
                    }

                    return true;
                }),

                // The finisher
                (state, downstream) -> {
                    for (final T element : state.elements) {
                        if (!downstream.isRejecting()) {
                            downstream.push(element);
                        }
                    }
                }
        );
    }

    /**
     * Demonstrate open and closed ranges. This method also
     * demonstrates using multiple gatherers in a single
     * operation.
     *
     * @return  java.util.List&lt;java.lang.String&gt;
     * @since   0.12.0
     */
    private List<String> tryRanges() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Gatherer<String, ?, String> gatherer1 = this.range(1, 7);
        final Gatherer<String, ?, String> gatherer2 = this.rangeClosed(2, 3);
        final Gatherer<String, ?, String> gatherers = gatherer1.andThen(gatherer2);

        final List<String> results = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .map(i -> i + "a")
                .gather(gatherers)
                // Is the same as:
//                .gather(this.range(1, 7))
//                .gather(this.rangeClosed(2, 3))
                .toList();

        /*
         * This code does not compile:
            final List<String> results = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                    .map(i -> i + "a")
                    .gather(this.range(1, 7)
                        .andThen(this.rangeClosed(2, 3)
                     )
                    .toList();
         */

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * A gatherer that returns the range of
     * elements starting with the start index
     * (zero based) and continuing to the
     * item specified by the end index but
     * excluding that element.
     *
     * @param   <T>             The type of element
     * @param   start           int
     * @param   endExclusive    int
     * @return                  java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since                   0.12.0
     */
    private <T> Gatherer<T, ?, T> range(final int start, final int endExclusive) {
        class State {
            /** The number of elements processed so far. */
            int count;

            /** A list of elements to push downstream. */
            final List<T> elements = new ArrayList<>();
        }

        if (start < 0) {
            throw new IllegalArgumentException("Start must be zero or greater");
        }

        if (start > endExclusive) {
            throw new IllegalArgumentException("Start must be less than end (exclusive)");
        }

        return Gatherer.<T, State, T>ofSequential(
                // The initializer
                State::new,

                // The integrator
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    if (state.count >= start && state.count < endExclusive) {
                        state.elements.add(element);
                    }

                    state.count = state.count + 1;

                    return true;
                }),

                // The finisher
                (state, downstream) -> {
                    for (final T element : state.elements) {
                        if (!downstream.isRejecting()) {
                            downstream.push(element);
                        }
                    }
                }
        );
    }

    /**
     * A gatherer that returns the range of
     * elements starting with the start index
     * (zero based) and continuing to the
     * item specified by the end index and
     * including that element.
     *
     * @param   <T>             The type of element
     * @param   start           int
     * @param   endInclusive    int
     * @return                  java.util.stream.Gatherer&lt;T, ?, R&gt;
     * @since                   0.12.0
     */
    private <T> Gatherer<T, ?, T> rangeClosed(final int start, final int endInclusive) {
        class State {
            /** The number of elements processed so far. */
            int count;

            /** A list of elements to push downstream. */
            final List<T> elements = new ArrayList<>();
        }

        if (start < 0) {
            throw new IllegalArgumentException("Start must be zero or greater");
        }

        if (start > endInclusive) {
            throw new IllegalArgumentException("Start must be less than end (inclusive)");
        }

        return Gatherer.<T, State, T>ofSequential(
                // The initializer
                State::new,

                // The integrator
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    if (state.count >= start && state.count <= endInclusive) {
                        state.elements.add(element);
                    }

                    state.count = state.count + 1;

                    return true;
                }),

                // The finisher
                (state, downstream) -> {
                    for (final T element : state.elements) {
                        if (!downstream.isRejecting()) {
                            downstream.push(element);
                        }
                    }
                }
        );
    }

    /**
     * Return a stream of money.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private Stream<Money> getMoney() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Money> money = Stream.of(
                new Money(BigDecimal.valueOf(12), Currency.getInstance("PLN")),
                new Money(BigDecimal.valueOf(11), Currency.getInstance("EUR")),
                new Money(BigDecimal.valueOf(15), Currency.getInstance("PLN"))
        );

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(money));
        }

        return money;
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

    /**
     * Return a function to get the
     * first and only element from a
     * list.
     *
     * @param   <T> The type of element
     * @return      java.util.function.Function&lt;java.util.List&lt;T&gt;&gt;
     */
    private <T> Function<List<T>, T> getFirstElementFromList() {
        return List::getFirst;
    }
}
