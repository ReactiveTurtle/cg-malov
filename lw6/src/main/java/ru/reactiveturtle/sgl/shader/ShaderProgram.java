package ru.reactiveturtle.sgl.shader;

import org.joml.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import ru.reactiveturtle.sgl.Disposable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public abstract class ShaderProgram implements Disposable {
    private final int programID;

    private int vertexShaderID;

    private int fragmentShaderID;

    private final String vertexShaderFile;
    private final String fragmentShaderFile;

    protected boolean isBind = false;

    protected ShaderProgram(String vertexShaderFile, String fragmentShaderFile) {
        programID = glCreateProgram();
        if (programID == 0) {
            System.err.println("Could not create Shader");
        }

        this.vertexShaderFile = vertexShaderFile;
        this.fragmentShaderFile = fragmentShaderFile;
    }

    public abstract void bindAllAttributes();

    public abstract void getAllUniforms();

    public abstract void load(Object data);

    public void bind() {
        if (!isBind) {
            glUseProgram(programID);
            isBind = true;
        }
    }

    public void unbind() {
        if (isBind) {
            glUseProgram(0);
            isBind = false;
        }
    }

    public boolean isBind() {
        return isBind;
    }

    public void dispose() {
        unbind();
        if (programID != 0) {
            glDeleteProgram(programID);
        }
    }

    protected void create() {
        createVertexShader();
        createFragmentShader();

        bindAllAttributes();

        link();

        getAllUniforms();
    }

    protected void bindAttribute(int index, String varName) {
        GL20.glBindAttribLocation(programID, index, varName);
    }

    protected int getUniform(String name) {
        return GL20.glGetUniformLocation(programID, name);
    }

    public void loadIntUniform(int location, int value) {
        glUniform1i(location, value);
    }

    public void loadFloatUniform(int location, float value) {
        glUniform1f(location, value);
    }

    public void loadFloatArrayUniform(int location, float[] values) {
        glUniform1fv(location, values);
    }

    public void loadVector2fUniform(int location, Vector2f value) {
        glUniform2f(location, value.x, value.y);
    }

    public void loadVector3fUniform(int location, Vector3f value) {
        glUniform3f(location, value.x, value.y, value.z);
    }

    public void loadVector3fArrayUniform(int location, Vector3f[] values) {
        float[] buffer = new float[values.length * 3];
        for (int i = 0; i < buffer.length; i += 3) {
            Vector3f vector3f = values[(i - i % 3) / 3];
            buffer[i] = vector3f.x;
            buffer[i + 1] = vector3f.y;
            buffer[i + 2] = vector3f.z;

        }
        glUniform3fv(location, buffer);
    }

    public void loadVector4fUniform(int location, Vector4f value) {
        glUniform4f(location, value.x, value.y, value.z, value.w);
    }

    public void loadMatrix3fUniform(int location, Matrix3f matrix3f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
        matrix3f.get(buffer);
        glUniformMatrix3fv(location, false, buffer);
        buffer.flip();
    }

    public void loadMatrix4fUniform(int location, Matrix4f matrix4f) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        matrix4f.get(buffer);
        glUniformMatrix4fv(location, false, buffer);
        buffer.flip();
    }

    private void createVertexShader() {
        if (vertexShaderFile != null)
            vertexShaderID = createShader(vertexShaderFile, GL_VERTEX_SHADER);
    }


    private void createFragmentShader() {
        if (fragmentShaderFile != null)
            fragmentShaderID = createShader(fragmentShaderFile, GL_FRAGMENT_SHADER);
    }

    private int createShader(String shaderFile, int shaderType) {
        int shaderID = GL20.glCreateShader(shaderType);

        if (shaderID == 0) {
            throw new RuntimeException("Error creating " + shaderTypeToString(shaderType) + " shader");
        }

        GL20.glShaderSource(shaderID, readFile(shaderFile));

        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error compiling " + shaderTypeToString(shaderType) + " shader - " + GL20.glGetShaderInfoLog(shaderID));
        }

        GL20.glAttachShader(programID, shaderID);

        return shaderID;
    }

    private void link() {
        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error: Program Linking - " + glGetProgramInfoLog(programID));
        }

        if (vertexShaderID != 0) {
            glDetachShader(programID, vertexShaderID);
        }
        if (fragmentShaderID != 0) {
            glDetachShader(programID, fragmentShaderID);
        }

        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            throw new RuntimeException("Error: Program Validating\n" + glGetProgramInfoLog(programID));
        }
    }

    private String readFile(String file) {
        StringBuilder string = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                string.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException("Error: Couldn't find file " + new File(file).getAbsolutePath());
        }
        return string.toString();
    }

    private String shaderTypeToString(int shaderType) {
        switch (shaderType) {
            case GL_VERTEX_SHADER:
                return "'" + vertexShaderFile + "' vertex";
            case GL_FRAGMENT_SHADER:
                return "'" + fragmentShaderFile + "' fragment";
            default:
                throw new IllegalStateException("Unknown shader type " + shaderType);
        }
    }

    protected static int toBoolInt(Object obj) {
        return obj == null ? 0 : 1;
    }
}
