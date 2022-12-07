package ru.keellere.pojo;

import java.util.Arrays;
import java.util.Random;

public enum Space {
    LEFT,
    RIGHT,
    FORWARD,
    BACKWARD,
    UP,
    DOWN;


    public static Space randomSpace() {
        var rnd = new Random();
        var spaces = Arrays.asList(Space.values());
        return spaces.get(rnd.nextInt(spaces.size()));
    }
}
