package ru.reactiveturtle.sgl.shape.texture;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.shader.TextureShaderProgram;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class TextureShapeBatch  implements Disposable {
    private final TextureShaderProgram textureShaderProgram;
    private final RenderContext renderContext;

    public TextureShapeBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        textureShaderProgram = new TextureShaderProgram();
    }

    public void begin() {
        textureShaderProgram.bind();
    }

    public void draw(TextureShape shape) {
        ShaderMatrixData data = new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(shape));
        textureShaderProgram.load(data);
        shape.draw(textureShaderProgram);
    }

    public void end() {
        textureShaderProgram.unbind();
    }

    @Override
    public void dispose() {
        textureShaderProgram.dispose();
    }
}
