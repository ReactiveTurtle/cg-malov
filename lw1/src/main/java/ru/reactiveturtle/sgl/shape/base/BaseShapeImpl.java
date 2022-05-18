package ru.reactiveturtle.sgl.shape.base;

import ru.reactiveturtle.sgl.Transform2DImpl;

public interface BaseShapeImpl extends Transform2DImpl {
    void setStrokeWidth(float strokeWidth);

    float getStrokeWidth();
}
