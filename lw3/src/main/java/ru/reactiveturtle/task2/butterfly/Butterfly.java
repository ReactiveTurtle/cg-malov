package ru.reactiveturtle.task2.butterfly;

import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.shape.ShapeShaderProgram;
import ru.reactiveturtle.sgl.utils.MatrixUtils;
import ru.reactiveturtle.task2.Randomizer;

public class Butterfly extends Model2d {
    private final WingAnimator wingAnimator;
    private final float swingSpeed;
    private final float moveSpeed;
    private Vector4f wingColor;
    private final Vector4f bodyColor = new Vector4f(new Color(165, 42, 42, 255).getComponentsAsVector());

    private double deltaTime = 0;

    public Butterfly() {
        wingAnimator = new WingAnimator(this);
        WingParams wingParams = WingGenerator.generateWing(this, (mesh, shaderProgram) -> {
            ShapeShaderProgram shapeShaderProgram = (ShapeShaderProgram) shaderProgram;
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            shapeShaderProgram.loadColor(wingColor);
            wingAnimator.updateRotation(deltaTime);

            wingAnimator.updateFirstWing();
            shapeShaderProgram.loadModelMatrix(MatrixUtils.getModelMatrix(this));
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

            wingAnimator.updateSecondWing();
            shapeShaderProgram.loadModelMatrix(MatrixUtils.getModelMatrix(this));
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
            setRotationRadiansY(0);
            setRotationRadiansX(0);
        });
        wingColor = wingParams.getWingColor();
        swingSpeed = Randomizer.random(3f, 10f);
        moveSpeed = swingSpeed * 20f;

        createBody(wingParams.getHeight());

        setScale(20f);
        setOriginX(-0.025f);
    }

    public void update(double deltaTime) {
        this.deltaTime = deltaTime;
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawAllMeshes(shaderProgram);
    }

    public float getSwingSpeed() {
        return swingSpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    private void createBody(float wingHeight) {
        Mesh body = createMesh(6, (mesh, shaderProgram) -> {
            ShapeShaderProgram shapeShaderProgram = (ShapeShaderProgram) shaderProgram;
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            shapeShaderProgram.loadModelMatrix(MatrixUtils.getModelMatrix(this));
            shapeShaderProgram.loadColor(bodyColor);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        int vertexBufferId = body.createBuffer();
        float bodyWidth = wingHeight * 0.1f;
        float bodyHeight = wingHeight * 0.7f;
        body.storeArrayBuffer(vertexBufferId, new float[]{
                -bodyWidth / 2f, -bodyHeight / 2f,
                -bodyWidth / 2f, bodyHeight / 2f,
                bodyWidth / 2f, -bodyHeight / 2f,
                bodyWidth / 2f, bodyHeight / 2f
        }, 0, 2);

        int indexBufferId = body.createBuffer();
        body.storeElementArrayBuffer(indexBufferId, new int[]{
                0, 1, 3,
                3, 0, 2
        });
    }

    @Override
    public void setRotationRadiansY(float rotationRadiansY) {
        super.setRotationRadiansY(rotationRadiansY);
    }
}
