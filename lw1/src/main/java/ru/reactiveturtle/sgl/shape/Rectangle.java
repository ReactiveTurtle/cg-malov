package ru.reactiveturtle.sgl.shape;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Color;

public class Rectangle extends Shape {
    private float width;
    private float height;
    private Vector2f boxSize;

    public Rectangle(float width, float height, float strokeWidth) {
        super(
                getFillVertices(width, height),
                getFillIndices(),
                getStrokeVertices(width, height, strokeWidth),
                getStrokeIndices());
        this.width = width;
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
        super.setFillVertices(getFillVertices(width, height), getFillIndices());
        super.setStrokeVertices(getStrokeVertices(width, height, getStrokeWidth()), getStrokeIndices());
    }

    public void setHeight(float height) {
        this.height = height;
        super.setFillVertices(getFillVertices(width, height), getFillIndices());
        super.setStrokeVertices(getStrokeVertices(width, height, getStrokeWidth()), getStrokeIndices());
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getScaleWidth() {
        return width * getScaleX();
    }

    public float getScaleHeight() {
        return height * getScaleY();
    }

    @Override
    public void setStrokeWidth(float strokeWidth) {
        super.setStrokeWidth(strokeWidth);
        super.setStrokeVertices(getStrokeVertices(width, height, getStrokeWidth()), getStrokeIndices());
    }

    @Override
    public void draw(ShapeShaderProgram shapeShader) {
        GL30.glBindVertexArray(getFillVertexArrayId());
        GL20.glEnableVertexAttribArray(0);
        shapeShader.loadColor(new Color(getFillColor()).getComponentsAsVector());
        GL20.glDrawElements(GL11.GL_TRIANGLES, getFillVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);

        GL30.glBindVertexArray(getStrokeVertexArrayId());
        GL20.glEnableVertexAttribArray(0);
        shapeShader.loadColor(new Color(getStrokeColor()).getComponentsAsVector());
        GL20.glDrawElements(GL11.GL_TRIANGLES, getStrokeVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public Vector2f getBoxSize() {
        if (boxSize != null) {
            return new Vector2f(boxSize);
        }
        double sin = Math.abs(Math.sin(getRotationRadians()));
        double cos = Math.abs(Math.cos(getRotationRadians()));
        float boxHeight = (float) (width * sin + height * cos);
        float boxWidth = (float) (width * cos + height * sin);
        boxSize = new Vector2f(boxWidth, boxHeight);
        return new Vector2f(boxSize);
    }

    private static float[] getFillVertices(float width, float height) {
        return new float[]{
                0, 0,
                0, height,
                width, height,
                width, 0
        };
    }

    private static int[] getFillIndices() {
        return new int[]{
                0, 1, 2,
                0, 2, 3
        };
    }

    private static float[] getStrokeVertices(
            float width,
            float height,
            float strokeWidth) {
        return new float[]{
                0, 0,
                0, height,
                width, height,
                width, 0,
                -strokeWidth, -strokeWidth,
                width + strokeWidth, -strokeWidth,
                width + strokeWidth, height + strokeWidth,
                -strokeWidth, height + strokeWidth
        };
    }

    private static int[] getStrokeIndices() {
        return new int[]{
                0, 4, 5,
                5, 0, 3,

                3, 5, 6,
                6, 3, 2,

                2, 6, 7,
                7, 2, 1,

                1, 7, 4,
                4, 1, 0
        };
    }
}
