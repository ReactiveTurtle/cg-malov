package ru.reactiveturtle.task3;

import ru.reactiveturtle.sgl.Application;
import ru.reactiveturtle.sgl.Window;

public class TetrisApp extends Application {
    public void start() {
        createWindow("Tetris", 800, 480, false, new Window.WindowListener() {
            @Override
            public void onInit(Window window) {
                TetrisRenderContext renderContext = new TetrisRenderContext(window);
                window.setRenderContext(renderContext);
                renderContext.start();
                window.show();
            }

            @Override
            public void onFrameRenderEnd(Window window) {

            }
        });
    }
}
