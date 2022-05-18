package ru.reactiveturtle.task2.flower;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.shader.ColorShaderProgram;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.shape.gradient.GradientShape;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class FlowerBatch implements Disposable {
    private final ColorShaderProgram colorShaderProgram;
    private final RenderContext renderContext;

    public FlowerBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        colorShaderProgram = ColorShaderProgram.getInstance();
    }

    public void begin() {
        colorShaderProgram.bind();
    }

    public void draw(Flower flower) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(flower));
        colorShaderProgram.load(data);
        flower.draw(colorShaderProgram);
    }

    public void end() {
        colorShaderProgram.unbind();
    }

    @Override
    public void dispose() {

    }
}
