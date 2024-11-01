package net.jmp.demo.streams.demos;

/*
 * (#)SpliteratorsDemo.java 0.10.0  09/24/2024
 * (#)SpliteratorsDemo.java 0.9.0   09/09/2024
 *
 * @author   Jonathan Parker
 * @version  0.10.0
 * @since    0.9.0
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

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

import java.util.concurrent.ForkJoinPool;

import java.util.concurrent.atomic.AtomicInteger;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import net.jmp.demo.streams.beans.Article;

import net.jmp.demo.streams.spliterators.*;

import static net.jmp.demo.streams.util.SpliteratorUtils.*;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates spliterators.
 */
public final class SpliteratorsDemo implements Demo {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * The default constructor.
     */
    public SpliteratorsDemo() {
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

        this.standardMethods();
        this.customSpliterators();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate the standard methods
     * of the spliterator.
     */
    private void standardMethods() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isInfoEnabled()) {
            this.tryAdvance().stream()
                    .limit(1)
                    .forEach(article -> this.logger.info(article.getTitle()));

            final List<List<Article>> lists = this.trySplit();

            lists.forEach(list -> list.stream()
                    .limit(1)
                    .forEach(article -> this.logger.info(article.getTitle())));

            this.logger.info("Split1/2 estimated size: {}", this.estimateSize());
            this.logger.info("Split1/2 characteristics: {}", this.characteristics());

            this.logCharacteristics(this.characteristics());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate custom spliterators.
     */
    private void customSpliterators() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        if (this.logger.isInfoEnabled()) {
            this.logger.info("Sum: {}", this.customListSpliterator());
            this.logger.info("Sum: {}", this.customListSpliteratorInParallel());

            this.logger.info("Words: {}", this.customWordSpliterator());

            this.logger.info("Uneven sum: {}", this.customListSpliteratorUsingForkJoinPoolUnevenly());
            this.logger.info("Even sum: {}", this.customListSpliteratorUsingForkJoinPoolEvenly());
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate the custom list spliterator synchronously.
     * Return the accumulated sum of the integers from 1-100.
     *
     * @return int
     */
    private int customListSpliterator() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = IntStream.rangeClosed(1, 100)
                .boxed()
                .toList();

        final ListSpliterator<Integer> listSpliterator = new ListSpliterator<>(integers);
        final AtomicInteger sum = new AtomicInteger(0);

        listSpliterator.forEachRemaining(sum::addAndGet);   // Non-parallel; never issues trySplit()

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(sum.get()));
        }

