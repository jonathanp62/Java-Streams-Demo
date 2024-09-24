package net.jmp.demo.streams.spliterators;

/*
 * (#)WordSpliterator.java  0.10.0  09/24/2024
 * (#)WordSpliterator.java  0.9.0   09/11/2024
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

import java.util.Objects;
import java.util.Spliterator;

import java.util.function.Consumer;

import static net.jmp.util.logging.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A word spliterator.
 */
public final class WordSpliterator implements Spliterator<Character> {
    /** The logger. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /** The string of words. */
    private final String string;

    /** The position of the current character in the string. */
    private int currentPosition;

    /**
     * The constructor.
     *
     * @param   string  java.lang.String
     */
    public WordSpliterator(final String string) {
        super();

        this.string = Objects.requireNonNull(string);
        this.currentPosition = 0;
    }

    /**
     * If a remaining element exists: performs the given action on it,
     * returning true; else returns false.
     *
     * @param   action  java.util.function.Consumer&lt;? super T&gt;
     * @return          boolean
     */
    @Override
    public boolean tryAdvance(final Consumer<? super Character> action) {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entryWith(action));
        }

        final String threadName = this.getThreadName();

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("{} currentPosition: {}", threadName, this.currentPosition);
            this.logger.debug("{} stringLength: {}", threadName, this.string.length());
        }

        action.accept(this.string.charAt(this.currentPosition++));  // Consume the current character

        this.logger.debug("{} currentPosition: {}", threadName, this.currentPosition);

        final boolean result = this.currentPosition < this.string.length(); // Return true if there are more characters to consume

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(result));
        }

        return result;
    }

    /**
     * If this spliterator can be partitioned, returns a Spliterator
     * covering elements, that will, upon return from this method,
     * not be covered by this Spliterator.
     *
     * @return  net.jmp.demo.streams.spliterators.WordSpliterator
     */
    @Override
    public WordSpliterator trySplit() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        WordSpliterator spliterator = null;

        final String threadName = this.getThreadName();
        final int currentSize = this.string.length() - this.currentPosition;

        this.logger.debug("{} currentSize: {}", threadName, currentSize);

        if (currentSize < 2) {
            if (this.logger.isTraceEnabled()) {
                this.logger.trace(exitWith(null));
            }

            return null;    // Return null to signal that the string is too small and should be processed sequentially
        }

        for (int splitPos = (currentSize / 2) + this.currentPosition; splitPos < this.string.length(); splitPos++) {
            // White space is a word boundary

            if (Character.isWhitespace(this.string.charAt(splitPos))) {
                spliterator = new WordSpliterator(this.string.substring(this.currentPosition, splitPos));

                this.currentPosition = splitPos;

                this.logger.debug("{} currentPosition: {}", threadName, this.currentPosition);

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace(exitWith(spliterator));
                }

                return spliterator;
            }
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(null));
        }

        return null;
    }

    /**
     * Returns an estimate of the number of elements that would be encountered
     * by a forEachRemaining(java.util.function.Consumer<? super T>) traversal,
     * or returns Long.MAX_VALUE if infinite, unknown, or too expensive to compute.
     *
     * @return  long
     */
    @Override
    public long estimateSize() {
        return this.string.length() - this.currentPosition;
    }

    /**
     * Returns a set of characteristics of this Spliterator and its elements.
     *
     * @return  int
     */
    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }

    /**
     * Return a bracketed thread name.
     *
     * @return  java.lang.String
     */
    private String getThreadName() {
        return String.format("[%s]",Thread.currentThread().getName());
    }
}
