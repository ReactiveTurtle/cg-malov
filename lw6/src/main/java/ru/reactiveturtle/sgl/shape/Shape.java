package ru.reactiveturtle.sgl.shape;

import ru.reactiveturtle.sgl.shape.base.BaseShape;

public abstract class Shape extends BaseShape<ShapeShaderProgram> implements ShapeImpl {
    private int fillColor = 0;
    private int strokeColor = 0;

    public Shape(
            float[] fillVertices,
            int[] fillIndices,
            float[] strokeVertices,
            int[] strokeIndices) {
        super(fillVertices, fillIndices, strokeVertices, strokeIndices);
    }

    @Override
    public void setFillColor(int color) {
        fillColor = color;
    }

    @Override
    public int getFillColor() {
        return fillColor;
    }

    @Override
    public void setStrokeColor(int color) {
        this.strokeColor = color;
    }

    @Override
    public int getStrokeColor() {
        return strokeColor;
    }
}
