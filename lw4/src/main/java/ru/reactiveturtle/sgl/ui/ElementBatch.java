package ru.reactiveturtle.sgl.ui;

import org.joml.Matrix4f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;

import java.util.Objects;

public class ElementBatch implements Disposable {
    private final ElementShaderProgram elementShader;
    private final RenderContext renderContext;

    public ElementBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        elementShader = new ElementShaderProgram();
    }

    public void begin() {
        elementShader.bind();
    }

    public void draw(Element element) {
        elementShader.load(null);

        float halfWindowWidth = renderContext.getWindow().getWidth() / 2f;
        float halfWindowHeight = renderContext.getWindow().getHeight() / 2f;
        elementShader.loadProjectionMatrix(new Matrix4f()
                .ortho2D(-halfWindowWidth, halfWindowWidth, -halfWindowHeight, halfWindowHeight));
        elementShader.loadModelMatrix(element);
        element.draw(elementShader);
    }

    public void end() {
        elementShader.unbind();
    }

    @Override
    public void dispose() {
        elementShader.dispose();
    }
}
