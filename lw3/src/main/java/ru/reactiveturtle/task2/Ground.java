package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Ground implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;

    private final Rectangle rectangle;

    public Ground(ShapeBatch shapeBatch) {
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

    public void setPosition(float x, float y) {
        rectangle.setPosition(x, y);
    }

    public void setScale(float scaleX, float scaleY) {
        rectangle.setScale(scaleX, scaleY);
    }
}
