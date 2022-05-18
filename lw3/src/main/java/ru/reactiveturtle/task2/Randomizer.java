package ru.reactiveturtle.task2;

import java.util.Random;

public class Randomizer {
    private static Random RANDOM;

    public static Random getInstance() {
        if (RANDOM == null) {
            RANDOM = new Random(4742352);
        }
        return RANDOM;
    }

    public static int random(int min, int max) {
        return Math.round(random((float) min, (float) max));
    }

    public static float random(float min, float max) {
        return RANDOM.nextFloat() * (max - min) + min;
    }
}
