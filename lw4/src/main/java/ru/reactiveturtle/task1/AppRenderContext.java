package ru.reactiveturtle.task1;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL11;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.Window;
import ru.reactiveturtle.sgl.camera.PerspectiveCamera;
import ru.reactiveturtle.sgl.control.Cursor;
import ru.reactiveturtle.sgl.light.DirectionalLight;
import ru.reactiveturtle.sgl.model.Model3dBatch;

public class AppRenderContext extends RenderContext {
    private Model3dBatch modelBatch;
    private Figure figure;

    public AppRenderContext(Window window) {
        super(window);
    }

    @Override
    public void start() {
        enableDepthTest();
        setCamera(new PerspectiveCamera((float) Math.toRadians(70), getWindow().getAspectRatio(), 0.1f, 1000));
        modelBatch = new Model3dBatch(this);
        figure = new Figure();

        getWindow().getCursor().setListener(new Cursor.Listener() {
            @Override
            public void onMove(Vector2f cursorPosition, double biasX, double biasY) {
                super.onMove(cursorPosition, biasX, biasY);
                biasX *= 0.01f;
                biasY *= 0.01f;
                getCamera().setRotationRadiansX((float) (getCamera().getRotationRadiansX() + biasY));
                getCamera().setRotationRadiansY((float) (getCamera().getRotationRadiansY() + biasX));

                float radiansX = getCamera().getRotationRadiansX();
                float radiansY = getCamera().getRotationRadiansY();
                float distance = 10f;
                float y = (float) (Math.sin(radiansX) * distance);
                float square = (float) (Math.cos(radiansX) * distance);
                float x = (float) (Math.sin(radiansY) * -square);
                float z = (float) (Math.cos(radiansY) * square);
                getCamera().setPosition(x, y, z);
            }
        });

        getCamera().setZ(10f);
        getWindow().getCursor().hide();

        DirectionalLight directionalLight = new DirectionalLight();
        directionalLight.setAmbient(0.1f, 0.1f, 0.1f);
        directionalLight.setDiffuse(0.7f, 0.7f, 0.7f);
        directionalLight.setSpecular(0.7f, 0.5f, 0.8f);
        directionalLight.setDirection(1f, 1f, 1f);
        modelBatch.setDirectionalLight(directionalLight);
    }

    @Override
    public void render() {
        clearColorAndDepthBufferBit();
        clearColor(0.5f, 0f, 0f, 1f);
        modelBatch.begin();
        modelBatch.draw(figure);
        modelBatch.end();
        getWindow().getCursor().moveToCenter();
    }

    @Override
    public void dispose() {
        figure.dispose();
        modelBatch.dispose();
    }

    @Override
    public PerspectiveCamera getCamera() {
        return (PerspectiveCamera) super.getCamera();
    }
}
