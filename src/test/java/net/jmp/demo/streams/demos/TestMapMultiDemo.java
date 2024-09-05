package net.jmp.demo.streams.demos;

/*
 * (#)TestMapMultiDemo.java 0.6.0   09/05/2024
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

import static net.jmp.demo.streams.testutil.TestUtils.castToType;
import static net.jmp.demo.streams.testutil.TestUtils.streamToTypedList;

import org.apache.commons.lang3.tuple.Pair;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMapMultiDemo {
    @Test
    public void testFilterAndMap() throws Exception {
        final var demo = new MapMultiDemo();
        final var method = MapMultiDemo.class.getDeclaredMethod("filterAndMap");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Double> doubles = streamToTypedList(stream, Double.class);

        assertNotNull(doubles);
        assertEquals(3, doubles.size());
        assertEquals(2.02, doubles.get(0), 0.01);
        assertEquals(4.04, doubles.get(1), 0.01);
        assertEquals(6.06, doubles.get(2), 0.01);
    }

    @Test
    public void testMapMulti() throws Exception {
        final var demo = new MapMultiDemo();
        final var method = MapMultiDemo.class.getDeclaredMethod("mapMulti");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final Stream<?> stream = castToType(Stream.class, o);
        final List<Double> doubles = streamToTypedList(stream, Double.class);

        assertNotNull(doubles);
        assertEquals(3, doubles.size());
        assertEquals(1.01, doubles.get(0), 0.01);
        assertEquals(3.03, doubles.get(1), 0.01);
        assertEquals(5.05, doubles.get(2), 0.01);
    }

    @Test
    public void testMapMultiToDouble() throws Exception {
        final var demo = new MapMultiDemo();
        final var method = MapMultiDemo.class.getDeclaredMethod("mapMultiToDouble");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final DoubleStream stream = (DoubleStream) o;
        final double[] doubles = stream.toArray();

        assertNotNull(doubles);
        assertEquals(6, doubles.length);
        assertEquals(1.01, doubles[0], 0.01);
        assertEquals(4.02, doubles[1], 0.01);
        assertEquals(9.03, doubles[2], 0.01);
        assertEquals(16.04, doubles[3], 0.01);
        assertEquals(25.05, doubles[4], 0.01);
        assertEquals(36.06, doubles[5], 0.01);
    }

    @Test
    public void testFlatMapArtistAlbumPairs() throws Exception {
        final var demo = new MapMultiDemo();
        final var method = MapMultiDemo.class.getDeclaredMethod("flatMapArtistAlbumPairs");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Stream<Pair<String, String>> pairs = (Stream<Pair<String, String>>) method.invoke(demo);

        final List<Pair<String, String>> list = pairs.toList();

        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals(Pair.of("Colin Davis", "Dvorak Symphonies"), list.get(0));
        assertEquals(Pair.of("London Symphony Orchestra", "Dvorak Symphonies"), list.get(1));
        assertEquals(Pair.of("Lucie Horsch", "Baroque Journey"), list.get(2));
        assertEquals(Pair.of("Hanover Band", "Baroque Journey"), list.get(3));
        assertEquals(Pair.of("Herbert von Karajan", "New Year's Eve"), list.get(4));
        assertEquals(Pair.of("Evgeny Kissin", "New Year's Eve"), list.get(5));
    }

    @Test
    public void testMapMultiArtistAlbumPairs() throws Exception {
        final var demo = new MapMultiDemo();
        final var method = MapMultiDemo.class.getDeclaredMethod("mapMultiArtistAlbumPairs");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Stream<Pair<String, String>> pairs = (Stream<Pair<String, String>>) method.invoke(demo);

        final List<Pair<String, String>> list = pairs.toList();

        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals(Pair.of("Colin Davis", "Dvorak Symphonies"), list.get(0));
        assertEquals(Pair.of("London Symphony Orchestra", "Dvorak Symphonies"), list.get(1));
        assertEquals(Pair.of("Lucie Horsch", "Baroque Journey"), list.get(2));
        assertEquals(Pair.of("Hanover Band", "Baroque Journey"), list.get(3));
        assertEquals(Pair.of("Herbert von Karajan", "New Year's Eve"), list.get(4));
        assertEquals(Pair.of("Evgeny Kissin", "New Year's Eve"), list.get(5));
    }

    @Test
    public void testMapMultiCopyrightedArtistAlbum() throws Exception {
        final var demo = new MapMultiDemo();
        final var method = MapMultiDemo.class.getDeclaredMethod("mapMultiCopyrightedArtistAlbum");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final Stream<Pair<String, String>> pairs = (Stream<Pair<String, String>>) method.invoke(demo);

        final List<Pair<String, String>> list = pairs.toList();

        assertNotNull(list);
        assertEquals(6, list.size());
        assertEquals(Pair.of("Colin Davis: Dvorak Symphonies", "LSO, Phillips"), list.get(0));
        assertEquals(Pair.of("London Symphony Orchestra: Dvorak Symphonies", "LSO, Warner"), list.get(1));
        assertEquals(Pair.of("Lucie Horsch: Baroque Journey", "Alpha, Decca"), list.get(2));
        assertEquals(Pair.of("Hanover Band: Baroque Journey", "Alpha, Erato"), list.get(3));
        assertEquals(Pair.of("Herbert von Karajan: New Year's Eve", "DG"), list.get(4));
        assertEquals(Pair.of("Evgeny Kissin: New Year's Eve", "DG, RCA"), list.get(5));
    }
}
