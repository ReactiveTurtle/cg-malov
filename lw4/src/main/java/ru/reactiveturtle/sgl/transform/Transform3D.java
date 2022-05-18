package ru.reactiveturtle.sgl.transform;

import org.joml.Vector3f;

public class Transform3D implements ITransform3D {
    /**
     * Координаты источника объекта (не позиция)
     * относительно которых происходит поворот и масштабирование
     */
    private final Vector3f origin = new Vector3f();
    /**
     * Глобальное положение объекта в 3D
     */
    private final Vector3f position = new Vector3f();
    /**
     * Показывает поворот объекта в радианах
     */
    private final Vector3f rotationRadians = new Vector3f();
    /**
     * Масштабирование. По умолчанию всегда 1
     */
    private final Vector3f scale = new Vector3f(1f);

    @Override
    public void setOriginX(float x) {
        origin.x = x;
    }

    @Override
    public void setOriginY(float y) {
        origin.y = y;
    }

    @Override
    public void setOriginZ(float z) {
        origin.z = z;
    }

    @Override
    public void setOrigin(float x, float y, float z) {
        setOriginX(x);
        setOriginY(y);
        setOriginZ(z);
    }

    @Override
    public void setOrigin(Vector3f origin) {
        setOrigin(origin.x, origin.y, origin.z);
    }

    @Override
    public float getOriginX() {
        return origin.x;
    }

    @Override
    public float getOriginY() {
        return origin.y;
    }

    @Override
    public float getOriginZ() {
        return origin.z;
    }

    @Override
    public Vector3f getOrigin() {
        return new Vector3f(origin);
    }

    @Override
    public void setX(float x) {
        position.x = x;
    }

    @Override
    public void setY(float y) {
        position.y = y;
    }

    @Override
    public void setZ(float z) {
        position.z = z;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    @Override
    public void setPosition(Vector3f position) {
        setPosition(position.x, position.y, position.z);
    }

    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public float getZ() {
        return position.z;
    }

    @Override
    public Vector3f getPosition() {
        return new Vector3f(position);
    }

    @Override
    public void setRotationRadiansX(float rotationRadiansX) {
        rotationRadians.x = rotationRadiansX;
    }

    @Override
    public void setRotationRadiansY(float rotationRadiansY) {
        rotationRadians.y = rotationRadiansY;
    }

    @Override
    public void setRotationRadiansZ(float rotationRadiansZ) {
        rotationRadians.z = rotationRadiansZ;
    }

    @Override
    public void setRotationRadians(float radiansX, float radiansY, float radiansZ) {
        setRotationRadiansX(radiansX);
        setRotationRadiansY(radiansY);
        setRotationRadiansZ(radiansZ);
    }

    @Override
    public void setRotationRadians(Vector3f rotationRadians) {
        setRotationRadians(rotationRadians.x, rotationRadians.y, rotationRadians.z);
    }

    @Override
    public void setRotationAngleX(float angleX) {
        setRotationRadiansX((float) Math.toRadians(angleX));
    }

    @Override
    public void setRotationAngleY(float angleY) {
        setRotationRadiansY((float) Math.toRadians(angleY));
    }

    @Override
    public void setRotationAngleZ(float angleZ) {
        setRotationRadiansZ((float) Math.toRadians(angleZ));
    }

    @Override
    public void setRotationAngle(float angleX, float angleY, float angleZ) {
        setRotationAngleX(angleX);
        setRotationAngleY(angleY);
        setRotationAngleZ(angleZ);
    }

    @Override
    public void setRotationAngle(Vector3f angle) {
        setRotationAngle(angle.x, angle.y, angle.z);
    }

    @Override
    public float getRotationRadiansX() {
        return rotationRadians.x;
    }

    @Override
    public float getRotationRadiansY() {
        return rotationRadians.y;
    }

    @Override
    public float getRotationRadiansZ() {
        return rotationRadians.z;
    }

    @Override
    public Vector3f getRotationRadians() {
        return new Vector3f(rotationRadians);
    }

    @Override
    public float getRotationAngleX() {
        return (float) Math.toDegrees(rotationRadians.x);
    }

    @Override
    public float getRotationAngleY() {
        return (float) Math.toDegrees(rotationRadians.y);
    }

    @Override
    public float getRotationAngleZ() {
        return (float) Math.toDegrees(rotationRadians.z);
    }

    @Override
    public Vector3f getRotationAngle() {
        return new Vector3f(getRotationAngleX(), getRotationAngleY(), getRotationAngleZ());
    }

    @Override
    public void setScaleX(float x) {
        scale.x = x;
    }

    @Override
    public void setScaleY(float y) {
        scale.y = y;
    }

    @Override
    public void setScaleZ(float z) {
        scale.z = z;
    }

    @Override
    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
        setScaleZ(scale);
    }

    @Override
    public void setScale(float scaleX, float scaleY, float scaleZ) {
        setScaleX(scaleX);
        setScaleY(scaleY);
        setScaleZ(scaleZ);
    }

    @Override
    public void setScale(Vector3f scale) {
        setScale(scale.x, scale.y, scale.z);
    }

    @Override
    public float getScaleX() {
        return scale.x;
    }

    @Override
    public float getScaleY() {
        return scale.y;
    }

    @Override
    public float getScaleZ() {
        return scale.z;
    }

    @Override
    public Vector3f getScale() {
        return new Vector3f(scale);
    }
}
