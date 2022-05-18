package ru.reactiveturtle.sgl.ui;

import ru.reactiveturtle.sgl.shader.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

public abstract class Layout extends Element {
    private ElementBatch elementBatch;
    private final List<Element> elementList = new ArrayList<>();

    protected void setUIElementBatch(ElementBatch elementBatch) {
        this.elementBatch = elementBatch;
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        super.draw(shaderProgram);
        for (Element element : elementList) {
            elementBatch.draw(element);
        }
    }

    public void add(Element element) {
        elementList.add(element);
        element.setParent(this);
        if (element instanceof Layout) {
            ((Layout) element).setUIElementBatch(elementBatch);
        }
        element.adaptSize();
    }

    public void remove(Element element) {
        elementList.remove(element);
        element.setParent(null);
        if (element instanceof Layout) {
            ((Layout) element).setUIElementBatch(null);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        for (Element element : elementList) {
            element.dispose();
            element.setParent(null);
        }
        elementList.clear();
    }
}
