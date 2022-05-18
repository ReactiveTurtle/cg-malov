package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.Rectangle;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Chimney extends Transform2D implements Drawable, Disposable {
    private ShapeBatch shapeBatch;

    private Rectangle pipe;
    private Rectangle edging;

    public Chimney(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;
        pipe = new Rectangle(0.1f, 0.2f, 0);
        pipe.setFillColor(new Color(178, 34, 34, 255).value());

        edging = new Rectangle(0.11f, 0.02f, 0);
        edging.setFillColor(new Color(167, 167, 165, 255).value());
        edging.setOriginX((edging.getWidth() - pipe.getWidth()) / 2f);
        edging.setOriginY(-pipe.getHeight());
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(edging);
        shapeBatch.draw(pipe);
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        pipe.dispose();
        edging.dispose();
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        pipe.setScaleX(x);
        edging.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        pipe.setScaleY(y);
        edging.setScaleY(y);
    }
}
