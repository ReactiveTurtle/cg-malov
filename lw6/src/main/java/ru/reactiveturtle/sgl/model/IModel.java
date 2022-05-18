package ru.reactiveturtle.sgl.model;

import ru.reactiveturtle.sgl.shader.ShaderProgram;

public interface IModel {
    void draw(ShaderProgram shaderProgram);

    void drawAllMeshes(ShaderProgram shaderProgram);

    void drawMesh(int meshId, ShaderProgram shaderProgram);

    Mesh createMesh(int indexCount, IMeshDrawer drawer);

    void deleteMesh(int meshId);
}
