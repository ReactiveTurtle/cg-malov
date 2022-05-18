package ru.reactiveturtle.task2.butterfly;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.shape.Shape;
import ru.reactiveturtle.sgl.shape.ShapeShaderProgram;

public class Wing extends Shape {
    private float height;

    public Wing(float[] fillVertices, int[] fillIndices, float height) {
        super(fillVertices, fillIndices, new float[0], new int[0]);
        this.height = height;
    }

    @Override
    public void draw(ShapeShaderProgram shaderProgram) {
        GL30.glBindVertexArray(getFillVertexArrayId());
        GL20.glEnableVertexAttribArray(0);
        shaderProgram.loadColor(new Color(getFillColor()).getComponentsAsVector());
        GL20.glDrawElements(GL11.GL_TRIANGLES, getFillVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public float getHeight() {
        return height;
    }
}
