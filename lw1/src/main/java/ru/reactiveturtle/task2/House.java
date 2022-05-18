package ru.reactiveturtle.task2;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

// Не использовать Transform2D
public class House extends Transform2D implements Drawable, Disposable {
    private ShapeBatch shapeBatch;

    private Rectangle cement;

    private Rectangle brick;
    private Vector2f[] brickPositions;

    private Roof roof;

    private HouseWindow houseWindow;
    private Door door;

    public House(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;

        cement = new Rectangle(1f, 1f, 0);

        brick = new Rectangle(1, 0.48f, 0);
        brick.setFillColor(new Color(178, 34, 34, 255).value());
        brick.setScale(20f);

        int brickHeightCount = 21;
        int brickWidthCount = 20;
        float brickBias = 0.05f;
        brickPositions = new Vector2f[brickHeightCount * brickWidthCount - ((int) Math.floor(brickHeightCount / 2f))];
        for (int i = 0; i < brickHeightCount; i++) {
            int brickRowCount = brickWidthCount - i % 2;
            float biasRow = (i % 2) * brick.getWidth() / 2f;
            for (int j = 0; j < brickRowCount; j++) {
                int index = i * brickWidthCount - (int) Math.floor(i / 2f) + j;
                brickPositions[index] = new Vector2f(
                        biasRow + j * (brick.getWidth() + brickBias),
                        i * (brick.getHeight() + brickBias)).mul(-1);
            }
        }

        cement.setScale(
                brickWidthCount * brick.getScaleX() * brick.getWidth() +
                        (brickWidthCount - 1) * brick.getScaleX() * brickBias,
                brickHeightCount * brick.getScaleY() * brick.getHeight() +
                        (brickHeightCount - 1) * brick.getScaleY() * brickBias);
        cement.setFillColor(new Color(167, 167, 165, 255).value());

        roof = new Roof(shapeBatch);
        roof.setScale(cement.getScaleWidth() * 1.2f);

        door = new Door(shapeBatch);

        houseWindow = new HouseWindow(shapeBatch);
        houseWindow.setY(cement.getY());
        setX(getX());
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(cement);
        for (Vector2f brickPosition : brickPositions) {
            brick.setOrigin(brickPosition);
            shapeBatch.draw(brick);
        }
        shapeBatch.end();
        roof.draw();
        houseWindow.draw();
        door.draw();
    }

    @Override
    public void dispose() {
        roof.draw();
        cement.dispose();
        brick.dispose();
        houseWindow.draw();
        door.dispose();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        cement.setX(x);
        houseWindow.setX(x + houseWindow.getScaleWidth() * 2 / 3f);
        door.setX(x + cement.getScaleWidth() - door.getScaleWidth() * 1.7f);
        roof.setX(x - cement.getScaleWidth() * 0.1f);
        brick.setX(x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        cement.setY(y);
        houseWindow.setY(y + (cement.getScaleHeight() - houseWindow.getScaleHeight()) / 2f);
        door.setY(y);
        roof.setY(y + cement.getScaleHeight() * 15f / 16f);
        brick.setY(y);
    }

    public float getWidth() {
        return cement.getWidth() * cement.getScaleX();
    }

    public float getHeight() {
        return cement.getHeight() * cement.getScaleY();
    }
}
