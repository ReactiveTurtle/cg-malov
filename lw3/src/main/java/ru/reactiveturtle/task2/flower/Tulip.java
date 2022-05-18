package ru.reactiveturtle.task2.flower;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

public class Tulip extends Flower {
    private float width = 0;
    private float height = 0;
    private float center = 0;

    public Tulip() {
        createStem();
        createPetal();
        setScale(15f);
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

    private void createStem() {
        int stemVertexCount = 6;
        Mesh stem = createMesh(stemVertexCount, (mesh, shaderProgram) -> {
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glDrawElements(GL11.GL_TRIANGLES, stemVertexCount, GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });
        int vertexBufferId = stem.createBuffer();
        int indexBufferId = stem.createBuffer();
        int colorBufferId = stem.createBuffer();

        height += 1.5f;

        stem.storeArrayBuffer(vertexBufferId, new float[]{
                -0.1f, 0f,
                -0.1f, 1.5f,
                0.1f, 1.5f,
                0.1f, 0
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

    private void createPetal() {
        int steps = 10;
        int elementCount = steps * 3 + 4 * 3;
        Mesh petal = createMesh(elementCount, (mesh, shaderProgram) -> {
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });
        int vertexBufferId = petal.createBuffer();
        int indicesBufferId = petal.createBuffer();
        int colorsBufferId = petal.createBuffer();

        float[] vertices = new float[steps * 2 + 2 + 3 * 2];
        width += 0.8f;
        height += 2.3f;
        center += 1.9f;
        vertices[0] = 0;
        vertices[1] = 1.5f;
        vertices[2] = -0.4f;
        vertices[3] = 2.3f;
        vertices[4] = 0f;
        vertices[5] = 1.9f;
        vertices[6] = 0.4f;
        vertices[7] = 2.3f;
        for (int i = 0; i < steps; i++) {
            int index = i * 2 + 2 + 6;
            double radians = (Math.PI) / (steps - 1) * i - Math.PI;
            vertices[index] = (float) Math.cos(radians) * 0.4f;
            vertices[index + 1] = (float) Math.sin(radians) * 0.3f + 1.5f;
        }
        petal.storeArrayBuffer(vertexBufferId, vertices, 0, 2);

        int[] indices = new int[elementCount];
        indices[0] = 1;
        indices[1] = 0;
        indices[2] = 4;
        indices[3] = 1;
        indices[4] = 0;
        indices[5] = 2;

        indices[6] = 3;
        indices[7] = 0;
        indices[8] = vertices.length / 2 - 1;
        indices[9] = 3;
        indices[10] = 0;
        indices[11] = 2;
        for (int i = 0; i < steps - 1; i++) {
            int index = i * 3 + 4 * 3;
            indices[index] = 0;
            indices[index + 1] = i + 4;
            indices[index + 2] = i + 5;
        }
        petal.storeElementArrayBuffer(indicesBufferId, indices);

        float[] colors = new float[(steps + 5) * 3];
        for (int i = 0, iterations = steps + 5; i < iterations; i++) {
            int index = i * 3;
            colors[index] = 215 / 255f;
            colors[index + 1] = 83 / 255f;
            colors[index + 2] = 94 / 255f;
        }
        petal.storeArrayBuffer(colorsBufferId, colors, 1, 3);
    }
}
