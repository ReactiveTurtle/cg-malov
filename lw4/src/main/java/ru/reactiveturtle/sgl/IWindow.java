package ru.reactiveturtle.sgl;

import org.joml.Vector2f;
import org.joml.Vector2i;
import ru.reactiveturtle.sgl.control.Cursor;
import ru.reactiveturtle.sgl.control.Keyboard;

public interface IWindow {
    void show();

    void setFullscreen(boolean isFullscreen);

    void setRenderContext(RenderContext renderContext);

    RenderContext getRenderContext();

    int getWidth();

    int getHeight();

    Vector2f getSize();

    double getDeltaTime();

    Cursor getCursor();

    Keyboard getKeyboard();

    Vector2i getPosition();
}
