package ru.reactiveturtle.task4;

import ru.reactiveturtle.sgl.Application;
import ru.reactiveturtle.sgl.Window;

public class App extends Application {
    public void start() {
        createWindow("Task 4.2", 800, 480, false, new Window.WindowListener() {
            @Override
            public void onInit(Window window) {
                AppRenderContext appRenderContext = new AppRenderContext(window);
                appRenderContext.start();
                window.setRenderContext(appRenderContext);
                window.show();
            }

            @Override
            public void onFrameRenderEnd(Window window) {

            }
        });
    }
}
