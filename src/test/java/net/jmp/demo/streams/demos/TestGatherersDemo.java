package net.jmp.demo.streams.demos;

/*
 * (#)TestGatherersDemo.java    0.11.0  10/26/2024
 * (#)TestGatherersDemo.java    0.10.0  09/24/2024
 * (#)TestGatherersDemo.java    0.7.0   09/05/2024
 *
 * @author   Jonathan Parker
 * @version  0.11.0
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

import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import net.jmp.demo.streams.records.Money;

import static net.jmp.util.testing.testutil.TestUtils.*;

import static org.junit.Assert.*;

import org.junit.Test;

public final class TestGatherersDemo {
    @Test
    public void testSlidingWindow() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("slidingWindow");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final List<List<String>> windows = (List<List<String>>) method.invoke(demo);

        assertNotNull(windows);
        assertEquals(4, windows.size());

        List<String> expected0 = List.of("India", "Poland", "UK");
        List<String> expected1 = List.of("Poland", "UK", "Australia");
        List<String> expected2 = List.of("UK", "Australia", "USA");
        List<String> expected3 = List.of("Australia", "USA", "Netherlands");

        assertEquals(expected0, windows.get(0));
        assertEquals(expected1, windows.get(1));
        assertEquals(expected2, windows.get(2));
        assertEquals(expected3, windows.get(3));
    }

    @Test
    public void testFixedWindow() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("fixedWindow");

        method.setAccessible(true);

        @SuppressWarnings("unchecked")
        final List<List<String>> windows = (List<List<String>>) method.invoke(demo);

        assertNotNull(windows);
        assertEquals(5, windows.size());

        List<String> expected0 = List.of("Mozart", "Bach");
        List<String> expected1 = List.of("Beethoven", "Mahler");
        List<String> expected2 = List.of("Bruckner", "Liszt");
        List<String> expected3 = List.of("Chopin", "Telemann");
        List<String> expected4 = List.of("Vivaldi");

        assertEquals(expected0, windows.get(0));
        assertEquals(expected1, windows.get(1));
        assertEquals(expected2, windows.get(2));
        assertEquals(expected3, windows.get(3));
        assertEquals(expected4, windows.get(4));
    }

    @Test
    public void testScan() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("scan");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final List<?> list = castToType(List.class, o);
        final List<String> scans = listToTypedList(list, String.class);

        assertNotNull(scans);
        assertEquals(9, scans.size());

        assertEquals("1", scans.get(0));
        assertEquals("12", scans.get(1));
        assertEquals("123", scans.get(2));
        assertEquals("1234", scans.get(3));
        assertEquals("12345", scans.get(4));
        assertEquals("123456", scans.get(5));
        assertEquals("1234567", scans.get(6));
        assertEquals("12345678", scans.get(7));
        assertEquals("123456789", scans.get(8));
    }

    @Test
    public void testFold() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("fold");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final String result = castToType(String.class, o);

        assertNotNull(result);
        assertEquals("123456789", result);
    }

    @Test
    public void testMapConcurrent() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("mapConcurrent");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final List<?> list = castToType(List.class, o);
        final List<String> results = listToTypedList(list, String.class);

        assertNotNull(results);
        assertEquals(9, results.size());

        assertEquals("1", results.get(0));
        assertEquals("2", results.get(1));
        assertEquals("3", results.get(2));
        assertEquals("4", results.get(3));
        assertEquals("5", results.get(4));
        assertEquals("6", results.get(5));
        assertEquals("7", results.get(6));
        assertEquals("8", results.get(7));
        assertEquals("9", results.get(8));
    }

    @Test
    public void testDistinctBy() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customDistinctByGatherer", Stream.class);

        method.setAccessible(true);

        final Object o = method.invoke(demo, this.getMoney());
        final List<?> list = castToType(List.class, o);
        final List<String> results = listToTypedList(list, String.class);

        assertNotNull(results);
        assertEquals(2, results.size());
        assertTrue(results.contains("PLN"));
        assertTrue(results.contains("EUR"));
    }

    @Test
    public void testReduceBy() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customReduceByGatherer", Stream.class);

        method.setAccessible(true);

        final Object o = method.invoke(demo, this.getMoney());
        final List<?> list = castToType(List.class, o);
        final List<String> results = listToTypedList(list, String.class);

        assertNotNull(results);
        assertEquals(2, results.size());

        final Money expected0 = new Money(new BigDecimal(11), Currency.getInstance("EUR"));
        final Money expected1 = new Money(new BigDecimal(27), Currency.getInstance("PLN"));

        assertTrue(results.contains(expected0.toString()));
        assertTrue(results.contains(expected1.toString()));
    }

    @Test
    public void testMaxBy() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customMaxByGatherer", Stream.class);

        method.setAccessible(true);

        final Object o = method.invoke(demo, this.getMoney());
        final String string = castToType(String.class, o);

        assertNotNull(string);

        final Money expected = new Money(new BigDecimal(15), Currency.getInstance("PLN"));

        assertEquals(expected.toString(), string);
    }

    @Test
    public void testMinBy() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customMinByGatherer", Stream.class);

        method.setAccessible(true);

        final Object o = method.invoke(demo, this.getMoney());
        final String string = castToType(String.class, o);

        assertNotNull(string);

        final Money expected = new Money(new BigDecimal(11), Currency.getInstance("EUR"));

        assertEquals(expected.toString(), string);
    }

    @Test
    public void testMapNotNull() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customMapNotNullGatherer");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final List<?> list = castToType(List.class, o);
        final List<String> results = listToTypedList(list, String.class);

        assertNotNull(results);
        assertEquals(3, results.size());

        final Money expected0 = new Money(new BigDecimal(24), Currency.getInstance("PLN"));
        final Money expected1 = new Money(new BigDecimal(22), Currency.getInstance("EUR"));
        final Money expected2 = new Money(new BigDecimal(30), Currency.getInstance("PLN"));

        assertTrue(results.contains(expected0.toString()));
        assertTrue(results.contains(expected1.toString()));
        assertTrue(results.contains(expected2.toString()));
    }

    @Test
    public void testFindFirst() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customFindFirstGatherer", Stream.class);

        method.setAccessible(true);

        final Object o = method.invoke(demo, this.getMoney());
        final String string = castToType(String.class, o);

        assertNotNull(string);

        final Money expected = new Money(new BigDecimal(12), Currency.getInstance("PLN"));

        assertEquals(expected.toString(), string);
    }

    @Test
    public void testFindLast() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customFindLastGatherer", Stream.class);

        method.setAccessible(true);

        final Object o = method.invoke(demo, this.getMoney());
        final String string = castToType(String.class, o);

        assertNotNull(string);

        final Money expected = new Money(new BigDecimal(15), Currency.getInstance("PLN"));

        assertEquals(expected.toString(), string);
    }

    @Test
    public void testGatherAndThen() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customGatherAndThen");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final List<?> list = castToType(List.class, o);
        final List<String> results = listToTypedList(list, String.class);

        assertNotNull(results);
        assertEquals(2, results.size());

        final Money expected0 = new Money(new BigDecimal(54), Currency.getInstance("PLN"));
        final Money expected1 = new Money(new BigDecimal(22), Currency.getInstance("EUR"));

        assertTrue(results.contains(expected0.toString()));
        assertTrue(results.contains(expected1.toString()));
    }

    @Test
    public void testOfSequential() throws Exception {
        final var demo = new GatherersDemo();
        final var method = GatherersDemo.class.getDeclaredMethod("customOfSequential");

        method.setAccessible(true);

        final Object o = method.invoke(demo);
        final List<?> list = castToType(List.class, o);
        final List<String> results = listToTypedList(list, String.class);

        assertNotNull(results);
        assertEquals(3, results.size());

        assertTrue(results.contains("GP1"));
        assertTrue(results.contains("GP2"));
        assertTrue(results.contains("GP3"));
    }

    /**
     * Return a stream of money.
     *
     * @return  java.util.stream.Stream&lt;net.jmp.demo.streams.records.Money&gt;
     */
    private Stream<Money> getMoney() {
        return Stream.of(
                new Money(BigDecimal.valueOf(12), Currency.getInstance("PLN")),
                new Money(BigDecimal.valueOf(11), Currency.getInstance("EUR")),
                new Money(BigDecimal.valueOf(15), Currency.getInstance("PLN"))
        );
    }
}
