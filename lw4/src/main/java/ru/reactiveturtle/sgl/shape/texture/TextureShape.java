package ru.reactiveturtle.sgl.shape.texture;

import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Texture;
import ru.reactiveturtle.sgl.shader.TextureShaderProgram;
import ru.reactiveturtle.sgl.shape.base.BaseShape;
import ru.reactiveturtle.sgl.utils.BufferUtils;

public abstract class TextureShape extends BaseShape<TextureShaderProgram> implements TextureShapeImpl {
    private Integer texCoordsBufferId;
    private Texture texture;

    public TextureShape(
            float[] fillVertices,
            int[] fillIndices,
            float[] strokeVertices,
            int[] strokeIndices) {
        super(fillVertices, fillIndices, strokeVertices, strokeIndices);
        texCoordsBufferId = null;
    }

    public void setTextureCoordinates(float[] textureCoordinates) {
        GL30.glBindVertexArray(getFillVertexArrayId());
        if (texCoordsBufferId != null) {
            GL15.glDeleteBuffers(texCoordsBufferId);
            texCoordsBufferId = null;
        }

        if (textureCoordinates != null) {
            texCoordsBufferId = GL15.glGenBuffers();
            texCoordsBufferId = BufferUtils.storeArrayBuffer(texCoordsBufferId, textureCoordinates, 1, 2);
        }
        GL30.glBindVertexArray(0);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
        if (texCoordsBufferId != null) {
            GL15.glDeleteBuffers(texCoordsBufferId);
        }
        super.dispose();
    }
}
