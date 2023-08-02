package com.grizz.wooman.test.assertion;

import com.grizz.wooman.test.TestToFail;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;

public class AssertAllExampleTest {
    @Test
    void test1() {
        assertAll(
                () -> {
                },
                () -> {
                },
                () -> {
                }
        );
    }

    @TestToFail
    void test2() {
        Stream<Executable> executables = Stream.of(
                () -> {
                },
                () -> {
                    throw new IllegalStateException();
                }
        );
        assertAll(executables);
    }
}
