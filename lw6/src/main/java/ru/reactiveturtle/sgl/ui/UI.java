package ru.reactiveturtle.sgl.ui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import ru.reactiveturtle.sgl.RenderContext;

public class UI extends Layout {
    private final ElementBatch elementBatch;

    public UI(RenderContext renderContext) {
        elementBatch = new ElementBatch(renderContext);
        setUIElementBatch(elementBatch);
        setSize(renderContext.getWindow().getWidth(), renderContext.getWindow().getHeight());
    }

    public void draw() {
        GL20.glEnable(GL11.GL_BLEND);
        elementBatch.begin();
        elementBatch.draw(this);
        elementBatch.end();
        GL20.glDisable(GL11.GL_BLEND);
    }

    @Override
    public void dispose() {
        super.dispose();
        elementBatch.dispose();
    }
}
