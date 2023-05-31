package com.grizz.wooman.completablefuture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public class MethodReferenceExample {
    @RequiredArgsConstructor
    public static class Person {
        @Getter
        private final String name;

        public Boolean compareTo(Person o) {
            return o.name.compareTo(name) > 0;
        }
    }

    public static void print(String name) {
        System.out.println(name);
    }

    public static void main(String[] args) {
        var target = new Person("f");
        Consumer<String> staticPrint = MethodReferenceExample::print;

        Stream.of("a", "b", "g", "h")
                .map(Person::new)// constructor reference
                .filter(target::compareTo) // method reference
                .map(Person::getName) // instance method reference
                .forEach(staticPrint); // static method reference
    }
}
