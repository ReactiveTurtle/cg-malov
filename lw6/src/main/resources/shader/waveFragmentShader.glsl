#version 330 core

in vec2 f_vertex;
in vec2 f_texCoord;

out vec4 fragColor;

uniform vec2 f_clickPoint;
uniform vec2 f_scale;
uniform sampler2D f_firstTextureSampler;
uniform sampler2D f_secondTextureSampler;
uniform float f_waveTimePassed;
uniform float f_waveSpeed;
uniform float f_transitionBorderWidth;
uniform float f_waveOffset;

void main() {
    vec4 firstColor = texture(f_firstTextureSampler, f_texCoord);
    vec4 secondColor = texture(f_secondTextureSampler, f_texCoord);
    float vectorLength = length((f_vertex * f_scale - f_clickPoint));
    float waveProgress = f_waveTimePassed * f_waveSpeed;

    float startTransitionFactor = min(1, max((vectorLength + f_waveOffset - waveProgress) / f_transitionBorderWidth, 0));
    float waveProgressFactor = 1 / 8f;
    float cos = cos(waveProgress * waveProgressFactor - vectorLength * waveProgressFactor);
    float cosFactor = min(cos / 2f + 0.75f, 1);
    float waveFactor = sign(floor(vectorLength / waveProgress));
    float firstColorFactor = (cosFactor * (1 - waveFactor) + waveFactor) * startTransitionFactor;

    fragColor = firstColor * firstColorFactor;
    fragColor += secondColor * (1 - startTransitionFactor);
}
