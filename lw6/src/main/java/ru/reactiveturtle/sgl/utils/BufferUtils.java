package ru.reactiveturtle.sgl.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public final class BufferUtils {
    public static int storeArrayBuffer(int bufferId, float[] data, int index, int size) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);

        storeArrayBuffer(bufferId, buffer, index, size);

        return bufferId;
    }

    public static int storeArrayBuffer(int bufferId, FloatBuffer buffer, int index, int size) {
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferId;
    }

    public static int storeElementArrayBuffer(int bufferId, int[] dataArray) {
        IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(dataArray.length);
        buffer.put(dataArray);

        storeElementArrayBuffer(bufferId, buffer);

        return bufferId;
    }

    public static int storeElementArrayBuffer(int bufferId, float[] dataArray) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(dataArray.length);
        buffer.put(dataArray);

        storeElementArrayBuffer(bufferId, buffer);

        return bufferId;
    }

    public static int storeElementArrayBuffer(int bufferId, IntBuffer buffer) {
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferId;
    }

    public static int storeElementArrayBuffer(int bufferId, FloatBuffer buffer) {
        buffer.flip();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferId);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        return bufferId;
    }
}
