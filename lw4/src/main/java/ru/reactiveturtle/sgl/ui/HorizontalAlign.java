package ru.reactiveturtle.sgl.ui;

public final class HorizontalAlign {
    public static final int LEFT = 1;
    public static final int CENTER = 2;
    public static final int RIGHT = 4;

    public static boolean isValid(int value) {
        return value == LEFT || value == CENTER || value == RIGHT;
    }
}
