package ru.reactiveturtle.sgl.shape;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Color;

public class Circle extends Shape {
    private static final int DEFAULT_QUALITY = 18;
    private final float PART_RADIANS;
    private final int ROUND_POINT_COUNT;
    private float radius;

    public Circle(float radius, float strokeWidth) {
        this(radius, strokeWidth, DEFAULT_QUALITY);
    }

    public Circle(float radius, float strokeWidth, int quality) {
        super(new float[0], new int[0], new float[0], new int[0]);
        this.radius = radius;
        PART_RADIANS = (float) (Math.PI / quality);
        ROUND_POINT_COUNT = (int) (Math.PI / PART_RADIANS) * 2 + 1;
        setFillVertices(getFillVertices(radius), getFillIndices());
        setStrokeVertices(getStrokeVertices(radius, strokeWidth), getStrokeIndices());
    }

    public void setRadius(float radius) {
        this.radius = radius;
        setFillVertices(getFillVertices(radius), getFillIndices());
        setStrokeVertices(getStrokeVertices(radius, getStrokeWidth()), getStrokeIndices());
    }

    @Override
    public void setStrokeWidth(float strokeWidth) {
        super.setStrokeWidth(strokeWidth);
        setStrokeVertices(getStrokeVertices(radius, strokeWidth), getStrokeIndices());
    }

    @Override
    public void draw(ShapeShaderProgram shapeShader) {
        GL30.glBindVertexArray(getFillVertexArrayId());
        GL20.glEnableVertexAttribArray(0);
        shapeShader.loadColor(new Color(getFillColor()).getComponentsAsVector());
        GL20.glDrawElements(GL11.GL_TRIANGLES, getFillVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDrawElements(GL11.GL_TRIANGLES, getFillVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);

        int originalStrokeColor = getStrokeColor();
        if (getStrokeWidth() == 0) {
            setStrokeColor(new Color(0, 0, 0, 0).value());
        }
        GL30.glBindVertexArray(getStrokeVertexArrayId());
        GL20.glEnableVertexAttribArray(0);
        shapeShader.loadColor(new Color(getStrokeColor()).getComponentsAsVector());
        GL20.glDrawElements(GL11.GL_TRIANGLES, getStrokeVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        setStrokeColor(originalStrokeColor);
    }

    public float getRadius() {
        return radius;
    }

    private float[] getFillVertices(float radius) {
        float[] vertices = new float[2 * ROUND_POINT_COUNT + 2];
        float radians = 0;
        int vertexStartIndex = 2;
        for (int i = 0; i < ROUND_POINT_COUNT; i++) {
            vertices[vertexStartIndex] = (float) (Math.cos(radians) * radius);
            vertices[vertexStartIndex + 1] = (float) (Math.sin(radians) * radius);
            radians += PART_RADIANS;
            vertexStartIndex += 2;
        }
        return vertices;
    }

    private int[] getFillIndices() {
        int[] indices = new int[3 * ROUND_POINT_COUNT];
        for (int i = 0; i < ROUND_POINT_COUNT; i++) {
            int startIndex = i * 3;
            indices[startIndex] = 0;
            indices[startIndex + 1] = i + 1;
            indices[startIndex + 2] = i + 2;
        }
        return indices;
    }

    private float[] getStrokeVertices(
            float radius,
            float strokeWidth) {
        float[] floatBuffer = new float[4 * ROUND_POINT_COUNT];
        float radians = 0;
        int vertexStartIndex = 0;
        for (int i = 0; i < ROUND_POINT_COUNT; i++) {
            floatBuffer[vertexStartIndex] = (float) Math.cos(radians) * radius;
            floatBuffer[vertexStartIndex + 1] = (float) Math.sin(radians) * radius;
            floatBuffer[vertexStartIndex + 2] = (float) Math.cos(radians) * (radius + strokeWidth);
            floatBuffer[vertexStartIndex + 3] = (float) Math.sin(radians) * (radius + strokeWidth);
            radians += PART_RADIANS;
            vertexStartIndex += 4;
        }
        return floatBuffer;
    }

    private int[] getStrokeIndices() {
        int[] indices = new int[6 * ROUND_POINT_COUNT];
        for (int i = 0; i < ROUND_POINT_COUNT; i++) {
            int startIndex = i * 3;
            indices[startIndex] = i;
            indices[startIndex + 1] = i + 1;
            indices[startIndex + 2] = i + 2;
            indices[startIndex + 1] = i + 3;
            indices[startIndex + 2] = i + 2;
            indices[startIndex + 2] = i + 1;
        }
        return indices;
    }
}
