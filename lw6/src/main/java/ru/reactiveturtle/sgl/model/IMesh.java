package ru.reactiveturtle.sgl.model;

import ru.reactiveturtle.sgl.shader.ShaderProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface IMesh {
    <T extends ShaderProgram> void draw(T shaderProgram);

    int createBuffer();

    void storeArrayBuffer(int bufferId, float[] data, int index, int size);

    void storeArrayBuffer(int bufferId, FloatBuffer floatBuffer, int index, int size);

    void storeElementArrayBuffer(int arrayBufferId, int[] data);

    void storeElementArrayBuffer(int arrayBufferId, IntBuffer buffer);

    void storeElementArrayBuffer(int arrayBufferId, float[] data);

    void storeElementArrayBuffer(int arrayBufferId, FloatBuffer buffer);

    void clearArrayBufferId(int arrayBufferId);

    int getId();

    int getIndexCount();
}
