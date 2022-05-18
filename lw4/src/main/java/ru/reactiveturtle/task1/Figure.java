package ru.reactiveturtle.task1;

import org.joml.Vector4f;
import org.lwjgl.opengl.*;
import ru.reactiveturtle.sgl.Texture;
import ru.reactiveturtle.sgl.material.Material;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model3d;
import ru.reactiveturtle.sgl.model.Model3dShaderProgram;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class Figure extends Model3d {
    private Material material;

    public Figure() {
        super();
        init();
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawAllMeshes(shaderProgram);
    }

    private void init() {
        material = new Material();
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("src/main/resources/texture/derevo.jpg"));
            material.getTexture().set(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        material.setAmbient(1f, 1f, 1f);
        material.setDiffuse(1f, 1f, 1f);
        material.setSpecular(1f, 1f, 1f);
        material.setReflectance(1000f);

        Mesh figure = createMesh(36, (mesh, shaderProgram) -> {
            Model3dShaderProgram model3dShaderProgram = (Model3dShaderProgram) shaderProgram;
            model3dShaderProgram.loadMaterial(material);

            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);

            GL15.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL_TEXTURE_2D, material.getTexture().getId());

            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

            GL20.glDisableVertexAttribArray(2);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        float b = 1;
        float a = 1.7498526f * b;
        float e = 1.9431513f * b;
        float r = 0.8343915f * b;
        float a2 = (float) Math.sqrt(a * a - (e / 2f) * (e / 2f));

        float dihedralAngle = (float) Math.toRadians(153.18);

        int vertexBufferId = figure.createBuffer();
        figure.storeArrayBuffer(vertexBufferId, new float[]{
                0, 0, 0,
                
        }, 0, 3);

        int indexBufferId = figure.createBuffer();
        figure.storeElementArrayBuffer(indexBufferId, new int[]{

        });

        int textureCoordinateBufferId = figure.createBuffer();
        figure.storeArrayBuffer(textureCoordinateBufferId, new float[]{
        }, 1, 2);

        int normalBufferId = figure.createBuffer();
        figure.storeArrayBuffer(normalBufferId, new float[]{
        }, 2, 3);
    }
}
