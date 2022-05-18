package ru.reactiveturtle.sgl.model;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import ru.reactiveturtle.sgl.light.DirectionalLight;
import ru.reactiveturtle.sgl.material.Material;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.shader.ShaderProgram;

public class Model3dShaderProgram extends ShaderProgram {
    private int v_projectionMatrixLocation;
    private int v_viewMatrixLocation;
    private int v_modelMatrixLocation;
    private int f_viewMatrixLocation;
    private int f_modelMatrixLocation;

    private int f_hasDirectionalLight;
    private int f_directionalLightDirectionLocation;
    private int f_directionalLightAmbientLocation;
    private int f_directionalLightDiffuseLocation;
    private int f_directionalLightSpecularLocation;

    private int f_hasMaterialLocation;
    private int f_materialHasTextureLocation;
    private int f_materialTextureSamplerLocation;
    private int f_materialAmbientLocation;
    private int f_materialDiffuseLocation;
    private int f_materialSpecularLocation;
    private int f_materialEmissionLocation;
    private int f_materialReflectanceLocation;

    public Model3dShaderProgram() {
        super("src/main/resources/shader/3dVertexShader.glsl",
                "src/main/resources/shader/3dFragmentShader.glsl");
        create();
    }

    @Override
    public void bindAllAttributes() {
        super.bindAttribute(0, "v_vertex");
        super.bindAttribute(1, "v_textureCoordinate");
        super.bindAttribute(2, "v_normal");
    }

    @Override
    public void getAllUniforms() {
        v_projectionMatrixLocation = getUniform("v_projectionMatrix");
        v_viewMatrixLocation = getUniform("v_viewMatrix");
        v_modelMatrixLocation = getUniform("v_modelMatrix");

        f_viewMatrixLocation = getUniform("f_viewMatrix");
        f_modelMatrixLocation = getUniform("f_modelMatrix");

        f_hasDirectionalLight = getUniform("f_hasDirectionalLight");
        f_directionalLightDirectionLocation = getUniform("f_directionalLight.direction");
        f_directionalLightAmbientLocation = getUniform("f_directionalLight.ambient");
        f_directionalLightDiffuseLocation = getUniform("f_directionalLight.diffuse");
        f_directionalLightSpecularLocation = getUniform("f_directionalLight.specular");

        f_hasMaterialLocation = getUniform("f_hasMaterial");
        f_materialHasTextureLocation = getUniform("f_material.hasTexture");
        f_materialTextureSamplerLocation = getUniform("f_material.textureSampler");
        f_materialAmbientLocation = getUniform("f_material.ambient");
        f_materialDiffuseLocation = getUniform("f_material.diffuse");
        f_materialSpecularLocation = getUniform("f_material.specular");
        f_materialEmissionLocation = getUniform("f_material.emission");
        f_materialReflectanceLocation = getUniform("f_material.reflectance");
    }

    @Override
    public void load(Object data) {
        ShaderMatrixData shaderData = (ShaderMatrixData) data;
        loadMatrix4fUniform(v_projectionMatrixLocation, shaderData.getProjectionMatrix());
        loadMatrix4fUniform(v_viewMatrixLocation, shaderData.getViewMatrix());
        loadMatrix4fUniform(f_viewMatrixLocation, shaderData.getViewMatrix());
        loadModelMatrix(shaderData.getModelMatrix());
    }

    public void loadModelMatrix(Matrix4f modelMatrix) {
        loadMatrix4fUniform(v_modelMatrixLocation, modelMatrix);
        loadMatrix4fUniform(f_modelMatrixLocation, modelMatrix);
    }

    public void loadDirectionalLight(DirectionalLight directionalLight) {
        loadIntUniform(f_hasDirectionalLight, directionalLight != null ? 1 : 0);
        if (directionalLight != null) {
            loadVector4fUniform(f_directionalLightDirectionLocation, new Vector4f(directionalLight.getDirection(), 0));
            loadVector4fUniform(f_directionalLightAmbientLocation, new Vector4f(directionalLight.getAmbient(), 1));
            loadVector4fUniform(f_directionalLightDiffuseLocation, new Vector4f(directionalLight.getDiffuse(), 1));
            loadVector4fUniform(f_directionalLightSpecularLocation, new Vector4f(directionalLight.getSpecular(), 1));
        }
    }

    public void loadMaterial(Material material) {
        loadIntUniform(f_hasMaterialLocation, toBoolInt(material));
        if (material == null) {
            return;
        }
        loadIntUniform(f_materialHasTextureLocation, toBoolInt(material.getTexture()));
        loadIntUniform(f_materialTextureSamplerLocation, 0);
        loadVector4fUniform(f_materialAmbientLocation, new Vector4f(material.getAmbient(), 1));
        loadVector4fUniform(f_materialDiffuseLocation, new Vector4f(material.getDiffuse(), 1));
        loadVector4fUniform(f_materialSpecularLocation, new Vector4f(material.getSpecular(), 1));
        loadVector4fUniform(f_materialEmissionLocation, new Vector4f(material.getEmission(), 1));
        loadFloatUniform(f_materialReflectanceLocation, material.getReflectance());
    }
}
