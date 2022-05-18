package ru.reactiveturtle.task2;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.ShapeBatch;
import ru.reactiveturtle.sgl.shape.Triangle;

public class Roof extends Transform2D implements Drawable, Disposable {
    private ShapeBatch shapeBatch;

    private Chimney chimney;

    private Triangle triangleFirst;
    private Triangle triangleSecond;

    public Roof(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;

        chimney = new Chimney(shapeBatch);

        triangleFirst = new Triangle(
                new Vector2f(0f, 0f),
                new Vector2f(0.5f, 0.2f),
                new Vector2f(1f, 0f),
                0);
        triangleFirst.setFillColor(new Color("#762B25").value());

        triangleSecond = new Triangle(
                new Vector2f(0.05f, 0f),
                new Vector2f(0.5f, 0.175f),
                new Vector2f(0.95f, 0f),
                0);
        triangleSecond.setFillColor(new Color("#C2AC95").value());
    }

    @Override
    public void draw() {
        chimney.draw();
        shapeBatch.begin();
        shapeBatch.draw(triangleFirst);
        shapeBatch.draw(triangleSecond);
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        chimney.dispose();
        triangleFirst.dispose();
        triangleSecond.dispose();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        chimney.setX(x);
        triangleFirst.setX(x);
        triangleSecond.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        chimney.setY(y);
        triangleFirst.setY(y);
        triangleSecond.setY(y);
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        chimney.setScaleX(x);
        triangleFirst.setScaleX(x);
        triangleSecond.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        chimney.setScaleY(y);
        triangleFirst.setScaleY(y);
        triangleSecond.setScaleY(y);
    }
}
