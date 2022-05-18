package ru.reactiveturtle.task2;

import ru.reactiveturtle.sgl.Application;
import ru.reactiveturtle.sgl.Window;

public class GameWindow extends Application {
    public GameWindow() {
        super();
    }

    public void start() {
        createWindow("Луг", 800, 480, false, new Window.WindowListener() {
            @Override
            public void onInit(Window window) {
                initGame(window);
            }

            @Override
            public void onFrameRenderEnd(Window window) {

            }
        });
    }

    private void initGame(Window window) {
        AppRenderContext appRenderContext = new AppRenderContext(window);
        appRenderContext.start();
        window.setRenderContext(appRenderContext);
        window.show();
    }
}
