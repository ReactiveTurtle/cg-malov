package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.*;
import ru.reactiveturtle.sgl.shape.ShapeBatch;
import ru.reactiveturtle.sgl.shape.gradient.GradientShapeBatch;

public class AppRenderContext extends RenderContext {
    private final Color backgroundColor = new Color(0, 0, 0, 255);

    private ShapeBatch shapeBatch;
    private GradientShapeBatch gradientShapeBatch;

    private Sky sky;
    private Grass grass;
    private House house;
    private Fence fence;

    public AppRenderContext(Window window) {
        super(window);
    }

    @Override
    public void start() {
        setCamera(new OrthographicCamera(getWindow().getWidth(), getWindow().getHeight()));
        shapeBatch = new ShapeBatch(this);
        gradientShapeBatch = new GradientShapeBatch(this);

        sky = new Sky(gradientShapeBatch);
        sky.setScale(getWindow().getWidth(), getWindow().getHeight() * 3 / 4f);
        sky.setY(-getWindow().getHeight() / 4f);

        grass = new Grass(shapeBatch);
        grass.setScale(getWindow().getWidth(), getWindow().getHeight() / 4f);
        grass.setX(-getWindow().getWidth() / 2f);
        grass.setY(-getWindow().getHeight() / 2f);

        house = new House(shapeBatch);
        house.setX(-house.getWidth() * house.getScaleX() / 4f);
        house.setY(sky.getY() - grass.getScaleY() / 4f);

        fence = new Fence(shapeBatch);
        fence.setScale(25f);
        fence.setPosition(
                house.getX() - fence.getScaleWidth() / 8f * 7f,
                house.getY() - grass.getScaleY() / 8f);
    }

    @Override
    public void onChangeWindowSize(int width, int height) {
        getCamera().setWidth(width);
        getCamera().setHeight(height);
    }

    @Override
    public void render() {
        clearColorBufferBit();
        clearColor(backgroundColor);
        sky.draw();
        grass.draw();
        house.draw();
        fence.draw();
    }

    @Override
    public void dispose() {
        sky.dispose();
        grass.dispose();
        house.dispose();
        fence.dispose();
        shapeBatch.dispose();
        gradientShapeBatch.dispose();
    }

    @Override
    public OrthographicCamera getCamera() {
        return (OrthographicCamera) super.getCamera();
    }
}
