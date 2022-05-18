package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Grass extends Transform2D implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;

    private final Rectangle rectangle;

    public Grass(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;
        rectangle = new Rectangle(1f, 1f, 0);
        rectangle.setFillColor(new Color(100, 221, 23, 255).value());
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(rectangle);
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        rectangle.dispose();
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        rectangle.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        rectangle.setScaleY(y);
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        rectangle.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        rectangle.setY(y);
    }
}
