package ru.reactiveturtle.task4;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.Window;
import ru.reactiveturtle.sgl.camera.OrthographicCamera;
import ru.reactiveturtle.sgl.control.Cursor;

public class AppRenderContext extends RenderContext {
    private WaveBatch waveBatch;
    private WaveModel waveModel;

    public AppRenderContext(Window window) {
        super(window);
    }

    @Override
    public void start() {
        setCamera(new OrthographicCamera(getWindow().getWidth(), getWindow().getHeight()));
        waveBatch = new WaveBatch(this);
        final float waveSpeed = 80f;
        final float transitionBorderWidth = getWindow().getHeight() / 10f;
        waveModel = new WaveModel(waveSpeed, transitionBorderWidth);
        getWindow().getCursor().setListener(new Cursor.Listener() {
            @Override
            public void onDown(Vector2f cursorPosition, Cursor.Key key) {
                if (!waveModel.hasClickPoint()) {
                    waveModel.setClickPoint(getCamera().getCursorPosition(getWindow(), cursorPosition));
                }
            }
        });
    }

    @Override
    public void onChangeWindowSize(int width, int height) {
        waveModel.setScale(width, height);
        getCamera().setWidth(width);
        getCamera().setHeight(height);
        waveModel.setDiagonalLength(new Vector2f(width, height));
    }

    @Override
    public void render() {
        clearColorBufferBit();
        clearColor(0f, 1f, 0f, 1f);

        if (waveModel.hasClickPoint()) {
            waveModel.addTimeLeft(getDeltaTime());
        }
        waveBatch.begin();
        waveBatch.draw(waveModel);
        waveBatch.end();
    }

    @Override
    public void dispose() {
        waveModel.dispose();
        waveBatch.dispose();
    }

    @Override
    public OrthographicCamera getCamera() {
        return (OrthographicCamera) super.getCamera();
    }
}
