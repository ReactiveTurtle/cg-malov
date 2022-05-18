package ru.reactiveturtle.task2;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Circle;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;
import ru.reactiveturtle.sgl.shape.Triangle;

public class Plank extends Transform2D implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;

    private final Circle circle;
    private final Vector2f[] circleOrigins = new Vector2f[2];

    private final Rectangle rectangle;
    private final Triangle triangle;

    public Plank(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;

        int plankColor = new Color("#A95D2C").value();
        rectangle = new Rectangle(1f, 5f, 0);
        rectangle.setFillColor(plankColor);

        circle = new Circle(rectangle.getWidth() / 12f, 0);
        circle.setFillColor(new Color("#260403").value());
        circleOrigins[0] = new Vector2f(
                -rectangle.getWidth() / 2f,
                -rectangle.getHeight() * 2 / 8f);
        circleOrigins[1] = new Vector2f(
                -rectangle.getWidth() / 2f,
                -rectangle.getHeight() * 5 / 8f);

        triangle = new Triangle(
                new Vector2f(0f, 0f),
                new Vector2f(rectangle.getWidth() / 2f, 0.5f),
                new Vector2f(rectangle.getWidth(), 0f),
                0);
        triangle.setFillColor(plankColor);
        triangle.setOrigin(0, -rectangle.getHeight());
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(rectangle);
        shapeBatch.draw(triangle);
        for (Vector2f origin : circleOrigins) {
            circle.setOrigin(origin);
            shapeBatch.draw(circle);
        }
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        rectangle.dispose();
        triangle.dispose();
        circle.dispose();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        rectangle.setX(x);
        triangle.setX(x);
        circle.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        rectangle.setY(y);
        triangle.setY(y);
        circle.setY(y);
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        rectangle.setScaleX(x);
        triangle.setScaleX(x);
        circle.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        rectangle.setScaleY(y);
        triangle.setScaleY(y);
        circle.setScaleY(y);
    }

    public float getScaleWidth() {
        return rectangle.getScaleWidth();
    }

    public float getHeight() {
        return rectangle.getHeight();
    }
}
