package ru.reactiveturtle.task2.butterfly;

import org.joml.Vector4f;
import ru.reactiveturtle.sgl.model.Mesh;

public class WingParams {
    private Mesh wing;
    private Vector4f wingColor;
    private float height;

    public WingParams(Mesh wing, Vector4f wingColor, float height) {
        this.wing = wing;
        this.wingColor = wingColor;
        this.height = height;
    }

    public Mesh getWing() {
        return wing;
    }

    public Vector4f getWingColor() {
        return wingColor;
    }

    public float getHeight() {
        return height;
    }
}
