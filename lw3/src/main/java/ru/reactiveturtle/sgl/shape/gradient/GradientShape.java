package ru.reactiveturtle.sgl.shape.gradient;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.shader.ColorShaderProgram;
import ru.reactiveturtle.sgl.shape.base.BaseShape;
import ru.reactiveturtle.sgl.utils.BufferUtils;

public abstract class GradientShape extends BaseShape<ColorShaderProgram> implements GradientShapeImpl {
    private final int fillColorsBufferId;
    private final int strokeColorsBufferId;

    private float[] fillColors;
    private float[] strokeColors;

    public GradientShape(
            float[] fillVertices,
            int[] fillIndices,
            float[] fillColors,
            float[] strokeVertices,
            int[] strokeIndices,
            float[] strokeColors) {
        super(fillVertices, fillIndices, strokeVertices, strokeIndices);

        fillColorsBufferId = GL15.glGenBuffers();
        setFillColors(fillColors);

        strokeColorsBufferId = GL15.glGenBuffers();
        setStrokeColors(strokeColors);
    }

    public void setFillColors(float[] fillColors) {
        this.fillColors = fillColors;
        GL30.glBindVertexArray(getFillVertexArrayId());
        BufferUtils.storeArrayBuffer(fillColorsBufferId, fillColors, 1, 3);
        GL30.glBindVertexArray(0);
    }

    @Override
    public float[] getFillColors() {
        return fillColors;
    }

    @Override
    public void setStrokeColors(float[] strokeColors) {
        this.strokeColors = strokeColors;
        GL30.glBindVertexArray(getStrokeVertexArrayId());
        BufferUtils.storeArrayBuffer(strokeColorsBufferId, strokeColors, 1, 3);
        GL30.glBindVertexArray(0);
    }

    @Override
    public float[] getStrokeColors() {
        return strokeColors;
    }

    @Override
    public void dispose() {
        GL15.glDeleteBuffers(new int[]{
                fillColorsBufferId,
                strokeColorsBufferId
        });
        super.dispose();
    }

    public int getFillColorsBufferId() {
        return fillColorsBufferId;
    }
}
