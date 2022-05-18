package ru.reactiveturtle.sgl.model;

import ru.reactiveturtle.sgl.shader.ShaderProgram;

public interface IMeshDrawer {
    void draw(IMesh mesh, ShaderProgram shaderProgram);
}
