package ru.reactiveturtle.sgl.model;

import org.joml.Vector2f;
import org.joml.Vector3f;
import ru.reactiveturtle.sgl.transform.ITransform2D;
import ru.reactiveturtle.sgl.transform.Transform2D;

public abstract class Model2d extends Model implements ITransform2D {
    private final Transform2D transform2D = new Transform2D();

    @Override
    public void setOriginX(float x) {
        transform2D.setOriginX(x);
    }

    @Override
    public void setOriginY(float y) {
        transform2D.setOriginY(y);
    }

    @Override
    public void setOrigin(float x, float y) {
        transform2D.setOrigin(x, y);
    }

    @Override
    public void setOrigin(Vector2f origin) {
        transform2D.setOrigin(origin);
    }

    @Override
    public float getOriginX() {
        return transform2D.getOriginX();
    }

    @Override
    public float getOriginY() {
        return transform2D.getOriginY();
    }

    @Override
    public Vector2f getOrigin() {
        return transform2D.getOrigin();
    }

    @Override
    public void setX(float x) {
        transform2D.setX(x);
    }

    @Override
    public void setY(float y) {
        transform2D.setY(y);
    }

    @Override
    public void setPosition(float x, float y) {
        transform2D.setPosition(x, y);
    }

    @Override
    public float getX() {
        return transform2D.getX();
    }

    @Override
    public float getY() {
        return transform2D.getY();
    }

    @Override
    public Vector2f getPosition() {
        return transform2D.getPosition();
    }

    @Override
    public void setPosition(Vector2f position) {
        transform2D.setPosition(position);
    }

    @Override
    public void setRotationRadiansX(float rotationRadiansX) {
        transform2D.setRotationRadiansX(rotationRadiansX);
    }

    @Override
    public void setRotationRadiansY(float rotationRadiansY) {
        transform2D.setRotationRadiansY(rotationRadiansY);
    }

    @Override
    public void setRotationRadiansZ(float rotationRadiansZ) {
        transform2D.setRotationRadiansZ(rotationRadiansZ);
    }

    @Override
    public void setRotationRadians(Vector3f rotationRadians) {
        transform2D.setRotationRadians(rotationRadians);
    }

    @Override
    public void setRotationAngleX(float angleX) {
        transform2D.setRotationAngleX(angleX);
    }

    @Override
    public void setRotationAngleY(float angleY) {
        transform2D.setRotationAngleY(angleY);
    }

    @Override
    public void setRotationAngleZ(float angleZ) {
        transform2D.setRotationAngleZ(angleZ);
    }

    @Override
    public void setRotationAngle(Vector3f angle) {
        transform2D.setRotationAngle(angle);
    }

    @Override
    public float getRotationRadiansX() {
        return transform2D.getRotationRadiansX();
    }

    @Override
    public float getRotationRadiansY() {
        return transform2D.getRotationRadiansY();
    }

    @Override
    public float getRotationRadiansZ() {
        return transform2D.getRotationRadiansZ();
    }

    @Override
    public Vector3f getRotationRadians() {
        return transform2D.getRotationRadians();
    }

    @Override
    public float getRotationAngleX() {
        return transform2D.getRotationAngleX();
    }

    @Override
    public float getRotationAngleY() {
        return transform2D.getRotationAngleY();
    }

    @Override
    public float getRotationAngleZ() {
        return transform2D.getRotationAngleZ();
    }

    @Override
    public Vector3f getRotationAngle() {
        return transform2D.getRotationAngle();
    }

    @Override
    public void setScale(float scale) {
        transform2D.setScale(scale);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        transform2D.setScale(scaleX, scaleY);
    }

    @Override
    public void setScale(Vector2f scale) {
        transform2D.setScale(scale);
    }

    @Override
    public void setScaleX(float x) {
        transform2D.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        transform2D.setScaleY(y);
    }

    @Override
    public Vector2f getScale() {
        return transform2D.getScale();
    }

    @Override
    public float getScaleX() {
        return transform2D.getScaleX();
    }

    @Override
    public float getScaleY() {
        return transform2D.getScaleY();
    }
}
