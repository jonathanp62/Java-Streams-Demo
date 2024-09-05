package net.jmp.demo.streams.demos;

/*
 * (#)MapMultiDemo.java 0.6.0   09/04/2024
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

import net.jmp.demo.streams.records.Album;
import net.jmp.demo.streams.records.Artist;

import static net.jmp.demo.streams.util.LoggerUtils.*;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

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
            this.flatMapArtistAlbumPairs().forEach(pair -> this.logger.info("flatMap: {}", pair));
            this.mapMultiArtistAlbumPairs().forEach(pair -> this.logger.info("mapMulti: {}", pair));
        }

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exit());
        }
    }

    /**
     * An example that uses filter and
     * map and will be re-implemented
     * using mapMulti.
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
     * An example that uses mapMulti
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
     * An example that uses mapMmulti
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

    /**
     * Return a stream of pairs of artist/album
     * names using flat map. It will be re-implemented
     * using mapMulti.
     *
     * @return  java.util.stream.Stream&lt;org.apache.commons.lang3.tuple.Pair&lt;java.lang.String, java.lang.String&gt;&gt;
     */
    private Stream<Pair<String, String>> flatMapArtistAlbumPairs() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final int upperCost = 20;

        final Stream<Pair<String, String>> artistAlbumPairs = this.getAlbums()
                .flatMap(album -> album.artists()
                        .stream()
                        .filter(artist -> upperCost > album.albumCost())
                        .map(artist -> new ImmutablePair<String, String>(artist.name(), album.albumName())));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(artistAlbumPairs));
        }

        return artistAlbumPairs;
    }

    /**
     * Return a stream of pairs of artist/album
     * names using mapMulti.
     *
     * @return  java.util.stream.Stream&lt;org.apache.commons.lang3.tuple.Pair&lt;java.lang.String, java.lang.String&gt;&gt;
     */
    private Stream<Pair<String, String>> mapMultiArtistAlbumPairs() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Stream<Pair<String, String>> artistAlbumPairs = this.getAlbums()
                .mapMulti((album, consumer) -> album.artists().stream()
                        .forEach(artist -> consumer.accept(new ImmutablePair<>(artist.name(), album.albumName()))));

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(artistAlbumPairs));
        }

        return artistAlbumPairs;
    }

    /**
     * Return a stream of albums.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Album&gt;
     */
    private Stream<Album> getAlbums() {
        if (this.logger.isTraceEnabled()) {
            this.logger.trace(entry());
        }

        final Album album1 = new Album(
                "Dvorak Symphonies",
                15,
                List.of(
                        new Artist(
                                "Colin Davis",
                                true,
                                List.of(
                                        "LSO",
                                        "Phillips"
                                )
                        ),
                        new Artist(
                                "London Symphony Orchestra",
                                true,
                                List.of(
                                        "LSO",
                                        "Warner"
                                )
                        )
                )
        );

        final Album album2 = new Album(
                "Baroque Journey",
                18,
                List.of(
                        new Artist(
                                "Lucie Horsch",
                                true,
                                List.of(
                                        "Alpha",
                                        "Decca"
                                )
                        ),
                        new Artist(
                                "Hanover Band",
                                true,
                                List.of(
                                        "Alpha",
                                        "Erato"
                                )
                        )
                )
        );

        final Album album3 = new Album(
                "New Year's Eve",
                10,
                List.of(
                        new Artist(
                                "Herbert von Karajan",
                                true,
                                List.of(
                                        "DG"
                                )
                        ),
                        new Artist(
                                "Evgeny Kissin",
                                true,
                                List.of(
                                        "DG",
                                        "RCA"
                                )
                        )
                )
        );

        final Stream<Album> albums = Stream.of(album1, album2, album3);

        if (this.logger.isTraceEnabled()) {
            this.logger.trace(exitWith(albums));
        }

        return albums;
    }
}
