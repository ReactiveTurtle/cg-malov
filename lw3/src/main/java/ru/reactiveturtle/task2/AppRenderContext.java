package ru.reactiveturtle.task2;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import ru.reactiveturtle.sgl.OrthographicCamera;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.Window;
import ru.reactiveturtle.sgl.model.ColorBatch;
import ru.reactiveturtle.sgl.shape.ShapeBatch;
import ru.reactiveturtle.sgl.shape.gradient.GradientShapeBatch;
import ru.reactiveturtle.task2.butterfly.Butterfly;
import ru.reactiveturtle.task2.butterfly.ButterflyMap;
import ru.reactiveturtle.task2.diurnalcycle.DiurnalCycle;
import ru.reactiveturtle.task2.flower.*;

public class AppRenderContext extends RenderContext {
    private ShapeBatch shapeBatch;
    private ColorBatch colorBatch;
    private DiurnalCycle diurnalCycle;
    private Ground ground;

    private FlowerMap flowerMap;
    private ButterflyMap butterflyMap;

    public AppRenderContext(Window window) {
        super(window);
    }

    @Override
    public void start() {
        setCamera(new OrthographicCamera(getWindow().getWidth(), getWindow().getHeight()));
        shapeBatch = new ShapeBatch(this);
        colorBatch = new ColorBatch(this);
        diurnalCycle = new DiurnalCycle(colorBatch, new Vector2f(0, -getWindow().getHeight() * 3f / 4f));
        ground = new Ground(shapeBatch);

        butterflyMap = new ButterflyMap(this, shapeBatch);
    }

    @Override
    public void onChangeWindowSize(int width, int height) {
        getCamera().setWidth(width);
        getCamera().setHeight(height);
        diurnalCycle.updateViewport(width, height);
        ground.setScale(getWindow().getWidth(), getWindow().getHeight() / 4f);
        ground.setPosition(-getWindow().getWidth() / 2f, -getWindow().getHeight() / 2f);
    }

    @Override
    public void render() {
        clearColorBufferBit();
        clearColor(0f, 0f, 0f, 1f);
        diurnalCycle.update(getDeltaTime());
        butterflyMap.update(getDeltaTime());
        if (GLFW.glfwGetKey(getWindow().getWindowId(), GLFW.GLFW_KEY_0) == GLFW.GLFW_TRUE) {
            diurnalCycle.update(getDeltaTime() * 9);
        }
        diurnalCycle.draw();

        ground.draw();
        butterflyMap.draw();
    }

    @Override
    public void dispose() {
        shapeBatch.dispose();
        diurnalCycle.dispose();
        ground.dispose();
        butterflyMap.dispose();
    }

    @Override
    public OrthographicCamera getCamera() {
        return (OrthographicCamera) super.getCamera();
    }
}
