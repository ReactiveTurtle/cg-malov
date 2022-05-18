package ru.reactiveturtle.sgl.shape;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

public class ShapeShaderProgram extends ShaderProgram {
    private int v_projectionMatrixLocation;
    private int v_viewMatrixLocation;
    private int v_modelMatrixLocation;
    private int f_colorLocation;

    public ShapeShaderProgram() {
        super("src/main/resources/shader/simpleVertexShader.glsl",
                "src/main/resources/shader/simpleFragmentShader.glsl");
        create();
    }

    @Override
    public void bindAllAttributes() {
        super.bindAttribute(0, "v_vertex");
    }

    @Override
    public void getAllUniforms() {
        v_projectionMatrixLocation = getUniform("v_projectionMatrix");
        v_viewMatrixLocation = getUniform("v_viewMatrix");
        v_modelMatrixLocation = getUniform("v_modelMatrix");
        f_colorLocation = getUniform("f_color");
    }

    @Override
    public void load(Object data) {
        ShaderMatrixData shaderData = (ShaderMatrixData) data;
        loadMatrix4fUniform(v_projectionMatrixLocation, shaderData.getProjectionMatrix());
        loadMatrix4fUniform(v_viewMatrixLocation, shaderData.getViewMatrix());
        loadModelMatrix(shaderData.getModelMatrix());
    }

    public void loadModelMatrix(Matrix4f modelMatrix) {
        loadMatrix4fUniform(v_modelMatrixLocation, modelMatrix);
    }

    public void loadColor(Vector4f colorVector) {
        loadVector4fUniform(f_colorLocation, colorVector);
    }
}
