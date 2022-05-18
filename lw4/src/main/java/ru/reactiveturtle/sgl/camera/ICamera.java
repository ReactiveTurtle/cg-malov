package ru.reactiveturtle.sgl.camera;

import org.joml.Matrix4f;

public interface ICamera {
    Matrix4f getProjectionMatrix();

    Matrix4f getViewMatrix();
}
