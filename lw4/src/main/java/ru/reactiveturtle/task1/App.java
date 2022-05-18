package ru.reactiveturtle.task1;

import ru.reactiveturtle.sgl.Application;
import ru.reactiveturtle.sgl.Window;

public class App extends Application {
    public App() {
        super();
        createWindow("App", 800, 480, false, new Window.WindowListener() {
            @Override
            public void onInit(Window window) {
                AppRenderContext renderContext = new AppRenderContext(window);
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
