package ru.reactiveturtle.task3;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.shape.ShapeShaderProgram;

import java.util.Objects;

public class Block extends Model2d {
    public static final float BLOCK_SIZE = 1f;

    private final Vector4f fillColor;
    private final Vector4f strokeColor;

    public Block(Vector4f fillColor, Vector4f strokeColor) {
        this.fillColor = Objects.requireNonNull(fillColor);
        this.strokeColor = Objects.requireNonNull(strokeColor);
        init();
        setScale(16f);
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawAllMeshes(shaderProgram);
    }

    public Vector4f getFillColor() {
        return new Vector4f(fillColor);
    }

    public float getScaledSize() {
        return getScaleX() * BLOCK_SIZE;
    }

    public Block copy() {
        return new Block(fillColor, strokeColor);
    }

    private void init() {
        initFillMesh();
        initStrokeMesh();
    }

    private void initFillMesh() {
        int indexCount = 6;
        Mesh fillMesh = createMesh(indexCount, (mesh, shaderProgram) -> {
            ShapeShaderProgram shapeShaderProgram = (ShapeShaderProgram) shaderProgram;
            shapeShaderProgram.loadColor(fillColor);
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        int vertexBufferId = fillMesh.createBuffer();

        final float strokeWidthFactor = 0.1f;
        final float halfWidth = BLOCK_SIZE * (1 - strokeWidthFactor) / 2f;
        final float halfHeight = BLOCK_SIZE * (1 - strokeWidthFactor) / 2f;
        fillMesh.storeArrayBuffer(vertexBufferId, new float[] {
                -halfWidth, -halfHeight,
                -halfWidth, halfHeight,
                halfWidth, halfHeight,
                halfWidth, -halfHeight
        }, 0, 2);

        int indexBufferId = fillMesh.createBuffer();
        fillMesh.storeElementArrayBuffer(indexBufferId, new int[] {
                0, 1, 2,
                2, 0, 3
        });
    }

    private void initStrokeMesh() {
        int indexCount = 24;
        Mesh strokeMesh = createMesh(indexCount, (mesh, shaderProgram) -> {
            ShapeShaderProgram shapeShaderProgram = (ShapeShaderProgram) shaderProgram;
            shapeShaderProgram.loadColor(strokeColor);
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        final int vertexBufferId = strokeMesh.createBuffer();
        final float strokeWidthFactor = 0.1f;
        final float fillHalfWidth = BLOCK_SIZE * (1 - strokeWidthFactor) / 2f;
        final float fillHalfHeight = BLOCK_SIZE * (1 - strokeWidthFactor) / 2f;
        final float strokeWidth = BLOCK_SIZE * strokeWidthFactor / 2f;
        strokeMesh.storeArrayBuffer(vertexBufferId, new float[] {
                -fillHalfWidth, -fillHalfHeight,
                -fillHalfWidth, fillHalfHeight,
                fillHalfWidth, fillHalfHeight,
                fillHalfWidth, -fillHalfHeight,
                -fillHalfWidth - strokeWidth, -fillHalfHeight - strokeWidth,
                -fillHalfWidth - strokeWidth, fillHalfHeight + strokeWidth,
                fillHalfWidth + strokeWidth, fillHalfHeight + strokeWidth,
                fillHalfWidth + strokeWidth, -fillHalfHeight - strokeWidth
        }, 0, 2);

        int indexBufferId = strokeMesh.createBuffer();
        strokeMesh.storeElementArrayBuffer(indexBufferId, new int[] {
                0, 4, 5,
                5, 0, 1,

                1, 5, 6,
                6, 1, 2,

                2, 6, 7,
                7, 2, 3,

                3, 7, 4,
                4, 3, 0
        });
    }
}
