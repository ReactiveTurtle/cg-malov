package ru.reactiveturtle.task1;

import ru.reactiveturtle.sgl.Application;
import ru.reactiveturtle.sgl.Window;

public class Main {
    public static void main(String[] args) {
        Application application = new Application();
        application.createWindow("task1", 800, 480, false, new Window.WindowListener() {
            @Override
            public void onInit(Window window) {
                window.show();
                AppRenderContext appRenderContext = new AppRenderContext(window);
                appRenderContext.start();
                window.setRenderContext(appRenderContext);
            }

            @Override
            public void onFrameRenderEnd(Window window) {

            }
        });
    }
}
