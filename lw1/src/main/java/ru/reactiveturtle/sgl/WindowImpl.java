package ru.reactiveturtle.sgl;

import ru.reactiveturtle.sgl.control.Cursor;
import ru.reactiveturtle.sgl.control.Keyboard;

public interface WindowImpl {
    void show();

    void setFullscreen(boolean isFullscreen);

    void setRenderContext(RenderContext renderContext);

    RenderContext getRenderContext();

    double getDeltaTime();

    Cursor getCursor();

    Keyboard getKeyboard();
}
