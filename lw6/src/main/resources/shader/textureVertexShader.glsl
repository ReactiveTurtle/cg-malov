#version 330 core

in vec2 v_vertex;
in vec2 v_texCoord;

out vec2 f_texCoord;

uniform mat4 v_projectionMatrix;
uniform mat4 v_viewMatrix;
uniform mat4 v_modelMatrix;

void main() {
    gl_Position = v_projectionMatrix * v_viewMatrix * v_modelMatrix * vec4(v_vertex, 0, 1);
    f_texCoord = v_texCoord;
}
