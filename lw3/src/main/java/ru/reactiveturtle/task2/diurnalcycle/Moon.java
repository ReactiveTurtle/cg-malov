package ru.reactiveturtle.task2.diurnalcycle;

import org.joml.Vector4f;

public class Moon extends Glowing{
    public Moon() {
        super(
                2f,
                0.5f,
                new Vector4f(0.95f, 0.95f, 1f, 1f),
                new Vector4f(0.95f, 0.95f, 1f, 1f),
                new Vector4f(0.95f, 0.95f, 1f, 0f));
        setScale(20f);
    }
}
