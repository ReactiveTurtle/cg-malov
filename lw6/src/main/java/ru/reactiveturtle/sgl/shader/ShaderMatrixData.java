package ru.reactiveturtle.sgl.shader;

import org.joml.Matrix4f;

public class ShaderMatrixData {
    private final Matrix4f projectionMatrix;
    private final Matrix4f viewMatrix;
    private final Matrix4f modelMatrix;

    public ShaderMatrixData(Matrix4f projectionMatrix,
                            Matrix4f viewMatrix,
                            Matrix4f modelMatrix) {
        this.projectionMatrix = new Matrix4f(projectionMatrix);
        this.viewMatrix = new Matrix4f(viewMatrix);
        this.modelMatrix = new Matrix4f(modelMatrix);
    }

    public Matrix4f getProjectionMatrix() {
        return new Matrix4f(projectionMatrix);
    }

    public Matrix4f getViewMatrix() {
        return new Matrix4f(viewMatrix);
    }

    public Matrix4f getModelMatrix() {
        return new Matrix4f(modelMatrix);
    }
}
