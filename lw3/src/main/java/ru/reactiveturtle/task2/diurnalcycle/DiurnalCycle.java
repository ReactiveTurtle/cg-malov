package ru.reactiveturtle.task2.diurnalcycle;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.model.ColorBatch;

public class DiurnalCycle implements Drawable, Disposable {
    private final ColorBatch colorBatch;

    private final Sky sky;

    private final Sun sun;
    private final Moon moon;
    private final Vector2f rotationCenter;

    public DiurnalCycle(ColorBatch colorBatch, Vector2f rotationCenter) {
        this.colorBatch = colorBatch;

        sky = new Sky();
        sky.setOriginX(0.5f);

        this.rotationCenter = new Vector2f(rotationCenter);
        sun = new Sun();
        sun.setRotationRadiansZ((float) (Math.PI * 2f / 3f));
        moon = new Moon();
    }

    public void updateViewport(int width, int height) {
        sky.setScale(width, height * 3 / 4f);
        sky.setY(-height / 4f);
    }

    public void update(double deltaTime) {
        float currentRotation = sun.getRotationRadiansZ() - (float) (Math.PI * deltaTime) / 60f;
        sun.setRotationRadiansZ(currentRotation);
        Vector2f sunPosition = new Vector2f(
                (float) Math.cos(currentRotation) * rotationCenter.length() + rotationCenter.x,
                (float) Math.sin(currentRotation) * rotationCenter.length() + rotationCenter.y / 2f);
        sun.setPosition(sunPosition);
        moon.setPosition(sunPosition.negate().add(0, rotationCenter.y));
        sky.update(currentRotation);
    }

    @Override
    public void draw() {
        colorBatch.begin();
        colorBatch.draw(sky);
        colorBatch.draw(sun);
        colorBatch.draw(moon);
        colorBatch.end();
    }

    @Override
    public void dispose() {
        sun.dispose();
        moon.dispose();
    }
}
