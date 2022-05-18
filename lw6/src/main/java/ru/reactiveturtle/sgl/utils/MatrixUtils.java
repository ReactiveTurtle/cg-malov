package ru.reactiveturtle.sgl.utils;

import org.joml.Matrix4f;
import ru.reactiveturtle.sgl.transform.ITransform2D;
import ru.reactiveturtle.sgl.transform.ITransform3D;

public final class MatrixUtils {
    public static Matrix4f getModelMatrix(ITransform2D transform2D) {
        return new Matrix4f()
                .translate(transform2D.getX(), transform2D.getY(), 0)
                .rotateYXZ(transform2D.getRotationRadiansY(), transform2D.getRotationRadiansX(), transform2D.getRotationRadiansZ())
                .scale(transform2D.getScaleX(), transform2D.getScaleY(), 0)
                .translate(-transform2D.getOriginX(), -transform2D.getOriginY(), 0);
    }

    public static Matrix4f getModelMatrix(ITransform3D transform3D) {
        return new Matrix4f()
                .translate(transform3D.getX(), transform3D.getY(), transform3D.getZ())
                .rotateYXZ(transform3D.getRotationRadiansY(), transform3D.getRotationRadiansX(), transform3D.getRotationRadiansZ())
                .scale(transform3D.getScaleX(), transform3D.getScaleY(), transform3D.getScaleZ())
                .translate(-transform3D.getOriginX(), -transform3D.getOriginY(), -transform3D.getOriginZ());
    }
}
