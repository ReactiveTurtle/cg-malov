package ru.reactiveturtle.task2;

import org.lwjgl.opengl.GL20;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class HouseWindow extends Transform2D implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;
    private final Rectangle[] rectangles = new Rectangle[3];

    public HouseWindow(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;
        rectangles[0] = new Rectangle(1f, 1.2f, 0);
        rectangles[0].setFillColor(new Color("#4D372C").value());

        float windowWidthBias = rectangles[0].getWidth() * 0.18f / 3f;
        float windowWidth = rectangles[0].getWidth() * 0.82f / 2f;

        float windowHeightBias = rectangles[0].getHeight() * 0.08f / 2f;
        float windowHeight = rectangles[0].getHeight() * 0.92f;

        rectangles[1] = new Rectangle(windowWidth, windowHeight, 0);
        rectangles[1].setOrigin(-windowWidthBias, -windowHeightBias);
        rectangles[1].setFillColor(new Color("#78DCFC").value());

        rectangles[2] = new Rectangle(windowWidth, windowHeight, 0);
        rectangles[2].setOrigin(-windowWidthBias * 2 - windowWidth, -windowHeightBias);
        rectangles[2].setFillColor(new Color("#78DCFC").value());

        setScale(100f);
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

    public float getScaleHeight() {
        return rectangles[0].getScaleHeight();
    }
}
