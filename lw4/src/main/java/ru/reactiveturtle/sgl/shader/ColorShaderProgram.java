package ru.reactiveturtle.sgl.shader;

import org.joml.Matrix4f;

public class ColorShaderProgram extends ShaderProgram {
    private static ColorShaderProgram COLOR_SHADER_PROGRAM;
    private int v_projectionMatrixLocation;
    private int v_viewMatrixLocation;
    private int v_modelMatrixLocation;

    public static ColorShaderProgram getInstance() {
        if (COLOR_SHADER_PROGRAM == null) {
            COLOR_SHADER_PROGRAM = new ColorShaderProgram();
        }
        return COLOR_SHADER_PROGRAM;
    }

    private ColorShaderProgram() {
        super("src/main/resources/shader/colorVertexShader.glsl",
                "src/main/resources/shader/colorFragmentShader.glsl");
        create();
    }

    @Override
    public void bindAllAttributes() {
        super.bindAttribute(0, "v_vertex");
        super.bindAttribute(1, "v_color");
    }

    @Override
    public void getAllUniforms() {
        v_projectionMatrixLocation = getUniform("v_projectionMatrix");
        v_viewMatrixLocation = getUniform("v_viewMatrix");
        v_modelMatrixLocation = getUniform("v_modelMatrix");
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
}
