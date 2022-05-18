package ru.reactiveturtle.task4;

import org.joml.Vector2f;
import org.lwjgl.opengl.*;
import ru.reactiveturtle.sgl.Texture;
import ru.reactiveturtle.sgl.model.*;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class WaveModel extends Model2d {
    private Texture firstTexture;
    private Texture secondTexture;

    private Vector2f clickPoint = null;
    private final float waveOffset = 200f;
    private final float waveSpeed;
    private final float transitionBorderWidth;
    private float waveMaxTime = 0;
    private float waveTimeLeft = 0;
    private Vector2f diagonalVector;

    public WaveModel(float waveSpeed, float transitionBorderWidth) {
        this.waveSpeed = waveSpeed;
        this.transitionBorderWidth = transitionBorderWidth;
        try {
            firstTexture = new Texture(ImageIO.read(
                    Objects.requireNonNull(Main.class.getResourceAsStream("/texture/beach.jpg"))));
            secondTexture = new Texture(ImageIO.read(
                    Objects.requireNonNull(Main.class.getResourceAsStream("/texture/land.jpg"))));
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Mesh waveMesh = createMesh(6, (mesh, shaderProgram) -> {
            WaveShaderProgram waveShaderProgram = (WaveShaderProgram) shaderProgram;
            GL30.glBindVertexArray(mesh.getId());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);

            GL15.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL_TEXTURE_2D, firstTexture.getId());

            GL15.glActiveTexture(GL13.GL_TEXTURE1);
            GL11.glBindTexture(GL_TEXTURE_2D, secondTexture.getId());

            waveShaderProgram.loadWaveData(clickPoint, waveTimeLeft, waveSpeed, waveOffset, transitionBorderWidth);
            GL20.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndexCount(), GL11.GL_UNSIGNED_INT, 0);

            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(0);
            GL30.glBindVertexArray(0);
        });

        int arrayBufferId = waveMesh.createBuffer();
        waveMesh.storeArrayBuffer(arrayBufferId, new float[] {
                -0.5f, -0.5f,
                -0.5f, 0.5f,
                0.5f, 0.5f,
                0.5f, -0.5f
        }, 0, 2);

        int indexBufferId = waveMesh.createBuffer();
        waveMesh.storeElementArrayBuffer(indexBufferId, new int[] {
                0, 1, 2,
                2, 0, 3
        });

        int textureCoordinateBufferId = waveMesh.createBuffer();
        waveMesh.storeArrayBuffer(textureCoordinateBufferId, new float[] {
                0, 1,
                0, 0,
                1, 0,
                1, 1,
        }, 1, 2);
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        drawAllMeshes(shaderProgram);
    }

    public void setClickPoint(Vector2f clickPoint) {
        this.clickPoint = clickPoint;
        waveTimeLeft = 0;
        Vector2f signVector = new Vector2f(clickPoint).negate();
        signVector.x = Math.signum(signVector.x);
        signVector.y = Math.signum(signVector.y);
        waveMaxTime = (new Vector2f(diagonalVector).mul(signVector).sub(clickPoint).length() + waveOffset) / waveSpeed;
    }

    public boolean hasClickPoint() {
        return clickPoint != null;
    }

    public void setDiagonalLength(Vector2f diagonalVector) {
        if (diagonalVector.x <= 0 || diagonalVector.y <= 0) {
            throw new RuntimeException("Invalid vector " + diagonalVector);
        }
        this.diagonalVector = new Vector2f(diagonalVector).div(2);
    }

    public void addTimeLeft(double deltaTime) {
        waveTimeLeft += deltaTime;

        if (waveTimeLeft >= waveMaxTime) {
            clickPoint = null;
            Texture texture = firstTexture;
            firstTexture = secondTexture;
            secondTexture = texture;
            waveTimeLeft %= waveMaxTime;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        firstTexture.dispose();
        secondTexture.dispose();
    }
}
