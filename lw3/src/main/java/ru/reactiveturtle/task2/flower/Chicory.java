package ru.reactiveturtle.task2.flower;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.shader.ColorShaderProgram;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.utils.BufferUtils;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

public class Chicory extends Flower {
    private float width = 0;
    private float height = 0;
    private float center = 0;

    private int stemVertexArrayId;
    private int stemVertexBufferId;
    private int stemIndicesBufferId;
    private int stemColorsBufferId;
    private int stemVertexCount;

    private int petalVertexArrayId;
    private int petalVertexBufferId;
    private int petalIndicesBufferId;
    private int petalColorsBufferId;
    private int petalVertexCount;

    private int pestleVertexArrayId;
    private int pestleVertexBufferId;
    private int pestleIndicesBufferId;
    private int pestleColorsBufferId;
    private int pestleVertexCount;

    public Chicory() {
        bufferStem();
        bufferPetal();
        bufferPestle();
        setScale(10f);
    }

    @Override
    public void draw(ShaderProgram shader) {
        ColorShaderProgram shaderProgram = (ColorShaderProgram) shader;
        GL30.glBindVertexArray(stemVertexArrayId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glDrawElements(GL11.GL_TRIANGLES, stemVertexCount, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        GL30.glBindVertexArray(petalVertexArrayId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        float oldY = getY();
        float oldRotation = getRotationRadiansZ();
        setY(oldY + 3f * getScaleY());
        int iterations = 11;
        for (int i = 0; i < iterations; i++) {
            setRotationRadiansZ(oldRotation + (float) (Math.PI * 2f / iterations * i));
            shaderProgram.loadModelMatrix(MatrixUtils.getModelMatrix(this));
            GL20.glDrawElements(GL11.GL_TRIANGLES, petalVertexCount, GL11.GL_UNSIGNED_INT, 0);
        }
        setY(oldY);
        setRotationRadiansZ(oldRotation);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);

        GL30.glBindVertexArray(pestleVertexArrayId);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glDrawElements(GL11.GL_TRIANGLES, pestleVertexCount, GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    @Override
    public float getScaledWidth() {
        return width * getScaleX();
    }

    @Override
    public float getScaledHeight() {
        return height * getScaleY();
    }

    @Override
    public float getCenter() {
        return center * getScaleY();
    }

    @Override
    public void dispose() {

    }

    private void bufferStem() {
        stemVertexArrayId = GL30.glGenVertexArrays();
        stemVertexBufferId = GL30.glGenBuffers();
        stemIndicesBufferId = GL30.glGenBuffers();
        stemColorsBufferId = GL30.glGenBuffers();
        stemVertexCount = 6;

        GL30.glBindVertexArray(stemVertexArrayId);
        height += 3f;
        BufferUtils.storeArrayBuffer(stemVertexBufferId, new float[]{
                -0.1f, 0f,
                -0.1f, 3,
                0.1f, 3,
                0.1f, 0
        }, 0, 2);
        BufferUtils.storeElementArrayBuffer(stemIndicesBufferId, new int[]{
                0, 1, 2,
                2, 0, 3
        });
        BufferUtils.storeArrayBuffer(stemColorsBufferId, new float[]{
                0f, 0.8f, 0f,
                0f, 0.8f, 0f,
                0f, 0.8f, 0f,
                0f, 0.8f, 0f,
        }, 1, 3);
        GL30.glBindVertexArray(0);
    }

    private void bufferPestle() {
        pestleVertexArrayId = GL30.glGenVertexArrays();
        pestleVertexBufferId = GL30.glGenBuffers();
        pestleIndicesBufferId = GL30.glGenBuffers();
        pestleColorsBufferId = GL30.glGenBuffers();

        GL30.glBindVertexArray(pestleVertexArrayId);
        int steps = 16 * 4;
        float[] vertices = new float[steps * 2 + 2];
        vertices[1] = 0;
        center += 0.2f;
        for (int i = 0; i < steps; i++) {
            int index = i * 2 + 2;
            vertices[index] = (float) Math.cos((Math.PI * 2) / (steps - 1) * i) * 0.2f;
            vertices[index + 1] = (float) Math.sin((Math.PI * 2) / (steps - 1) * i) * 0.2f;
        }
        BufferUtils.storeArrayBuffer(pestleVertexBufferId, vertices, 0, 2);

        int[] indices = new int[(steps - 1) * 3];
        pestleVertexCount = (steps - 1) * 3;
        for (int i = 0; i < steps - 1; i++) {
            int index = i * 3;
            indices[index] = 0;
            indices[index + 1] = i + 1;
            indices[index + 2] = i + 2;
        }
        BufferUtils.storeElementArrayBuffer(pestleIndicesBufferId, indices);

        float[] colors = new float[(steps + 1) * 3];
        for (int i = 0; i < steps + 1; i++) {
            int index = i * 3;
            colors[index] = 34 / 255f;
            colors[index + 1] = 41 / 255f;
            colors[index + 2] = 70 / 255f;
        }
        BufferUtils.storeArrayBuffer(pestleColorsBufferId, colors, 1, 3);
        GL30.glBindVertexArray(0);
    }

    private void bufferPetal() {
        petalVertexArrayId = GL30.glGenVertexArrays();
        petalVertexBufferId = GL30.glGenBuffers();
        petalIndicesBufferId = GL30.glGenBuffers();
        petalColorsBufferId = GL30.glGenBuffers();

        GL30.glBindVertexArray(petalVertexArrayId);
        width = 0.8f + 0.8f + 0.2f + 0.2f;
        height += 0.8f;
        center += 0.8f;
        int steps = 10;
        float[] vertices = new float[steps * 2 + 2 + 3 * 2];
        vertices[0] = 0.05f;
        vertices[1] = 0;
        vertices[2] = 0.8f;
        vertices[3] = 0.15f;
        vertices[4] = 0.8f;
        vertices[5] = -0.15f;
        vertices[6] = 0.8f;
        vertices[7] = 0;
        for (int i = 0; i < steps; i++) {
            int index = i * 2 + 8;
            double radians = (Math.PI) / (steps - 1) * i - Math.PI / 2f;
            vertices[index] = (float) Math.cos(radians) * 0.2f + 0.8f;
            vertices[index + 1] = (float) Math.sin(radians) * 0.15f;
        }
        BufferUtils.storeArrayBuffer(petalVertexBufferId, vertices, 0, 2);

        int[] indices = new int[steps * 3 + 3];
        petalVertexCount = indices.length;
        indices[0] = 0;
        indices[1] = 1;
        indices[2] = 2;
        for (int i = 0; i < steps - 1; i++) {
            int index = i * 3 + 3;
            indices[index] = 3;
            indices[index + 1] = i + 4;
            indices[index + 2] = i + 5;
        }
        BufferUtils.storeElementArrayBuffer(petalIndicesBufferId, indices);

        float[] colors = new float[steps * 3 + 4 * 3];
        for (int i = 0, iterations = steps + 4; i < iterations; i++) {
            int index = i * 3;
            colors[index] = 65 / 255f;
            colors[index + 1] = 69 / 255f;
            colors[index + 2] = 228 / 255f;
        }
        BufferUtils.storeArrayBuffer(petalColorsBufferId, colors, 1, 3);
        GL30.glBindVertexArray(0);
    }
}
