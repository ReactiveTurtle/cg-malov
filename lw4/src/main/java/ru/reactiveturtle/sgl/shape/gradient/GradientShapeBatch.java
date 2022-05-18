package ru.reactiveturtle.sgl.shape.gradient;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.shader.ColorShaderProgram;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class GradientShapeBatch implements Disposable {
    private ColorShaderProgram colorShaderProgram;
    private final RenderContext renderContext;

    public GradientShapeBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        colorShaderProgram = ColorShaderProgram.getInstance();
    }

    public void begin() {
        colorShaderProgram.bind();
    }

    public void draw(GradientShape shape) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(shape));
        colorShaderProgram.load(data);
        shape.draw(colorShaderProgram);
    }

    public void end() {
        colorShaderProgram.unbind();
    }

    @Override
    public void dispose() {
        colorShaderProgram.dispose();
    }
}
