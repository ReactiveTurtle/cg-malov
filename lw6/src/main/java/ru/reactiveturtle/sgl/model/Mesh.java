package ru.reactiveturtle.sgl.model;

import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.utils.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Mesh implements IMesh {
    private IMeshDrawer meshDrawer;
    private final int meshId;
    private final int elementCount;
    private final List<Integer> bufferIdList = new ArrayList<>();

    public Mesh(int elementCount, IMeshDrawer meshDrawer) {
        this.elementCount = elementCount;
        this.meshDrawer = meshDrawer;
        meshId = GL30.glGenVertexArrays();
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        meshDrawer.draw(this, shaderProgram);
    }

    @Override
    public int createBuffer() {
        GL30.glBindVertexArray(meshId);
        int bufferId = GL30.glGenBuffers();
        bufferIdList.add(bufferId);
        GL30.glBindVertexArray(0);

        return bufferId;
    }

    @Override
    public void storeArrayBuffer(int bufferId, float[] data, int index, int size) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        storeArrayBuffer(bufferId, buffer, index, size);
    }

    @Override
    public void storeArrayBuffer(int bufferId, FloatBuffer floatBuffer, int index, int size) {
        GL30.glBindVertexArray(meshId);
        BufferUtils.storeArrayBuffer(bufferId, floatBuffer, index, size);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void storeElementArrayBuffer(int arrayBufferId, int[] data) {
        IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(data.length);
        buffer.put(data);

        storeElementArrayBuffer(arrayBufferId, buffer);
    }

    @Override
    public void storeElementArrayBuffer(int arrayBufferId, IntBuffer buffer) {
        GL30.glBindVertexArray(meshId);
        BufferUtils.storeElementArrayBuffer(arrayBufferId, buffer);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void storeElementArrayBuffer(int arrayBufferId, float[] data) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();

        storeElementArrayBuffer(arrayBufferId, buffer);
    }

    @Override
    public void storeElementArrayBuffer(int arrayBufferId, FloatBuffer buffer) {
        GL30.glBindVertexArray(meshId);
        BufferUtils.storeElementArrayBuffer(arrayBufferId, buffer);
        GL30.glBindVertexArray(0);
    }

    @Override
    public void clearArrayBufferId(int arrayBufferId) {
        GL30.glBindVertexArray(meshId);
        for (Integer bufferId : bufferIdList) {
            GL30.glDeleteBuffers(bufferId);
        }
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(meshId);
    }

    @Override
    public int getId() {
        return meshId;
    }

    @Override
    public int getIndexCount() {
        return elementCount;
    }
}
