package ru.reactiveturtle.sgl.shape.base;

import ru.reactiveturtle.sgl.ITransform2D;

public interface BaseShapeImpl extends ITransform2D {
    void setStrokeWidth(float strokeWidth);

    float getStrokeWidth();
}
