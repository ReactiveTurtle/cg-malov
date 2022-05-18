package ru.reactiveturtle.sgl;

import org.joml.Vector2f;
import org.joml.Vector3f;

public interface ITransform2D {
    void setOriginX(float x);

    void setOriginY(float y);

    void setOrigin(float x, float y);

    void setOrigin(Vector2f origin);

    float getOriginX();

    float getOriginY();

    Vector2f getOrigin();

    void setX(float x);

    void setY(float y);

    void setPosition(float x, float y);

    float getX();

    float getY();

    Vector2f getPosition();

    void setPosition(Vector2f position);

    void setRotationRadiansX(float rotationRadiansX);

    void setRotationRadiansY(float rotationRadiansY);

    void setRotationRadiansZ(float rotationRadiansZ);

    void setRotationRadians(Vector3f rotationRadians);

    void setRotationAngleX(float angleX);

    void setRotationAngleY(float angleY);

    void setRotationAngleZ(float angleZ);

    void setRotationAngle(Vector3f angle);

    float getRotationRadiansX();

    float getRotationRadiansY();

    float getRotationRadiansZ();

    Vector3f getRotationRadians();

    float getRotationAngleX();

    float getRotationAngleY();

    float getRotationAngleZ();

    Vector3f getRotationAngle();

    void setScale(float scale);

    void setScale(float scaleX, float scaleY);

    void setScale(Vector2f scale);

    void setScaleX(float x);

    void setScaleY(float y);

    Vector2f getScale();

    float getScaleX();

    float getScaleY();
}
