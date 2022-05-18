package ru.reactiveturtle.sgl.shape.base;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.utils.BufferUtils;

public abstract class BaseShape<T extends ShaderProgram> extends Transform2D implements BaseShapeImpl, Disposable {
    private final int fillVertexArrayId;
    private final int fillVertexBufferId;
    private final int fillIndicesBufferId;
    private int fillVertexCount;

    private final int strokeVertexArrayId;
    private final int strokeVertexBufferId;
    private final int strokeIndicesBufferId;
    private int strokeVertexCount;

    private float strokeWidth = 0;

    public BaseShape(
            float[] fillVertices,
            int[] fillIndices,
            float[] strokeVertices,
            int[] strokeIndices) {
        fillVertexArrayId = GL30.glGenVertexArrays();
        fillVertexBufferId = GL15.glGenBuffers();
        fillIndicesBufferId = GL15.glGenBuffers();
        setFillVertices(fillVertices, fillIndices);

        strokeVertexArrayId = GL30.glGenVertexArrays();
        strokeVertexBufferId = GL15.glGenBuffers();
        strokeIndicesBufferId = GL15.glGenBuffers();
        setStrokeVertices(strokeVertices, strokeIndices);
    }

    public void setFillVertices(float[] vertices, int[] indices) {
        GL30.glBindVertexArray(fillVertexArrayId);
        BufferUtils.storeArrayBuffer(fillVertexBufferId, vertices, 0, 2);
        BufferUtils.storeElementArrayBuffer(fillIndicesBufferId, indices);
        fillVertexCount = indices.length;
        GL30.glBindVertexArray(0);
    }

    public void setStrokeVertices(float[] strokeVertices, int[] strokeIndices) {
        GL30.glBindVertexArray(strokeVertexArrayId);
        BufferUtils.storeArrayBuffer(strokeVertexBufferId, strokeVertices, 0, 2);
        BufferUtils.storeElementArrayBuffer(strokeIndicesBufferId, strokeIndices);
        strokeVertexCount = strokeIndices.length;
        GL30.glBindVertexArray(0);
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    public float getStrokeWidth() {
        return strokeWidth;
    }

    public int getFillVertexArrayId() {
        return fillVertexArrayId;
    }

    public int getStrokeVertexArrayId() {
        return strokeVertexArrayId;
    }

    public int getFillVertexCount() {
        return fillVertexCount;
    }

    public int getStrokeVertexCount() {
        return strokeVertexCount;
    }

    @Override
    public void dispose() {
        GL15.glDeleteBuffers(new int[]{
                fillVertexBufferId,
                fillIndicesBufferId,
                strokeVertexBufferId,
                strokeIndicesBufferId});
        GL30.glDeleteVertexArrays(new int[]{fillVertexArrayId, strokeVertexArrayId});
    }

    public abstract void draw(T shaderProgram);
}
