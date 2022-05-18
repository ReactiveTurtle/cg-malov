#version 330 core

in vec3 v_vertex;
in vec2 v_textureCoordinate;
in vec3 v_normal;

uniform mat4 v_projectionMatrix;
uniform mat4 v_viewMatrix;
uniform mat4 v_modelMatrix;

out vec3 f_vertex;
out vec2 f_textureCoordinate;
out vec3 f_normal;

void main() {
    gl_Position = v_projectionMatrix * v_viewMatrix * v_modelMatrix * vec4(v_vertex, 1);
    f_vertex = v_vertex;
    f_textureCoordinate = v_textureCoordinate;
    f_normal = v_normal;
}
