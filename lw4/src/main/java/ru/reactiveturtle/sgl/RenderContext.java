package ru.reactiveturtle.sgl;

import org.lwjgl.opengl.GL11;
import ru.reactiveturtle.sgl.camera.ICamera;

public abstract class RenderContext {
    private final Window window;
    private ICamera camera;

    public RenderContext(Window window){
        this.window = window;
    }

    public void clearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    public void clearColor(Color color) {
        clearColor(color.redFloat(), color.greenFloat(), color.blueFloat(), color.alphaFloat());
    }

    public void clearColorAndDepthBufferBit() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void clearColorBufferBit() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void clearDepthBufferBit() {
        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
    }

    protected void enableDepthTest() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public abstract void start();

    public void onChangeWindowSize(int width, int height) {
    }

    public abstract void render();

    public abstract void dispose();

    public Window getWindow() {
        return window;
    }

    public void setCamera(ICamera camera) {
        this.camera = camera;
    }

    public ICamera getCamera() {
        return camera;
    }

    public double getDeltaTime() {
        return getWindow().getDeltaTime();
    }
}
