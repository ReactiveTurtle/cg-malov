package ru.reactiveturtle.sgl.shape;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.model.Model3d;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class ShapeBatch implements Disposable {
    private ShapeShaderProgram shapeShader;
    private final RenderContext renderContext;

    public ShapeBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        shapeShader = new ShapeShaderProgram();
    }

    public void begin() {
        shapeShader.bind();
    }

    public void draw(Shape shape) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(shape));
        shapeShader.load(data);
        shape.draw(shapeShader);
    }

    public void draw(Model2d model2d) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(model2d));
        shapeShader.load(data);
        model2d.draw(shapeShader);
    }

    public void draw(Model3d model3d) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(model3d));
        shapeShader.load(data);
        model3d.draw(shapeShader);
    }

    public void end() {
        shapeShader.unbind();
    }

    @Override
    public void dispose() {
        shapeShader.dispose();
    }
}
