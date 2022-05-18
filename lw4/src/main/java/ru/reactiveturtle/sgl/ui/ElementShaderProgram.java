package ru.reactiveturtle.sgl.ui;

import org.joml.Matrix4f;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

public class ElementShaderProgram extends ShaderProgram {
    private int v_projectionMatrixLocation;
    private int v_modelMatrixLocation;
    private int f_textureSamplerLocation;

    protected ElementShaderProgram() {
        super("src/main/resources/shader/uiVertexShader.glsl",
                "src/main/resources/shader/uiFragmentShader.glsl");
        create();
    }

    @Override
    public void bindAllAttributes() {
        super.bindAttribute(0, "v_vertex");
        super.bindAttribute(1, "v_texCoord");
    }

    @Override
    public void getAllUniforms() {
        v_projectionMatrixLocation = getUniform("v_projectionMatrix");
        v_modelMatrixLocation = getUniform("v_modelMatrix");
        f_textureSamplerLocation = getUniform("f_textureSampler");
    }

    @Override
    public void load(Object data) {
        loadIntUniform(f_textureSamplerLocation, 0);
    }

    public void loadProjectionMatrix(Matrix4f projectionMatrix) {
        loadMatrix4fUniform(v_projectionMatrixLocation, projectionMatrix);
    }

    public void loadModelMatrix(Element element) {
        loadMatrix4fUniform(v_modelMatrixLocation, MatrixUtils.getModelMatrix(element));
    }
}
