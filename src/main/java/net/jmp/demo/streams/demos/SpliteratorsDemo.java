package net.jmp.demo.streams.demos;

/*
 * (#)SpliteratorsDemo.java 0.9.0   09/09/2024
 *
 * @author   Jonathan Parker
 * @version  0.9.0
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

import java.util.List;

import java.util.Spliterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.jmp.demo.streams.beans.Article;

import static net.jmp.demo.streams.util.LoggerUtils.*;

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

        if (this.logger.isInfoEnabled()) {
            this.tryAdvance().stream()
                    .limit(1)
                    .forEach(article -> this.logger.info("Article: {}", article.getTitle()));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * Demonstrate the main method for stepping through a sequence.
     *
     * @return  java.util.List&lt;net.jmp.demo.streams.beans.Article&gt;
     */
    private List<Article> tryAdvance() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Article> articles = this.getListOfArticles();

        articles.spliterator()
                .tryAdvance(article -> article.setTitle("By Jonathan"));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(articles));
        }

        return articles;
    }

    private void trySplit() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final List<Article> articles = this.getListOfArticles();

        final Spliterator<Article> split1 = articles.spliterator();
        final Spliterator<Article> split2 = split1.trySplit();

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(articles));
        }

//        return articles;
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
}
