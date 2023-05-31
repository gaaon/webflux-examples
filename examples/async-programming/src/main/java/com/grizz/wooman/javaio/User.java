package com.grizz.wooman.javaio;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private final String name;
    private final int age;
}
