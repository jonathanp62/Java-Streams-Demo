package net.jmp.demo.streams.demos;

/*
 * (#)AdvancedDemo.java 0.4.0   08/30/2024
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

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class the demonstrates collectors.
 *
 * Demonstrations:
 *   Stream.collect() using the following collectors:
 *     averaging...()
 *     collectingAndThen()
 *     counting()
 *     filtering()
 *     flatMapping()
 *     groupingBy()
 *     joining()
 *     mapping()
 *     maxBy()
 *     minBy()
 *     partitioningBy()
 *     reducing()
 *     summarizing...()
 *     summing...()
 *     teeing()
 *     toConcurrentMap()
 *     toList()
 *     toMap()
 *     toSet()
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

        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }
}
