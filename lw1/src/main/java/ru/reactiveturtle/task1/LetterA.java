package ru.reactiveturtle.task1;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

import java.util.Objects;

public class LetterA extends Transform2D implements Drawable {
    private Rectangle center;
    private Rectangle left;
    private Rectangle right;
    private Vector2f leftStart;
    private Vector2f rightStart;
    private ShapeBatch shapeBatch;

    public LetterA(ShapeBatch shapeBatch) {
        this.shapeBatch = Objects.requireNonNull(shapeBatch);

        int color = new Color(1f, 0, 0, 0).value();

        center = new Rectangle(4f, 1f, 0);
        center.setScale(15f);
        center.setOrigin(center.getWidth() / 2f, center.getHeight() / 2f);

        left = new Rectangle(11f, 1f, 0);
        left.setScale(15f);
        left.setOrigin(left.getWidth() / 2f, left.getHeight() / 2f);
        left.setRotationRadians((float) (Math.PI * 73f / 180));
        leftStart = new Vector2f(
                -center.getWidth() * 0.8f / 2f * center.getScaleX(),
                left.getWidth() * 0.2f * center.getScaleY());
        left.setPosition(leftStart);

        right = new Rectangle(11f, 1f, 0);
        right.setScale(15f);
        right.setOrigin(right.getWidth() / 2f, right.getHeight() / 2f);
        right.setRotationRadians((float) (Math.PI * 107f / 180));
        rightStart = new Vector2f(
                center.getWidth() * 0.8f / 2f * center.getScaleX(),
                left.getWidth() * 0.2f * center.getScaleY());
        right.setPosition(rightStart);
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(center);
        shapeBatch.draw(left);
        shapeBatch.draw(right);
        shapeBatch.end();
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        center.setX(x);
        left.setX(leftStart.x + x);
        right.setX(rightStart.x + x);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        center.setY(y);
        left.setY(leftStart.y + y);
        right.setY(rightStart.y + y);
    }

    public void setColor(int color) {
        center.setFillColor(color);
        left.setFillColor(color);
        right.setFillColor(color);
    }
}