        return sum.get();
    }

    /**
     * Demonstrate the custom list spliterator in parallel.
     * Return the accumulated sum of the integers from 1-100.
     *
     * @return int
     */
    private int customListSpliteratorInParallel() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = IntStream.rangeClosed(1, 100)
                .boxed()
                .toList();

        final ListSpliterator<Integer> listSpliterator = new ListSpliterator<>(integers);
        final ListSpliterator<Integer> split2 = listSpliterator.trySplit();

        final AtomicInteger sum = new AtomicInteger(0);

        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        if (split2 != null) {
            final ListSpliterator<Integer> split3 = split2.trySplit();

            if (split3 != null) {
                final var task1 = forkJoinPool.submit(() -> listSpliterator.forEachRemaining(sum::addAndGet));
                final var task2 = forkJoinPool.submit(() -> split2.forEachRemaining(sum::addAndGet));
                final var task3 = forkJoinPool.submit(() -> split3.forEachRemaining(sum::addAndGet));

                task3.join();   // Each task processes smaller and smaller amounts of data
                task2.join();
                task1.join();
            } else {
                this.logger.warn("split3 is null");

                final var task1 = forkJoinPool.submit(() -> listSpliterator.forEachRemaining(sum::addAndGet));
                final var task2 = forkJoinPool.submit(() -> split2.forEachRemaining(sum::addAndGet));

                task2.join();   // Each task processes smaller and smaller amounts of data
                task1.join();
            }
        } else {
            this.logger.warn("split2 is null");

            forkJoinPool.submit(() -> listSpliterator.forEachRemaining(sum::addAndGet)).join();
        }

        forkJoinPool.shutdown();
        forkJoinPool.close();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(sum.get()));
        }

        return sum.get();
    }

    /**
     * Demonstrate the custom word spliterator using
     * StreamSupport. Return the number of words found
     * in the sentence.
     *
     * @return  int
     */
    private int customWordSpliterator() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final String sentences = "Lorem ipsum dolor sit amet, consectetur adipiscing " +
                "elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut " +
                "aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in " +
                "voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint " +
                "occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim " +
                "id est laborum.";

        final WordSpliterator spliterator = new WordSpliterator(sentences);
        final Stream<Character> stream = StreamSupport.stream(spliterator, true);

        final int result = this.countWords(stream);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Demonstrate recursive uneven splitting of the
     * list spliterator using the fork join pool.
     *
     * @return  int
     */
    private int customListSpliteratorUsingForkJoinPoolUnevenly() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = IntStream.rangeClosed(1, 1_000)
                .boxed()
                .toList();

        final AtomicInteger sum = new AtomicInteger(0);
        final ListSpliterator<Integer> spliterator = new ListSpliterator<>(integers);

        splitAndConsumeUnevenly(spliterator, sum::addAndGet);

        final int result = sum.get();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Demonstrate recursive even splitting of the
     * list spliterator using the fork join pool.
     *
     * @return  int
     */
    private int customListSpliteratorUsingForkJoinPoolEvenly() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Integer> integers = IntStream.rangeClosed(1, 1_000)
                .boxed()
                .toList();

        final AtomicInteger sum = new AtomicInteger(0);
        final ListSpliterator<Integer> spliterator = new ListSpliterator<>(integers);

        final int result = splitAndConsumeEvenly(spliterator, sum::addAndGet, sum::get);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Method to count the characters
     * in the stream.
     *
     * @param   stream  java.util.stream.Stream&lt;java.lang.Character&gt;
     * @return          int
     */
    private int countWords(final Stream<Character> stream) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(stream));
        }

        final WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
                WordCounter::accumulate,
                WordCounter::combine);

        final int result = wordCounter.getCounter();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * Demonstrate the main method tryAdvance() for stepping through a sequence.
     *
     * @return  java.util.List&lt;net.jmp.demo.streams.beans.Article&gt;
     */
    private List<Article> tryAdvance() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Article> articles = this.getListOfArticles();

        articles.spliterator()
                .tryAdvance(article -> article.setTitle("Advanced by Jonathan"));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(articles));
        }

        return articles;
    }

    /**
     * Demonstrate the method trySplit() for partitioning.
     *
     * @return  java.util.List&lt;java.util.List&lt;net.jmp.demo.streams.beans.Article&gt;&gt;
     */
    private List<List<Article>> trySplit() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Spliterator<Article> split1 = this.getListOfArticles().spliterator();
        final Spliterator<Article> split2 = split1.trySplit();

        final List<Article> list1 = new ArrayList<>(17_500);
        final List<Article> list2 = new ArrayList<>(17_500);

        // Consume the articles in the spliterators

        split1.forEachRemaining(e -> {
            e.setTitle("Split1 by Jonathan");
            list1.add(e);
        });

        split2.forEachRemaining(e -> {
            e.setTitle("Split2 by Jonathan");
            list2.add(e);
        });

        final List<List<Article>> results = List.of(list1, list2);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(results));
        }

        return results;
    }

    /**
     * Return the estimate size of the two splits.
     *
     * @return  long
     */
    private long estimateSize() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Spliterator<Article> split1 = this.getListOfArticles().spliterator();
        final Spliterator<Article> split2 = split1.trySplit();

        assert split1.estimateSize() == split2.estimateSize();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(split2.estimateSize()));
        }

        return split2.estimateSize();
    }

    /**
     * Return the characteristics of the two splits.
     *
     * @return  int
     */
    private int characteristics() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Spliterator<Article> split1 = this.getListOfArticles().spliterator();
        final Spliterator<Article> split2 = split1.trySplit();

        assert split1.characteristics() == split2.characteristics();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(split2.characteristics()));
        }

        return split2.characteristics();
    }

    /**
     * Log the individual spliterator characteristics.
     *
     * @param   characteristics int
     */
    private void logCharacteristics(final int characteristics) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(characteristics));
        }

        if ((characteristics & Spliterator.CONCURRENT) > 0) {
            this.logger.info("Spliterator is concurrent");
        }

        if ((characteristics & Spliterator.DISTINCT) > 0) {
            this.logger.info("Spliterator is distinct");
        }

        if ((characteristics & Spliterator.IMMUTABLE) > 0) {
            this.logger.info("Spliterator is immutable");
        }

        if ((characteristics & Spliterator.NONNULL) > 0) {
            this.logger.info("Spliterator is non-null");
        }

        if ((characteristics & Spliterator.ORDERED) > 0) {
            this.logger.info("Spliterator is ordered");
        }

        if ((characteristics & Spliterator.SIZED) > 0) {
            this.logger.info("Spliterator is sized");
        }

        if ((characteristics & Spliterator.SORTED) > 0) {
            this.logger.info("Spliterator is sorted");
        }

        if ((characteristics & Spliterator.SUBSIZED) > 0) {
            this.logger.info("Spliterator is sub-sized");
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Return a generated list of articles.
     *
     * @return  java.util.List&lt;net.jmp.demo.streams.beans.Article&gt;
     */
    private List<Article> getListOfArticles() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Article> articles = Stream.generate(Article::new)
                .limit(35_000)
                .collect(Collectors.toList());

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(articles));
        }

        return articles;
    }

    /**
     * A word counter class.
     */
    static class WordCounter {
        /** The counter. */
        private final int counter;

        /** True when the last space is encountered. */
        private final boolean lastSpace;

        /**
         * The constructor.
         *
         * @param   counter     int
         * @param   lastSpace   boolean
         */
        WordCounter(final int counter, final boolean lastSpace) {
            super();

            this.counter = counter;
            this.lastSpace = lastSpace;
        }

        /**
         * The accumulate method.
         *
         * @param   c   java.lang.Character
         * @return      net.jmp.demo.streams.spliterators.WordSpliterator.WordCounter
         */
        WordCounter accumulate(final Character c) {
            if (Character.isWhitespace(c)) {
                return this.lastSpace ? this : new WordCounter(this.counter, true);
            } else {
                return this.lastSpace ? new WordCounter(this.counter + 1, false) : this;
            }
        }

        /**
         * Method to combine two word-counters
         * by summing their counters.
         *
         * @param   wordCounter net.jmp.demo.streams.spliterators.WordSpliterator.WordCounter
         * @return              net.jmp.demo.streams.spliterators.WordSpliterator.WordCounter
         */
        WordCounter combine(final WordCounter wordCounter) {
            return new WordCounter(this.counter + wordCounter.counter, wordCounter.lastSpace);
        }

        /**
         * Method to return the counter.
         *
         * @return int
         */
        int getCounter() {
            return this.counter;
        }
    }
}
