package ru.reactiveturtle.task2.diurnalcycle;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

public class Sky extends Model2d {
    private final int colorBufferId;
    private final Mesh sky;

    private Vector3f sunriseSunsetColor = new Vector3f(0xf6 / 255f, 0x47 / 255f, 0x47 / 255f);
    private Vector3f dayTopColor = new Vector3f(0.008f, 0.39f, 0.82f);
    private Vector3f dayBottomColor = new Vector3f(0.5f, 0.83f, 0.98f);
    private Vector3f nightTopColor = new Vector3f(0x38 / 255f, 0x28 / 255f, 0x5c / 255f);
    private Vector3f nightBottomColor = new Vector3f(0x0c / 255f, 0x14 / 255f, 0x45 / 255f);

    public Sky() {
        sky = createMesh(6, (mesh, shaderProgram) -> {
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });
        int vertexBufferId = sky.createBuffer();
        sky.storeArrayBuffer(vertexBufferId, new float[]{
                0f, 0f,
                0f, 1f,
                1f, 0f,
                1f, 1f
        }, 0, 2);
        int indexBufferId = sky.createBuffer();
        sky.storeElementArrayBuffer(indexBufferId, new int[]{
                0, 1, 3,
                3, 0, 2
        });
        colorBufferId = sky.createBuffer();
        sky.storeArrayBuffer(colorBufferId, new float[]{
                0.5f, 0.83f, 0.98f,
                0.008f, 0.39f, 0.82f,
                0.5f, 0.83f, 0.98f,
                0.008f, 0.39f, 0.82f,
        }, 1, 3);
    }

    public void update(float sunRotation) {
        float sin = (float) Math.sin(sunRotation);
        float cos = (float) Math.cos(sunRotation);
        Vector3f leftTopColor = new Vector3f(nightTopColor).lerp(dayTopColor,
                Math.max(Math.min(sin + 0.5f, 1), 0));
        Vector3f rightTopColor = new Vector3f(nightTopColor).lerp(dayTopColor,
                Math.max(Math.min(sin + 0.5f, 1), 0));
        Vector3f leftBottomColor = new Vector3f(nightBottomColor)
                .lerp(sunriseSunsetColor,
                        Math.max(-cos, 0) * -Math.min(sin + 0.1f, 0))
                .lerp(dayBottomColor,
                        (float) Math.max(Math.min(Math.sin(sunRotation) + 0.9f, 1), 0) * Math.max(Math.min(sin + 0.5f, 1f), 0));
        Vector3f rightBottomColor = new Vector3f(nightBottomColor)
                .lerp(sunriseSunsetColor,
                        Math.min(cos * 0.5f + 1f, 1) * Math.min(sin * 0.5f + 0.5f, 1))
                .lerp(dayBottomColor,
                        (float) Math.max(Math.min(Math.sin(sunRotation) + 0.9f, 1), 0) * Math.max(Math.min(sin + 0.5f, 1f), 0));
        sky.storeArrayBuffer(colorBufferId, new float[]{
                leftBottomColor.x, leftBottomColor.y, leftBottomColor.z,
                leftTopColor.x, leftTopColor.y, leftTopColor.z,
                rightBottomColor.x, rightBottomColor.y, rightBottomColor.z,
                rightTopColor.x, rightTopColor.y, rightTopColor.z
        }, 1, 3);
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawAllMeshes(shaderProgram);
    }
}
