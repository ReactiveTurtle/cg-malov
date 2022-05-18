package ru.reactiveturtle.task2.diurnalcycle;

import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Glowing extends Model2d implements Disposable {
    private int meshId;

    public Glowing(float coreSize,
                   float haloSize,
                   Vector4f centerColor,
                   Vector4f coreColor,
                   Vector4f haloColor) {
        IntBuffer indicesBuffer = getIndices();
        int indicesCount = indicesBuffer.capacity();
        Mesh glowingMesh = createMesh(indicesCount, (mesh, shaderProgram) -> {
            GL30.glBindVertexArray(meshId);
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnable(GL20.GL_BLEND);
            GL20.glDrawElements(GL11.GL_TRIANGLES, indicesCount, GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisable(GL20.GL_BLEND);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });
        meshId = glowingMesh.getId();
        int vertexBufferId = glowingMesh.createBuffer();
        glowingMesh.storeArrayBuffer(vertexBufferId, getVertices(coreSize, coreSize + haloSize), 0, 2);
        int indexArrayBuffer = glowingMesh.createBuffer();
        glowingMesh.storeElementArrayBuffer(indexArrayBuffer, indicesBuffer);

        int colorBufferId = glowingMesh.createBuffer();
        glowingMesh.storeArrayBuffer(colorBufferId, getColors(centerColor, coreColor, haloColor), 1, 4);
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawMesh(meshId, shaderProgram);
    }

    private static final int STEPS = 64;

    private static FloatBuffer getVertices(float coreSize, float haloSize) {
        final int vertexSize = 2;

        FloatBuffer vertices = BufferUtils.createFloatBuffer((STEPS * 2 + 1) * vertexSize);
        vertices.put(0);
        vertices.put(0);
        for (int i = 0; i < STEPS; i++) {
            double radians = Math.PI * 2 / (STEPS - 1) * i;
            vertices.put((float) Math.cos(radians) * coreSize);
            vertices.put((float) Math.sin(radians) * coreSize);
        }

        for (int i = 0; i < STEPS; i++) {
            double radians = Math.PI * 2 / (STEPS - 1) * i;
            vertices.put((float) Math.cos(radians) * haloSize);
            vertices.put((float) Math.sin(radians) * haloSize);
        }

        return vertices;
    }

    private static IntBuffer getIndices() {
        final int partSize = 3;
        final int sunTriangleCount = STEPS * partSize;
        final int haloTriangleCount = STEPS * 2 * partSize;

        IntBuffer indices = BufferUtils.createIntBuffer(sunTriangleCount + haloTriangleCount);
        for (int i = 0; i < STEPS; i++) {
            indices.put(0);
            indices.put(i + 1);
            indices.put(i + 2);
        }

        for (int i = 0; i < STEPS; i++) {
            indices.put(i + 1);
            indices.put(STEPS + 1 + i);
            indices.put(STEPS + 1 + i + 1);
            indices.put(i + 1);
            indices.put(STEPS + 1 + i + 1);
            indices.put(i + 2);
        }

        return indices;
    }

    private static float[] getColors(Vector4f centerColor, Vector4f coreColor, Vector4f haloColor) {
        final int colorSize = 4;
        final int centerColorOffset = 1;
        float[] colors = new float[(STEPS + centerColorOffset + STEPS) * colorSize];
        colors[0] = centerColor.x;
        colors[1] = centerColor.y;
        colors[2] = centerColor.z;
        colors[3] = centerColor.w;
        for (int i = 0; i < STEPS; i++) {
            int index = (centerColorOffset + i) * colorSize;
            colors[index] = coreColor.x;
            colors[index + 1] = coreColor.y;
            colors[index + 2] = coreColor.z;
            colors[index + 3] = coreColor.w;
        }
        for (int i = 0; i < STEPS; i++) {
            int index = (centerColorOffset + STEPS + i) * colorSize;
            colors[index] = haloColor.x;
            colors[index + 1] = haloColor.y;
            colors[index + 2] = haloColor.z;
            colors[index + 3] = haloColor.w;
        }

        return colors;
    }

    @Override
    public void dispose() {

    }
}
