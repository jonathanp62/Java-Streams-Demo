package net.jmp.demo.streams.records;

/*
 * (#)Album.java    0.6.0   09/05/2024
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

import java.util.function.Consumer;

import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * The album record.
 */
public record Album(
        String albumName,
        int albumCost,
        List<Artist> artists
) {
    /**
     * Method that passes all the artist-album pairs with
     * their associated major labels to a consumer. Used
     * in the MapMultiDemo class.
     *
     * @param   consumer    java.util.function.Consumer&lt;org.apache.commons.lang3.tuple.Pair&lt;java.lang.String, java.lang.String&gt;&gt;
     */
    public void artistAlbumPairsToMajorLabels(final Consumer<Pair<String, String>> consumer) {
        artists.stream()
                .filter(Artist::associatedWithMajorLabels)
                .forEach(artist -> {
                    final String labels = artist.majorLabels().stream()
                            .collect(Collectors.joining(", "));

                    consumer.accept(new ImmutablePair<>(artist.name() + ": " + this.albumName, labels));
                });
    }
}
