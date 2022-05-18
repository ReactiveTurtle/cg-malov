package ru.reactiveturtle.sgl.ui;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector4f;
import ru.reactiveturtle.sgl.Color;

public interface IElement {
    void setBackgroundColor(Color color);

    Color getBackgroundColor();

    void setWidth(int width);

    void setHeight(int height);

    void setSize(int width, int height);

    void setSize(Vector2i size);

    int getWidth();

    int getHeight();

    Vector2i getSize();

    void setAdaptType(AdaptType adaptType);

    AdaptType getAdaptType();

    float getAdaptedWidth();

    float getAdaptedHeight();

    Vector2i getAdaptedSize();

    void setMarginLeft(int marginLeft);

    void setMarginTop(int marginTop);

    void setMarginRight(int marginRight);

    void setMarginBottom(int marginBottom);

    int getMarginLeft();

    int getMarginTop();

    int getMarginRight();

    int getMarginBottom();

    Margins getMargins();
}
