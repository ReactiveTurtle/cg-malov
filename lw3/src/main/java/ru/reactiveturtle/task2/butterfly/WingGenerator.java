package ru.reactiveturtle.task2.butterfly;

import org.joml.Vector4f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.model.IMeshDrawer;
import ru.reactiveturtle.sgl.model.Mesh;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.task2.Randomizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WingGenerator {
    private static final Random RANDOM = Randomizer.getInstance();

    public static WingParams generateWing(Model2d model2d, IMeshDrawer meshDrawer) {
        boolean isBigWing = RANDOM.nextBoolean();
        float bottomToTopWingWidthFactor = isBigWing ? 0.85f : 0.75f;
        float topWingWidth = RANDOM.nextFloat() * 0.7f + 0.5f;
        float bottomWingWidth = topWingWidth * bottomToTopWingWidthFactor;

        float bottomToTopWingHeightFactor = isBigWing ? 0.75f : 0.6f;
        float wingHeightFactor = isBigWing ? 0.6f : 0.5f;
        float topWingHeight = RANDOM.nextFloat() * 0.5f + wingHeightFactor;
        float bottomWingHeight = topWingHeight * bottomToTopWingHeightFactor;

        int wingCornerPointCount = 14;
        float[] bezierStartVertices = new float[wingCornerPointCount];
        bezierStartVertices[0] = 0f;
        bezierStartVertices[1] = 0f;
        bezierStartVertices[2] = topWingWidth * 0.1f;
        bezierStartVertices[3] = topWingHeight * 0.8f;
        bezierStartVertices[4] = topWingWidth;
        bezierStartVertices[5] = topWingHeight;
        bezierStartVertices[6] = topWingWidth * 0.9f;
        bezierStartVertices[7] = topWingHeight * 0.4f;

        bezierStartVertices[8] = bottomWingWidth;
        bezierStartVertices[9] = topWingHeight * 0.4f - (isBigWing ? 0.15f : 0.1f);

        bezierStartVertices[10] = bottomWingWidth * (isBigWing ? 0.7f : 0.6f);
        bezierStartVertices[11] = -bottomWingHeight * (isBigWing ? 0.7f : 0.6f);

        bezierStartVertices[12] = bottomWingWidth * 0.05f;
        bezierStartVertices[13] = -bottomWingHeight;

        int wingCount = 2;
        int wingCornerCount = 4;
        int bezierIterationCount = 18;
        int bezierWingTriangleCount = (bezierIterationCount + 2) * wingCornerCount - 1;
        int indexCount = bezierWingTriangleCount * wingCount * 3;
        Mesh wing = model2d.createMesh(indexCount, meshDrawer);

        float[] bezierPoints = new float[3 * wingCornerCount * 2 * wingCount];
        fillBezierPoints(bezierPoints, bezierStartVertices, 0, 6, 0, 2, 0.8f, 0.8f);
        fillBezierPoints(bezierPoints, bezierStartVertices, 6, 0, 2, 4, 0.85f, 0.9f);
        fillBezierPoints(bezierPoints, bezierStartVertices, 12, 2, 4, 6, 0.75f, 0.75f);
        fillBezierPoints(bezierPoints, bezierStartVertices, 18, 4, 6, 0, 0.8f, 0.8f);

        fillBezierPoints(bezierPoints, bezierStartVertices, 24, 12, 0, 8, 0.95f, 0.95f);
        fillBezierPoints(bezierPoints, bezierStartVertices, 30, 0, 8, 10, 0.85f, 0.8f);
        fillBezierPoints(bezierPoints, bezierStartVertices, 36, 8, 10, 12, 0.75f, 0.8f);
        fillBezierPoints(bezierPoints, bezierStartVertices, 42, 10, 12, 0, 0.8f, 0.85f);

        float[] wingVertices = new float[(bezierIterationCount + 2) * wingCornerCount * wingCount * 2];
        for (int i = 0; i < wingCount; i++) {
            int wingStartIndex = (bezierIterationCount + 2) * wingCornerCount * i * 2;
            for (int j = 0; j < wingCornerCount; j++) {
                int cornerStartIndex = wingStartIndex + (bezierIterationCount + 2) * 2 * j;
                int bezierPointsStartIndex = i * 2 * 3 * wingCornerCount + j * 2 * 3;
                wingVertices[cornerStartIndex] = bezierPoints[bezierPointsStartIndex];
                wingVertices[cornerStartIndex + 1] = bezierPoints[bezierPointsStartIndex + 1];
                wingVertices[cornerStartIndex + (bezierIterationCount + 1) * 2] = bezierPoints[bezierPointsStartIndex + 4];
                wingVertices[cornerStartIndex + (bezierIterationCount + 1) * 2 + 1] = bezierPoints[bezierPointsStartIndex + 5];
                for (int k = 0; k < bezierIterationCount; k++) {
                    int bezierVerticesStartIndex = cornerStartIndex + (k + 1) * 2;
                    float t = (k + 1) / (float) (bezierIterationCount + 2);
                    wingVertices[bezierVerticesStartIndex] = cubicBezier(
                            bezierPoints[bezierPointsStartIndex],
                            bezierPoints[bezierPointsStartIndex + 2],
                            bezierPoints[bezierPointsStartIndex + 4],
                            t);
                    wingVertices[bezierVerticesStartIndex + 1] = cubicBezier(
                            bezierPoints[bezierPointsStartIndex + 1],
                            bezierPoints[bezierPointsStartIndex + 3],
                            bezierPoints[bezierPointsStartIndex + 5],
                            t);
                }
            }
        }

        int[] indices = new int[bezierWingTriangleCount * wingCount * 3];
        for (int i = 0; i < bezierWingTriangleCount; i++) {
            int startIndex = i * 3;
            indices[startIndex] = 0;
            indices[startIndex + 1] = i + 1;
            indices[startIndex + 2] = i + 2;
        }

        int wingStartIndex = (bezierIterationCount + 2) * wingCornerCount;
        int wingIndexStart = bezierWingTriangleCount * 3;
        for (int i = 0; i < bezierWingTriangleCount; i++) {
            int startIndex = wingIndexStart + i * 3;
            indices[startIndex] = wingStartIndex;
            indices[startIndex + 1] = wingStartIndex + i + 1;
            indices[startIndex + 2] = wingStartIndex + i + 2;
        }

        int vertexBufferId = wing.createBuffer();
        wing.storeArrayBuffer(vertexBufferId, wingVertices, 0, 2);

        int indexBufferId = wing.createBuffer();
        wing.storeElementArrayBuffer(indexBufferId, indices);

        float firstColor = (float) (Math.random() * 0.5f + 0.5f);
        float secondColor = (float) (Math.random() * 0.2f + 0.8f);
        List<Integer> positions = new ArrayList<>(Arrays.asList(0, 1, 2));
        int firstColorPosition = positions.remove(Math.round(RANDOM.nextFloat() * (positions.size() - 1)));
        int secondColorPosition = positions.remove(Math.round(RANDOM.nextFloat() * (positions.size() - 1)));

        float[] color = new float[3];
        color[firstColorPosition] = firstColor;
        color[secondColorPosition] = secondColor;
        return new WingParams(
                wing,
                new Vector4f(new Color(
                        color[0],
                        color[1],
                        color[2],
                        1f).getComponentsAsVector()),
                bottomWingHeight + topWingHeight);
    }

    private static void fillBezierPoints(float[] bezierPoints,
                                         float[] vertices,
                                         int bezierPointsStartIndex,
                                         int firstVertexIndex,
                                         int secondVertexIndex,
                                         int thirdVertexIndex,
                                         float firstPointFactor,
                                         float thirdPointFactor) {
        bezierPoints[bezierPointsStartIndex] = vertices[firstVertexIndex] + (vertices[secondVertexIndex] - vertices[firstVertexIndex]) * firstPointFactor;
        bezierPoints[bezierPointsStartIndex + 1] = vertices[firstVertexIndex + 1] + (vertices[secondVertexIndex + 1] - vertices[firstVertexIndex + 1]) * firstPointFactor;
        bezierPoints[bezierPointsStartIndex + 2] = vertices[secondVertexIndex];
        bezierPoints[bezierPointsStartIndex + 3] = vertices[secondVertexIndex + 1];
        bezierPoints[bezierPointsStartIndex + 4] = vertices[thirdVertexIndex] + (vertices[secondVertexIndex] - vertices[thirdVertexIndex]) * thirdPointFactor;
        bezierPoints[bezierPointsStartIndex + 5] = vertices[thirdVertexIndex + 1] + (vertices[secondVertexIndex + 1] - vertices[thirdVertexIndex + 1]) * thirdPointFactor;
    }

    private static float cubicBezier(float first, float second, float third, float t) {
        float sub = 1 - t;
        return first * (sub * sub) + second * (2 * t * sub) + third * (t * t);
    }
}
