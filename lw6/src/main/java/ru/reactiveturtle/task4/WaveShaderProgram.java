package ru.reactiveturtle.task4;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

public class WaveShaderProgram extends ShaderProgram {
    private int v_projectionMatrixLocation;
    private int v_viewMatrixLocation;
    private int v_modelMatrixLocation;
    private int f_clickPointLocation;
    private int f_scaleLocation;
    private int f_firstTextureSamplerLocation;
    private int f_secondTextureSamplerLocation;
    private int f_waveTimePassedLocation;
    private int f_waveSpeedLocation;
    private int f_waveOffsetLocation;
    private int f_transitionBorderWidthLocation;

    protected WaveShaderProgram() {
        super("src/main/resources/shader/waveVertexShader.glsl",
                "src/main/resources/shader/waveFragmentShader.glsl");
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
        f_clickPointLocation = getUniform("f_clickPoint");
        f_scaleLocation = getUniform("f_scale");
        f_firstTextureSamplerLocation = getUniform("f_firstTextureSampler");
        f_secondTextureSamplerLocation = getUniform("f_secondTextureSampler");
        f_waveTimePassedLocation = getUniform("f_waveTimePassed");
        f_waveSpeedLocation = getUniform("f_waveSpeed");
        f_waveOffsetLocation = getUniform("f_waveOffset");
        f_transitionBorderWidthLocation = getUniform("f_transitionBorderWidth");
    }

    @Override
    public void load(Object data) {
        loadIntUniform(f_firstTextureSamplerLocation, 0);
        loadIntUniform(f_secondTextureSamplerLocation, 1);
        ShaderMatrixData shaderData = (ShaderMatrixData) data;
        loadMatrix4fUniform(v_projectionMatrixLocation, shaderData.getProjectionMatrix());
        loadMatrix4fUniform(v_viewMatrixLocation, shaderData.getViewMatrix());
        loadModelMatrix(shaderData.getModelMatrix());
    }

    public void loadModelMatrix(Matrix4f modelMatrix) {
        loadMatrix4fUniform(v_modelMatrixLocation, modelMatrix);
        loadVector2fUniform(f_scaleLocation, new Vector2f(modelMatrix.m00(), modelMatrix.m11()));
    }

    public void loadWaveData(Vector2f clickPoint, float waveTimeLeft, float waveSpeed, float waveOffset, float transitionBorderWidth) {
        if (clickPoint != null) {
            loadVector2fUniform(f_clickPointLocation, clickPoint);
        }
        loadFloatUniform(f_waveTimePassedLocation, waveTimeLeft);
        loadFloatUniform(f_waveSpeedLocation, waveSpeed);
        loadFloatUniform(f_waveOffsetLocation, waveOffset);
        loadFloatUniform(f_transitionBorderWidthLocation, transitionBorderWidth);
    }
}
