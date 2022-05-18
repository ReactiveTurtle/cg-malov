package ru.reactiveturtle.sgl.transform;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform2D implements ITransform2D {
    /**
     * Координаты источника объекта (не позиция)
     * относительно которых происходит поворот и масштабирование
     */
    private final Vector2f origin = new Vector2f();
    /**
     * Глобальное положение объекта в 2D
     */
    private final Vector2f position = new Vector2f();
    /**
     * Показывает поворот объекта в радианах
     */
    private final Vector3f rotationRadians = new Vector3f();
    /**
     * Масштабирование. По умолчанию всегда 1
     */
    private final Vector2f scale = new Vector2f(1f);

    /**
     * Устанавливает координаты источника объекта (не влияет на положение объекта)
     * относительно глобального положения объекта
     *
     * @param x Координата x
     */
    @Override
    public void setOriginX(float x) {
        this.origin.x = x;
    }

    /**
     * Устанавливает координаты источника объекта (не влияет на положение объекта)
     * относительно глобального положения объекта
     *
     * @param y Координата y
     */
    @Override
    public void setOriginY(float y) {
        this.origin.y = y;
    }

    /**
     * Устанавливает координаты источника объекта (не влияет на положение объекта)
     * относительно глобального положения объекта
     *
     * @param x Координата x
     * @param y Координата y
     */
    @Override
    public void setOrigin(float x, float y) {
        setOriginX(x);
        setOriginY(y);
    }

    /**
     * Устанавливает координаты источника объекта (не влияет на положение объекта)
     * относительно глобального положения объекта
     *
     * @param origin Вектор истоков объекта
     */
    @Override
    public void setOrigin(Vector2f origin) {
        setOriginX(origin.x);
        setOriginY(origin.y);
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
    public Vector2f getOrigin() {
        return new Vector2f(origin);
    }

    /**
     * Устанавливает глобальное положение по оси X для объекта
     *
     * @param x Положение на оси x
     */
    @Override
    public void setX(float x) {
        this.position.x = x;
    }

    /**
     * Устанавливает глобальное положение по оси Y для объекта
     *
     * @param y Положение на оси y
     */
    @Override
    public void setY(float y) {
        this.position.y = y;
    }

    /**
     * Устанавливает глоабальное положение объекта в 2D
     *
     * @param x Положение на оси x
     * @param y Положение на оси y
     */
    @Override
    public void setPosition(float x, float y) {
        setX(x);
        setY(y);
    }

    /**
     * @return Глобальная координата X объекта
     */
    @Override
    public float getX() {
        return position.x;
    }

    /**
     * @return Глобальная координата Y объекта
     */
    @Override
    public float getY() {
        return position.y;
    }

    /**
     * @return Возвращает глобальное положение объекта в 2d
     */
    @Override
    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    /**
     * Устанавливает глоабальное положение объекта в 2D
     *
     * @param position Вектор с координатами x и y
     */
    @Override
    public void setPosition(Vector2f position) {
        setPosition(position.x, position.y);
    }

    /**
     * Устанавливает значение поворота в радианах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param rotationRadiansX Угол поворота по оси X в радианах
     */
    @Override
    public void setRotationRadiansX(float rotationRadiansX) {
        this.rotationRadians.x = rotationRadiansX;
    }

    /**
     * Устанавливает значение поворота в радианах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param rotationRadiansY Угол поворота по оси Y в радианах
     */
    @Override
    public void setRotationRadiansY(float rotationRadiansY) {
        this.rotationRadians.y = rotationRadiansY;
    }

    /**
     * Устанавливает значение поворота в радианах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param rotationRadiansZ Угол поворота по оси Z в радианах
     */
    @Override
    public void setRotationRadiansZ(float rotationRadiansZ) {
        this.rotationRadians.z = rotationRadiansZ;
    }

    /**
     * Устанавливает значение поворота в радианах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param rotationRadians Угол поворота по осям X, Y и Z
     */
    @Override
    public void setRotationRadians(Vector3f rotationRadians) {
        setRotationRadiansX(rotationRadians.x);
        setRotationRadiansY(rotationRadians.y);
        setRotationRadiansZ(rotationRadians.z);
    }

    /**
     * Устанавливает значение поворота в градусах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param angleX Угол поворота в градусах по оси X
     */
    @Override
    public void setRotationAngleX(float angleX) {
        setRotationRadiansX((float) Math.toRadians(angleX));
    }

    /**
     * Устанавливает значение поворота в градусах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param angleY Угол поворота в градусах по оси Y
     */
    @Override
    public void setRotationAngleY(float angleY) {
        setRotationRadiansY((float) Math.toRadians(angleY));
    }

    /**
     * Устанавливает значение поворота в градусах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param angleZ Угол поворота в градусах по оси Z
     */
    @Override
    public void setRotationAngleZ(float angleZ) {
        setRotationRadiansZ((float) Math.toRadians(angleZ));
    }

    /**
     * Устанавливает значение поворота в градусах
     * Поворот происходит относительно origin (истоков) объекта
     *
     * @param angle Угол поворота в градусах по осям X, Y и Z
     */
    @Override
    public void setRotationAngle(Vector3f angle) {
        setRotationAngleX(angle.x);
        setRotationAngleY(angle.y);
        setRotationAngleZ(angle.z);
    }

    /**
     * @return Возвращает угол поворота объекта в радианах по оси X
     */
    @Override
    public float getRotationRadiansX() {
        return rotationRadians.x;
    }

    /**
     * @return Возвращает угол поворота объекта в радианах по оси Y
     */
    @Override
    public float getRotationRadiansY() {
        return rotationRadians.y;
    }

    /**
     * @return Возвращает угол поворота объекта в радианах по оси Z
     */
    @Override
    public float getRotationRadiansZ() {
        return rotationRadians.z;
    }

    /**
     * @return Возвращает углы поворота объекта в радианах по осям X, Y, Z
     */
    @Override
    public Vector3f getRotationRadians() {
        return new Vector3f(rotationRadians);
    }

    /**
     * @return Возвращает угол поворота объекта в градусах по оси X
     */
    @Override
    public float getRotationAngleX() {
        return (float) Math.toDegrees(rotationRadians.x);
    }

    /**
     * @return Возвращает угол поворота объекта в градусах по оси Y
     */
    @Override
    public float getRotationAngleY() {
        return (float) Math.toDegrees(rotationRadians.y);
    }

    /**
     * @return Возвращает угол поворота объекта в градусах по оси Z
     */
    @Override
    public float getRotationAngleZ() {
        return (float) Math.toDegrees(rotationRadians.z);
    }

    /**
     * @return Возвращает углы поворота объекта в градусах по осям X, Y и Z
     */
    @Override
    public Vector3f getRotationAngle() {
        return new Vector3f(
                getRotationAngleX(),
                getRotationAngleY(),
                getRotationAngleZ());
    }

    /**
     * Устанавливает значение масштабирования объекта.
     * Масштабирование происходит относительно origin (истоков) объекта
     *
     * @param scale Масштабирование по осям x и y
     */
    @Override
    public void setScale(float scale) {
        setScaleX(scale);
        setScaleY(scale);
    }

    /**
     * Устанавливает значение масштабирования объекта.
     * Масштабирование происходит относительно origin (истоков) объекта
     *
     * @param scaleX Масштабирование по оси x
     * @param scaleY Масштабирование по оси y
     */
    @Override
    public void setScale(float scaleX, float scaleY) {
        setScaleX(scaleX);
        setScaleY(scaleY);
    }


    /**
     * Устанавливает значение масштабирования объекта.
     * Масштабирование происходит относительно origin (истоков) объекта
     *
     * @param scale Вектор масштабирования по осям x и y
     */
    @Override
    public void setScale(Vector2f scale) {
        setScaleX(scale.x);
        setScaleY(scale.y);
    }

    /**
     * Устанавливает значение масштабирования объекта.
     * Масштабирование происходит относительно origin (истоков) объекта
     *
     * @param x Масштабирование по оси x
     */
    @Override
    public void setScaleX(float x) {
        this.scale.x = x;
    }

    /**
     * Устанавливает значение масштабирования объекта.
     * Масштабирование происходит относительно origin (истоков) объекта
     *
     * @param y Масштабирование по оси y
     */
    @Override
    public void setScaleY(float y) {
        this.scale.y = y;
    }

    /**
     * @return Возвращает вектор масштабирования объекта по осям x и y
     */
    @Override
    public Vector2f getScale() {
        return new Vector2f(scale);
    }

    /**
     * @return Возвращает масштабирование объекта по оси x
     */
    @Override
    public float getScaleX() {
        return scale.x;
    }

    /**
     * @return Возвращает масштабирование объекта по оси y
     */
    @Override
    public float getScaleY() {
        return scale.y;
    }
}
