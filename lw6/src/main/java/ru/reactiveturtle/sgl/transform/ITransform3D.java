package ru.reactiveturtle.sgl.transform;

import org.joml.Vector3f;

public interface ITransform3D {
    void setOriginX(float x);

    void setOriginY(float y);

    void setOriginZ(float z);

    void setOrigin(float x, float y, float z);

    void setOrigin(Vector3f origin);

    float getOriginX();

    float getOriginY();

    float getOriginZ();

    Vector3f getOrigin();

    void setX(float x);

    void setY(float y);

    void setZ(float z);

    void setPosition(float x, float y, float z);

    void setPosition(Vector3f position);

    float getX();

    float getY();

    float getZ();

    Vector3f getPosition();

    void setRotationRadiansX(float rotationRadiansX);

    void setRotationRadiansY(float rotationRadiansY);

    void setRotationRadiansZ(float rotationRadiansZ);

    void setRotationRadians(float radiansX, float radiansY, float radiansZ);

    void setRotationRadians(Vector3f rotationRadians);

    void setRotationAngleX(float angleX);

    void setRotationAngleY(float angleY);

    void setRotationAngleZ(float angleZ);

    void setRotationAngle(float angleX, float angleY, float angleZ);

    void setRotationAngle(Vector3f angle);

    float getRotationRadiansX();

    float getRotationRadiansY();

    float getRotationRadiansZ();

    Vector3f getRotationRadians();

    float getRotationAngleX();

    float getRotationAngleY();

    float getRotationAngleZ();

    Vector3f getRotationAngle();

    void setScaleX(float x);

    void setScaleY(float y);

    void setScaleZ(float z);

    void setScale(float scale);

    void setScale(float scaleX, float scaleY, float scaleZ);

    void setScale(Vector3f scale);

    float getScaleX();

    float getScaleY();

    float getScaleZ();

    Vector3f getScale();
}
