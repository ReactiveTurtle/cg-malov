#version 330 core

struct DirectionalLight {
    vec4 direction;
    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
};

struct Material {
    int hasTexture;
    sampler2D textureSampler;

    vec4 ambient;
    vec4 diffuse;
    vec4 specular;
    vec4 emission;
    float reflectance;
};

in vec3 f_vertex;
in vec2 f_textureCoordinate;
in vec3 f_normal;

uniform mat4 f_modelMatrix;
uniform mat4 f_viewMatrix;

uniform int f_hasDirectionalLight;
uniform DirectionalLight f_directionalLight;

uniform int f_hasMaterial;
uniform Material f_material;

out vec4 fragColor;

vec3 cameraDirection;

vec4 getDirectionalLightColor() {
    vec3 lightDirection = normalize(-f_directionalLight.direction.xyz);

    vec4 color = f_material.emission;

    color += f_material.ambient * f_directionalLight.ambient;

    float nDotL = max(dot(f_normal, lightDirection), 0.0f);
    color += f_material.diffuse * f_directionalLight.diffuse * nDotL;

    float rDotVPow = max(pow(dot(reflect(-lightDirection, f_normal), cameraDirection), f_material.reflectance), 0.0f);
    color += f_material.specular * f_directionalLight.specular * rDotVPow;

    return color;
}

void main() {
    vec4 f_vertex4 = vec4(f_vertex, 1);
    vec4 modelVertexPosition = f_modelMatrix * f_vertex4;
    vec3 viewPosition = vec3(f_viewMatrix[3][0], f_viewMatrix[3][1], f_viewMatrix[3][2]);
    cameraDirection = normalize(viewPosition - vec3(modelVertexPosition.xyz));

    fragColor = getDirectionalLightColor() * f_hasDirectionalLight;
    fragColor *= texture(f_material.textureSampler, f_textureCoordinate) * f_hasMaterial * f_material.hasTexture;
}
