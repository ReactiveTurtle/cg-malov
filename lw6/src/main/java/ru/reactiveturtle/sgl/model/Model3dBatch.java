package ru.reactiveturtle.sgl.model;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.light.DirectionalLight;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class Model3dBatch implements Disposable {
    private final Model3dShaderProgram shaderProgram;
    private final RenderContext renderContext;
    private DirectionalLight directionalLight;

    public Model3dBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        shaderProgram = new Model3dShaderProgram();
    }

    public void begin() {
        shaderProgram.bind();
    }

    public void draw(Model3d model3d) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(model3d));
        shaderProgram.load(data);
        shaderProgram.loadDirectionalLight(directionalLight);
        model3d.draw(shaderProgram);
    }

    public void end() {
        shaderProgram.unbind();
    }

    @Override
    public void dispose() {
        shaderProgram.dispose();
    }

    public void setDirectionalLight(DirectionalLight directionalLight) {
        this.directionalLight = directionalLight;
    }

    public DirectionalLight getDirectionalLight() {
        return directionalLight;
    }
}
