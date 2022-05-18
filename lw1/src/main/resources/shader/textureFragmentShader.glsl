#version 330 core

in vec2 f_texCoord;

out vec4 fragColor;

uniform sampler2D f_textureSampler;

void main() {
    fragColor = texture(f_textureSampler, f_texCoord);
}
