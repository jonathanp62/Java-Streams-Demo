package net.jmp.demo.streams.beans;

/*
 * (#)Article.java  0.9.0   09/09/2024
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

import java.util.Objects;

public final class Article {
    /** The title. */
    private String title;

    /**
     * The default constructor.
     */
    public Article() {
        super();
    }

    /**
     * Return the title.
     *
     * @return  java.lang.String
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Set the title.
     *
     * @param   title   java.lang.String
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * The equals method.
     *
     * @param   o   java.lang.Object
     * @return      boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        final Article article = (Article) o;

        return Objects.equals(this.title, article.title);
    }

    /**
     * The hash-code method.
     *
     * @return  int
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(this.title);
    }

    /**
     * The to-string method.
     *
     * @return  java.lang.String
     */
    @Override
    public String toString() {
        return "Article{" +
                "title='" + this.title + '\'' +
                '}';
    }
}
