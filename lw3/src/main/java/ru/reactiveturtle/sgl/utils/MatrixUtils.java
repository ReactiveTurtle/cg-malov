package ru.reactiveturtle.sgl.utils;

import org.joml.Matrix4f;
import ru.reactiveturtle.sgl.ITransform2D;

public final class MatrixUtils {
    public static Matrix4f getModelMatrix(ITransform2D transform2D) {
        return new Matrix4f()
                .translate(transform2D.getX(), transform2D.getY(), 0)
                .rotateYXZ(transform2D.getRotationRadiansY(), transform2D.getRotationRadiansX(), transform2D.getRotationRadiansZ())
                .scale(transform2D.getScaleX(), transform2D.getScaleY(), 0)
                .translate(-transform2D.getOriginX(), -transform2D.getOriginY(), 0);
    }
}
