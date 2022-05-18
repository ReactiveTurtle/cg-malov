package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Door extends Transform2D implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;
    private final Rectangle[] rectangles = new Rectangle[5];

    public Door(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;
        rectangles[0] = new Rectangle(1f, 2f, 0);
        rectangles[0].setFillColor(new Color("#4D372C").value());
        float width = rectangles[0].getWidth();
        float height = rectangles[0].getHeight();

        rectangles[1] = new Rectangle(width * 0.85f, height * 0.925f, 0);
        rectangles[1].setOrigin(-(width - rectangles[1].getWidth()) / 2f, -(height - rectangles[1].getHeight()) / 2f);
        rectangles[1].setFillColor(new Color("#3C2F1E").value());

        float bias = rectangles[1].getHeight() / 3f / 12f;
        float windowHeight = (rectangles[1].getHeight() - bias * 4) / 3f;

        rectangles[2] = new Rectangle(rectangles[1].getWidth() * 0.85f, windowHeight, 0);
        rectangles[2].setOrigin(-(width - rectangles[2].getWidth()) / 2f, rectangles[1].getOriginY() - bias);
        rectangles[2].setFillColor(new Color("#79D3F6").value());

        rectangles[3] = new Rectangle(rectangles[2].getWidth(), windowHeight, 0);
        rectangles[3].setOrigin(rectangles[2].getOriginX(),  rectangles[1].getOriginY() - bias * 2 - windowHeight);
        rectangles[3].setFillColor(new Color("#79D3F6").value());

        rectangles[4] = new Rectangle(rectangles[3].getWidth(), windowHeight, 0);
        rectangles[4].setOrigin(rectangles[3].getOriginX(), rectangles[1].getOriginY() - bias * 3 - windowHeight * 2);
        rectangles[4].setFillColor(new Color("#79D3F6").value());

        setScale(80f);
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        for (Rectangle rectangle : rectangles) {
            shapeBatch.draw(rectangle);
        }
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        for (Rectangle rectangle : rectangles) {
            rectangle.dispose();
        }
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        for (Rectangle rectangle : rectangles) {
            rectangle.setX(x);
        }
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        for (Rectangle rectangle : rectangles) {
            rectangle.setY(y);
        }
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        for (Rectangle rectangle : rectangles) {
            rectangle.setScaleX(x);
        }
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        for (Rectangle rectangle : rectangles) {
            rectangle.setScaleY(y);
        }
    }

    public float getScaleWidth() {
        return rectangles[0].getScaleWidth();
    }
}
