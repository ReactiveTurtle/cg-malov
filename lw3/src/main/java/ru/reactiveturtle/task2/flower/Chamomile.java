package ru.reactiveturtle.task2.flower;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.shader.ColorShaderProgram;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

// Одуванчик
public class Chamomile extends Flower {
    private float width = 0;
    private float height = 0;
    private float center = 0;

    public Chamomile() {
        bufferStem();
        bufferPetal();
        bufferPestle();
        setScale(10f);
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawAllMeshes(shaderProgram);
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
        int indexCount = 6;
        Mesh stem = createMesh(indexCount, (mesh, shaderProgram) -> {
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });
        int vertexBufferId = stem.createBuffer();
        int indexBufferId = stem.createBuffer();
        int colorBufferId = stem.createBuffer();

        height += 4f;
        center += 4f;
        stem.storeArrayBuffer(vertexBufferId, new float[]{
                -0.2f, 0f,
                -0.2f, 4,
                0.2f, 4,
                0.2f, 0
        }, 0, 2);
        stem.storeElementArrayBuffer(indexBufferId, new int[]{
                0, 1, 2,
                2, 0, 3
        });
        stem.storeArrayBuffer(colorBufferId, new float[]{
                0f, 0.8f, 0f,
                0f, 0.8f, 0f,
                0f, 0.8f, 0f,
                0f, 0.8f, 0f,
        }, 1, 3);
    }

    private void bufferPestle() {
        int steps = 16 * 4;
        int indexCount = (steps - 1) * 3;
        Mesh pestle = createMesh(indexCount, (mesh, shader) -> {
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        int vertexBufferId = pestle.createBuffer();
        int indexBufferId = pestle.createBuffer();
        int colorBufferId = pestle.createBuffer();

        float[] vertices = new float[steps * 2 + 2];
        vertices[1] = 0;
        center += 0.2f;
        for (int i = 0; i < steps; i++) {
            int index = i * 2 + 2;
            vertices[index] = (float) Math.cos((Math.PI * 2) / (steps - 1) * i) * 0.2f;
            vertices[index + 1] = (float) Math.sin((Math.PI * 2) / (steps - 1) * i) * 0.2f;
        }
        pestle.storeArrayBuffer(vertexBufferId, vertices, 0, 2);

        int[] indices = new int[indexCount];
        for (int i = 0; i < steps - 1; i++) {
            int index = i * 3;
            indices[index] = 0;
            indices[index + 1] = i + 1;
            indices[index + 2] = i + 2;
        }
        pestle.storeElementArrayBuffer(indexBufferId, indices);

        float[] colors = new float[(steps + 1) * 3];
        for (int i = 0; i < steps + 1; i++) {
            int index = i * 3;
            colors[index] = 0.9f;
            colors[index + 1] = 0.9f;
            colors[index + 2] = 0f;
        }
        pestle.storeArrayBuffer(colorBufferId, colors, 1, 3);
    }

    private void bufferPetal() {
        int steps = 10;
        int indexCount = steps * 3 + 3;
        Mesh petal = createMesh(indexCount, (mesh, shader) -> {
            ColorShaderProgram shaderProgram = (ColorShaderProgram) shader;
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            float oldY = getY();
            float oldRotation = getRotationRadiansZ();
            setY(oldY + 4f * getScaleY());
            int iterations = 16;
            for (int i = 0; i < iterations; i++) {
                setRotationRadiansZ(oldRotation + (float) (Math.PI * 2f / iterations * i));
                shaderProgram.loadModelMatrix(MatrixUtils.getModelMatrix(this));
                GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            setY(oldY);
            setRotationRadiansZ(oldRotation);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        int vertexBufferId = petal.createBuffer();
        int indexBufferId = petal.createBuffer();
        int colorBufferId = petal.createBuffer();

        width = 1f + 1f + 0.4f + 0.4f;
        height += 1f + 0.4f;
        float[] vertices = new float[steps * 2 + 2 + 3 * 2];
        vertices[0] = 0.05f;
        vertices[1] = 0;
        vertices[2] = 1f;
        vertices[3] = 0.15f;
        vertices[4] = 1f;
        vertices[5] = -0.15f;
        vertices[6] = 1f;
        vertices[7] = 0;
        for (int i = 0; i < steps; i++) {
            int index = i * 2 + 8;
            double radians = (Math.PI) / (steps - 1) * i - Math.PI / 2f;
            vertices[index] = (float) Math.cos(radians) * 0.4f + 1f;
            vertices[index + 1] = (float) Math.sin(radians) * 0.15f;
        }
        petal.storeArrayBuffer(vertexBufferId, vertices, 0, 2);

        int[] indices = new int[indexCount];
        indices[0] = 0;
        indices[1] = 1;
        indices[2] = 2;
        for (int i = 0; i < steps - 1; i++) {
            int index = i * 3 + 3;
            indices[index] = 3;
            indices[index + 1] = i + 4;
            indices[index + 2] = i + 5;
        }
        petal.storeElementArrayBuffer(indexBufferId, indices);

        float[] colors = new float[steps * 3 + 4 * 3];
        for (int i = 0, iterations = steps + 4; i < iterations; i++) {
            int index = i * 3;
            colors[index] = 1;
            colors[index + 1] = 1;
            colors[index + 2] = 1;
        }
        petal.storeArrayBuffer(colorBufferId, colors, 1, 3);
    }
}
