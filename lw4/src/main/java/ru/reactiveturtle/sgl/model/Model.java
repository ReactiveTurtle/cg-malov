package ru.reactiveturtle.sgl.model;

import org.lwjgl.opengl.GL30;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Model implements IModel, Disposable {
    private final Map<Integer, IMesh> meshMap = new HashMap<>();

    @Override
    public void drawAllMeshes(ShaderProgram shaderProgram) {
        for (IMesh mesh : meshMap.values()) {
            mesh.draw(shaderProgram);
        }
    }

    @Override
    public void drawMesh(int meshId, ShaderProgram shaderProgram) {
        meshMap.get(meshId).draw(shaderProgram);
    }

    @Override
    public Mesh createMesh(int indexCount, IMeshDrawer drawer) {
        Mesh mesh = new Mesh(indexCount, drawer);
        meshMap.put(mesh.getId(), mesh);

        return mesh;
    }

    @Override
    public void deleteMesh(int meshId) {
        GL30.glDeleteVertexArrays(meshId);
        meshMap.remove(meshId);
    }

    @Override
    public void dispose() {
        Set<Integer> vertexArrayIdSet = new HashSet<>(meshMap.keySet());
        for (Integer id : vertexArrayIdSet) {
            deleteMesh(id);
        }
        meshMap.clear();
    }
}
