package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.shape.gradient.GradientRectangle;
import ru.reactiveturtle.sgl.shape.gradient.GradientShapeBatch;

public class Sky extends Transform2D implements Drawable, Disposable {
    private GradientShapeBatch shapeBatch;
    private GradientRectangle gradientRectangle;

    public Sky(GradientShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;
        gradientRectangle = new GradientRectangle(1f, 1f, 0);
        gradientRectangle.setOrigin(gradientRectangle.getWidth() / 2f, 0);
        gradientRectangle.setFillColors(new float[] {
                0.5f, 0.83f, 0.98f,
                0.008f, 0.39f, 0.82f,
                0.008f, 0.39f, 0.82f,
                0.5f, 0.83f, 0.98f
        });
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        shapeBatch.draw(gradientRectangle);
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        gradientRectangle.dispose();
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        gradientRectangle.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        gradientRectangle.setScaleY(y);
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        gradientRectangle.setY(y);
    }
}
