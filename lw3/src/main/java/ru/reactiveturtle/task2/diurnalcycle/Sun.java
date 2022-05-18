package ru.reactiveturtle.task2.diurnalcycle;

import org.joml.Vector4f;

public class Sun extends Glowing {
    public Sun() {
        super(
                1.5f,
                4f,
                new Vector4f(1f, 1f, 1f, 1f),
                new Vector4f(1f, 1f, 0f, 1f),
                new Vector4f(1f, 1f, 0f, 0f));
        setScale(20f);
    }
}
