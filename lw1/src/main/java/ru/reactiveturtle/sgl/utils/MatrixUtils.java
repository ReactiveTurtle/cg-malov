package ru.reactiveturtle.sgl.utils;

import org.joml.Matrix4f;
import ru.reactiveturtle.sgl.shape.ShapeImpl;
import ru.reactiveturtle.sgl.shape.base.BaseShapeImpl;
import ru.reactiveturtle.sgl.shape.gradient.GradientShape;
import ru.reactiveturtle.sgl.shape.gradient.GradientShapeImpl;

public final class MatrixUtils {
    public static Matrix4f getModelMatrix(BaseShapeImpl shape) {
        return new Matrix4f()
                .translate(shape.getX(), shape.getY(), 0)
                .rotateZ(shape.getRotationRadians())
                .scale(shape.getScaleX(), shape.getScaleY(), 0)
                .translate(-shape.getOriginX(), -shape.getOriginY(), 0);
    }
}
