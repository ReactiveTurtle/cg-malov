package ru.reactiveturtle.sgl.shape;

import ru.reactiveturtle.sgl.Transform2DImpl;
import ru.reactiveturtle.sgl.shape.base.BaseShapeImpl;

public interface ShapeImpl extends BaseShapeImpl {
    void setFillColor(int color);

    int getFillColor();

    void setStrokeColor(int color);

    int getStrokeColor();
}
