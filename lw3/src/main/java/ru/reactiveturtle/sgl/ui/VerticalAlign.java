package ru.reactiveturtle.sgl.ui;

public final class VerticalAlign {
    public static final int TOP = 1;
    public static final int CENTER = 2;
    public static final int BOTTOM = 4;

    public static boolean isValid(int value) {
        return value == TOP || value == CENTER || value == BOTTOM;
    }
}
