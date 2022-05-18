package ru.reactiveturtle.sgl.model;

import org.joml.Vector3f;
import ru.reactiveturtle.sgl.transform.ITransform3D;
import ru.reactiveturtle.sgl.transform.Transform3D;

public abstract class Model3d extends Model implements ITransform3D {
    private final Transform3D transform = new Transform3D();

    @Override
    public void setOriginX(float x) {
        transform.setOriginX(x);
    }

    @Override
    public void setOriginY(float y) {
        transform.setOriginY(y);
    }

    @Override
    public void setOriginZ(float z) {
        transform.setOriginZ(z);
    }

    @Override
    public void setOrigin(float x, float y, float z) {
        transform.setOrigin(x, y, z);
    }

    @Override
    public void setOrigin(Vector3f origin) {
        transform.setOrigin(origin);
    }

    @Override
    public float getOriginX() {
        return transform.getOriginX();
    }

    @Override
    public float getOriginY() {
        return transform.getOriginY();
    }

    @Override
    public float getOriginZ() {
        return transform.getOriginZ();
    }

    @Override
    public Vector3f getOrigin() {
        return transform.getOrigin();
    }

    @Override
    public void setX(float x) {
        transform.setX(x);
    }

    @Override
    public void setY(float y) {
        transform.setY(y);
    }

    @Override
    public void setZ(float z) {
        transform.setZ(z);
    }

    @Override
    public void setPosition(float x, float y, float z) {
        transform.setPosition(x, y, z);
    }

    @Override
    public void setPosition(Vector3f position) {
        transform.setPosition(position);
    }

    @Override
    public float getX() {
        return transform.getX();
    }

    @Override
    public float getY() {
        return transform.getY();
    }

    @Override
    public float getZ() {
        return transform.getZ();
    }

    @Override
    public Vector3f getPosition() {
        return transform.getPosition();
    }

    @Override
    public void setRotationRadiansX(float rotationRadiansX) {
        transform.setRotationRadiansX(rotationRadiansX);
    }

    @Override
    public void setRotationRadiansY(float rotationRadiansY) {
        transform.setRotationRadiansY(rotationRadiansY);
    }

    @Override
    public void setRotationRadiansZ(float rotationRadiansZ) {
        transform.setRotationRadiansZ(rotationRadiansZ);
    }

    @Override
    public void setRotationRadians(float radiansX, float radiansY, float radiansZ) {
        transform.setRotationRadians(radiansX, radiansY, radiansZ);
    }

    @Override
    public void setRotationRadians(Vector3f rotationRadians) {
        transform.setRotationRadians(rotationRadians);
    }

    @Override
    public void setRotationAngleX(float angleX) {
        transform.setRotationAngleX(angleX);
    }

    @Override
    public void setRotationAngleY(float angleY) {
        transform.setRotationAngleY(angleY);
    }

    @Override
    public void setRotationAngleZ(float angleZ) {
        transform.setRotationAngleZ(angleZ);
    }

    @Override
    public void setRotationAngle(float angleX, float angleY, float angleZ) {
        transform.setRotationAngle(angleX, angleY, angleZ);
    }

    @Override
    public void setRotationAngle(Vector3f angle) {
        transform.setRotationAngle(angle);
    }

    @Override
    public float getRotationRadiansX() {
        return transform.getRotationRadiansX();
    }

    @Override
    public float getRotationRadiansY() {
        return transform.getRotationRadiansY();
    }

    @Override
    public float getRotationRadiansZ() {
        return transform.getRotationRadiansZ();
    }

    @Override
    public Vector3f getRotationRadians() {
        return transform.getRotationRadians();
    }

    @Override
    public float getRotationAngleX() {
        return transform.getRotationAngleX();
    }

    @Override
    public float getRotationAngleY() {
        return transform.getRotationAngleY();
    }

    @Override
    public float getRotationAngleZ() {
        return transform.getRotationAngleZ();
    }

    @Override
    public Vector3f getRotationAngle() {
        return transform.getRotationAngle();
    }

    @Override
    public void setScaleX(float x) {
        transform.setScaleX(x);
    }

    @Override
    public void setScaleY(float y) {
        transform.setScaleY(y);
    }

    @Override
    public void setScaleZ(float z) {
        transform.setScaleZ(z);
    }

    @Override
    public void setScale(float scale) {
        transform.setScale(scale);
    }

    @Override
    public void setScale(float scaleX, float scaleY, float scaleZ) {
        transform.setScale(scaleX, scaleY, scaleZ);
    }

    @Override
    public void setScale(Vector3f scale) {
        transform.setScale(scale);
    }

    @Override
    public float getScaleX() {
        return transform.getScaleX();
    }

    @Override
    public float getScaleY() {
        return transform.getScaleY();
    }

    @Override
    public float getScaleZ() {
        return transform.getScaleZ();
    }

    @Override
    public Vector3f getScale() {
        return transform.getScale();
    }
}
