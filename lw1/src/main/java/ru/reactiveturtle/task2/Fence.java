package ru.reactiveturtle.task2;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.*;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Fence extends Transform2D implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;

    private final Plank plank;
    private final Vector2f[] plankPositions = new Vector2f[10];

    private final Rectangle supportPlank;
    private final Vector2f[] supportPlankOrigins = new Vector2f[2];

    public Fence(ShapeBatch shapeBatch) {
        plank = new Plank(shapeBatch);
        this.shapeBatch = shapeBatch;
        generatePlankPositions();
        supportPlank = new Rectangle(getScaleWidth() / getScaleX(), 0.3f, 0);
        supportPlank.setFillColor(new Color("#892A00").value());
        supportPlankOrigins[0] = new Vector2f(0, -plank.getHeight() * 2f / 8f + supportPlank.getHeight() / 2f);
        supportPlankOrigins[1] = new Vector2f(0, -plank.getHeight() / 8f * 5f + supportPlank.getHeight() / 2f);
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        for (Vector2f origin : supportPlankOrigins) {
            supportPlank.setOrigin(origin);
            shapeBatch.draw(supportPlank);
        }
        shapeBatch.end();
        for (Vector2f position : plankPositions) {
            plank.setPosition(position);
            plank.draw();
        }
    }

    @Override
    public void dispose() {
        plank.dispose();
        supportPlank.dispose();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        supportPlank.setX(x);
        generatePlankPositions();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        supportPlank.setY(y);
        generatePlankPositions();
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        plank.setScaleX(x);
        supportPlank.setScaleX(x);
        generatePlankPositions();
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        plank.setScaleY(y);
        supportPlank.setScaleY(y);
    }

    public float getScaleWidth() {
        return (plankPositions[plankPositions.length - 1].x - plankPositions[0].x) + plank.getScaleWidth();
    }

    private void generatePlankPositions() {
        for (int i = 0; i < plankPositions.length; i++) {
            plankPositions[i] = new Vector2f(getX() + i * (plank.getScaleWidth() / 8f * 9f), getY());
        }
    }
}
