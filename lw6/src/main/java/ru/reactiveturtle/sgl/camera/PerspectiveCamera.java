package ru.reactiveturtle.sgl.camera;

import org.joml.Matrix4f;
import ru.reactiveturtle.sgl.transform.Transform3D;

public class PerspectiveCamera extends Transform3D implements ICamera {
    private float aspectRatio;
    private float fovRadians;
    private float near;
    private float far;
    private final Matrix4f viewMatrix = new Matrix4f().identity()
            .translate(-getX(), -getY(), -getZ());
    private final Matrix4f projectionMatrix = new Matrix4f().identity();

    public PerspectiveCamera(float fovRadians, float aspectRatio, float near, float far) {
        this.fovRadians = fovRadians;
        this.aspectRatio = aspectRatio;
        this.near = near;
        this.far = far;
        setAspectRatio(aspectRatio);
    }

    public void setFovRadians(float fovRadians) {
        this.fovRadians = fovRadians;
        updateProjectionMatrix();
    }

    public float getFovRadians() {
        return fovRadians;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
        updateProjectionMatrix();
    }

    public float getAspectRatio() {
        return aspectRatio;
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

    private void updateProjectionMatrix() {
        projectionMatrix.perspective(getFovRadians(), aspectRatio, near, far);
    }

    private void updateViewMatrix() {
        viewMatrix.identity()
                .rotateXYZ(getRotationRadians())
                .translate(-getX(), -getY(), -getZ())
                .scale(getScaleX(), getScaleY(), getScaleZ());
    }
}
