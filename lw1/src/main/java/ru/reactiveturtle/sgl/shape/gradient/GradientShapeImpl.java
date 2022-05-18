package ru.reactiveturtle.sgl.shape.gradient;

import ru.reactiveturtle.sgl.Transform2DImpl;
import ru.reactiveturtle.sgl.shape.base.BaseShapeImpl;

public interface GradientShapeImpl extends BaseShapeImpl {
    void setFillColors(float[] colors);

    float[] getFillColors();

    void setStrokeColors(float[] colors);

    float[] getStrokeColors();
}
