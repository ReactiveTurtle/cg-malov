package ru.reactiveturtle.sgl.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Window;
import ru.reactiveturtle.sgl.transform.Transform2D;
import ru.reactiveturtle.sgl.transform.Transform3D;

public class OrthographicCamera extends Transform3D implements ICamera {
    private float width;
    private float height;

    private final Matrix4f viewMatrix = new Matrix4f().identity()
            .translate(-getX(), -getY(), -getZ());
    private final Matrix4f projectionMatrix = new Matrix4f().identity();

    public OrthographicCamera(float width, float height) {
        this.width = width;
        this.height = height;
        projectionMatrix.ortho(
                -width / 2f,
                width / 2f,
                -height / 2f,
                height / 2f,
                0,
                200f);
    }

    public void setWidth(float width) {
        this.width = width;
        projectionMatrix.identity().ortho(
                -width / 2f,
                width / 2f,
                -height / 2f,
                height / 2f,
                0,
                200f);
    }

    public void setHeight(float height) {
        this.height = height;
        projectionMatrix.identity().ortho(
                -width / 2f,
                width / 2f,
                -height / 2f,
                height / 2f,
                0,
                200f);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Vector2f getCursorPosition(Window window, Vector2f cursorPosition) {
        Vector2f cursorCameraPosition = new Vector2f();
        cursorCameraPosition.x = (cursorPosition.x / window.getWidth() - 0.5f) * getWidth();
        cursorCameraPosition.y = (1 - cursorPosition.y / window.getHeight() - 0.5f) * getHeight();
        return cursorCameraPosition;
    }

    @Override
    public void setX(float x) {
        super.setX(x);
        updateViewMatrix();
    }

    @Override
    public void setY(float y) {
        super.setY(y);
        updateViewMatrix();
    }

    @Override
    public void setZ(float z) {
        super.setZ(z);
        updateViewMatrix();
    }

    @Override
    public void setRotationRadiansX(float rotationRadiansX) {
        super.setRotationRadiansX(rotationRadiansX);
        updateViewMatrix();
    }

    @Override
    public void setRotationRadiansZ(float rotationRadians) {
        super.setRotationRadiansZ(rotationRadians);
        updateViewMatrix();
    }

    @Override
    public void setScaleX(float x) {
        super.setScaleX(x);
        updateViewMatrix();
    }

    @Override
    public void setScaleY(float y) {
        super.setScaleY(y);
        updateViewMatrix();
    }

    @Override
    public void setScaleZ(float z) {
        super.setScaleZ(z);
        updateViewMatrix();
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        return new Matrix4f(projectionMatrix);
    }

    @Override
    public Matrix4f getViewMatrix() {
        return new Matrix4f(viewMatrix);
    }

    private void updateViewMatrix() {
        viewMatrix.identity()
                .rotateXYZ(getRotationRadians())
                .translate(-getX(), -getY(), -getZ())
                .scale(getScaleX(), getScaleY(), getScaleZ());
    }
}
