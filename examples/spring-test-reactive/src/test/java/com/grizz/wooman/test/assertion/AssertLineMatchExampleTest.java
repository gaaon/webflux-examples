package com.grizz.wooman.test.assertion;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

public class AssertLineMatchExampleTest {
    @Test
    void test1() {
        List<String> expected = List.of("abc", "[a-z]+", "[0-9]+");
        List<String> actual = List.of("abc", "abc", "123");
        assertLinesMatch(expected, actual);
    }
    @Test
    void test2() {
        Stream<String> expected = Stream.of("abc", "[a-z]+", "[0-9]+");
        Stream<String> actual = Stream.of("abc", "abc", "123");
        assertLinesMatch(expected, actual);
    }
}
