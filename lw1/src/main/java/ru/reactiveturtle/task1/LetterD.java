package ru.reactiveturtle.task1;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

import java.util.Objects;

public class LetterD extends Transform2D implements Drawable {
    private Rectangle top;
    private Rectangle leftTop;
    private Rectangle rightTop;
    private Rectangle center;
    private Rectangle leftBottom;
    private Rectangle rightBottom;
    private Vector2f topStart;
    private Vector2f leftTopStart;
    private Vector2f rightTopStart;
    private Vector2f leftBottomStart;
    private Vector2f rightBottomStart;

    private ShapeBatch shapeBatch;

    public LetterD(ShapeBatch shapeBatch) {
        this.shapeBatch = Objects.requireNonNull(shapeBatch);

        int color = new Color(0f, 0f, 1f, 1f).value();

        top = new Rectangle(6f, 1f, 0);
        top.setScale(15f);
        top.setFillColor(color);
        top.setOrigin(top.getWidth() / 2f, top.getHeight() / 2f);
        topStart = new Vector2f(0, 11f * 0.65f * top.getScaleY());
        top.setPosition(topStart);

        leftTop = new Rectangle(8f, 1f, 0);
        leftTop.setScale(15f);
        leftTop.setFillColor(color);
        leftTop.setRotationRadians((float) (Math.PI * 83 / 180));
        leftTop.setOrigin(leftTop.getWidth() / 2f, leftTop.getHeight() / 2f);
        leftTopStart = new Vector2f(
                -3.2f * leftTop.getScaleX(),
                topStart.y - (leftTop.getBoxSize().y - top.getHeight()) * leftTop.getScaleY() / 2f);
        leftTop.setPosition(leftTopStart);

        rightTop = new Rectangle(8f, 1f, 0);
        rightTop.setScale(15f);
        rightTop.setFillColor(color);
        rightTop.setRotationRadians((float) (Math.PI * 97 / 180));
        rightTop.setOrigin(rightTop.getWidth() / 2f, rightTop.getHeight() / 2f);
        rightTopStart = new Vector2f(
                3.2f * rightTop.getScaleX(),
                 topStart.y - (leftTop.getBoxSize().y - top.getHeight()) * rightTop.getScaleY() / 2f);
        rightTop.setPosition(rightTopStart);

        center = new Rectangle(10f, 1f, 0);
        center.setScale(15f);
        center.setFillColor(color);
        center.setOrigin(center.getWidth() / 2f, center.getHeight() / 2f);

        leftBottom = new Rectangle(4f, 1f, 0);
        leftBottom.setScale(15f);
        leftBottom.setFillColor(color);
        leftBottom.setRotationRadians((float) (Math.PI * 95 / 180));
        leftBottom.setOrigin(leftBottom.getWidth() / 2f, leftBottom.getHeight() / 2f);
        leftBottomStart = new Vector2f(
                -(center.getWidth() - leftBottom.getHeight()) / 2f * leftBottom.getScaleX(),
                (-leftBottom.getWidth() + center.getHeight()) / 2f * leftBottom.getScaleY());
        leftBottom.setPosition(leftBottomStart);

        rightBottom = new Rectangle(4f, 1f, 0);
        rightBottom.setScale(15f);
        rightBottom.setFillColor(color);
        rightBottom.setRotationRadians((float) (Math.PI * 85 / 180));
        rightBottom.setOrigin(rightBottom.getWidth() / 2f, rightBottom.getHeight() / 2f);
        rightBottomStart = new Vector2f(
                (center.getWidth() - rightBottom.getHeight()) / 2f * rightBottom.getScaleX(),
                (-rightBottom.getWidth() + center.getHeight()) / 2f * rightBottom.getScaleY());
        rightBottom.setPosition(rightBottomStart);
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(top);
        shapeBatch.draw(leftTop);
        shapeBatch.draw(rightTop);
        shapeBatch.draw(center);
        shapeBatch.draw(leftBottom);
        shapeBatch.draw(rightBottom);
        shapeBatch.end();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        top.setY(topStart.y + y);
        leftTop.setY(leftTopStart.y + y);
        rightTop.setY(rightTopStart.y + y);
        center.setY(y);
        leftBottom.setY(leftBottomStart.y + y);
        rightBottom.setY(rightBottomStart.y + y);
    }
}
