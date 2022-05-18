package ru.reactiveturtle.sgl.shader;

import org.joml.Matrix4f;

public class TextureShaderProgram extends ShaderProgram{
    private int v_projectionMatrixLocation;
    private int v_viewMatrixLocation;
    private int v_modelMatrixLocation;
    private int f_textureSamplerLocation;

    public TextureShaderProgram() {
        super("src/main/resources/shader/textureVertexShader.glsl",
                "src/main/resources/shader/textureFragmentShader.glsl");
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
        v_viewMatrixLocation = getUniform("v_viewMatrix");
        v_modelMatrixLocation = getUniform("v_modelMatrix");
        f_textureSamplerLocation = getUniform("f_textureSampler");
    }

    @Override
    public void load(Object data) {
        ShaderMatrixData shaderData = (ShaderMatrixData) data;
        loadIntUniform(f_textureSamplerLocation, 0);
        loadMatrix4fUniform(v_projectionMatrixLocation, shaderData.getProjectionMatrix());
        loadMatrix4fUniform(v_viewMatrixLocation, shaderData.getViewMatrix());
        loadModelMatrix(shaderData.getModelMatrix());
    }

    public void loadModelMatrix(Matrix4f modelMatrix) {
        loadMatrix4fUniform(v_modelMatrixLocation, modelMatrix);
    }
}
