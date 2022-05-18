package ru.reactiveturtle.sgl.model;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.shader.ColorShaderProgram;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class ColorBatch implements Disposable {
    private final ColorShaderProgram colorShaderProgram;
    private final RenderContext renderContext;

    public ColorBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        colorShaderProgram = ColorShaderProgram.getInstance();
    }

    public void begin() {
        colorShaderProgram.bind();
    }

    public void draw(Model2d model) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(model));
        colorShaderProgram.load(data);
        model.draw(colorShaderProgram);
    }

    public void end() {
        colorShaderProgram.unbind();
    }

    @Override
    public void dispose() {
        colorShaderProgram.dispose();
    }
}
