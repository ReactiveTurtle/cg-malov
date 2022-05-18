package ru.reactiveturtle.task2.flower;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.task2.Randomizer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class FlowerMap implements Drawable, Disposable {
    private Random random = Randomizer.getInstance();
    private FlowerBatch flowerBatch;
    private Flower[] flowers;
    private int[] flowerTypeIndices;
    private Vector2f[] flowerPositions;

    public FlowerMap(RenderContext renderContext) {
        flowerBatch = new FlowerBatch(renderContext);
        flowers = new Flower[]{
                new Chamomile(),
                new Chicory(),
                new Tulip()
        };
        plantFlowers(
                renderContext.getWindow().getSize(),
                renderContext.getWindow().getWidth(),
                renderContext.getWindow().getHeight() / 4f);
    }

    @Override
    public void draw() {
        flowerBatch.begin();
        for (int i = 0; i < flowerTypeIndices.length; i++) {
            flowers[flowerTypeIndices[i]].setPosition(flowerPositions[i]);
            flowerBatch.draw(flowers[flowerTypeIndices[i]]);
        }
        flowerBatch.end();
    }

    @Override
    public void dispose() {

    }

    public int getRandomFlowerIndex() {
        return Randomizer.getInstance().nextInt(flowerPositions.length - 1);
    }

    public Vector2f getFlowerPosition(int index) {
        return new Vector2f(flowerPositions[index]).add(0, flowers[flowerTypeIndices[index]].getCenter());
    }

    private void plantFlowers(Vector2f windowSize, float width, float height) {
        flowerTypeIndices = new int[Randomizer.random(40, 60)];
        flowerPositions = new Vector2f[flowerTypeIndices.length];
        for (int i = 0; i < flowerTypeIndices.length; i++) {
            flowerTypeIndices[i] = Math.round(random.nextFloat() * 2f);
            putFlower(i, windowSize, width, height);
        }
        Arrays.sort(flowerPositions, (o1, o2) -> (int) Math.signum(o2.y - o1.y));
    }

    private void putFlower(int index, Vector2f windowSize, float width, float height) {
        boolean foundPlace = false;
        float leftBias = width * 0.1f;
        Vector2f position = null;
        Flower currentFlower = flowers[flowerTypeIndices[index]];
        while (!foundPlace) {
            position = new Vector2f(
                    leftBias + random.nextFloat() * width * 0.8f - windowSize.x / 2f,
                    height * 0.1f + random.nextFloat() * height * 0.8f - windowSize.y / 2f);
            boolean isAllNotIntersects = true;
            for (int i = 0; i < index && isAllNotIntersects; i++) {
                Flower flower = flowers[flowerTypeIndices[i]];
                Vector2f flowerPosition = flowerPositions[i];
                isAllNotIntersects = isNotIntersectsFlower(
                        new Vector2f(position)
                                .add(-currentFlower.getScaledWidth() / 2f * 2f, currentFlower.getScaledHeight() * -0.1f), flower, flowerPosition) &&
                        isNotIntersectsFlower(new Vector2f(position)
                                .add(currentFlower.getScaledWidth() / 2f * 2f, currentFlower.getScaledHeight() * -0.1f), flower, flowerPosition) &&
                        isNotIntersectsFlower(new Vector2f(position)
                                .add(currentFlower.getScaledWidth() / 2f * 2f, currentFlower.getScaledHeight() * 1.1f), flower, flowerPosition) &&
                        isNotIntersectsFlower(new Vector2f(position)
                                .add(-currentFlower.getScaledWidth() / 2f * 2f, currentFlower.getScaledHeight() * 1.1f), flower, flowerPosition);
            }
            foundPlace = isAllNotIntersects;
        }
        flowerPositions[index] = position;
    }

    private boolean isNotIntersectsFlower(Vector2f point, Flower flower, Vector2f flowerPosition) {
        return point.x <= (flowerPosition.x - flower.getScaledWidth() / 2f)
                || point.x >= flowerPosition.x + flower.getScaledWidth() / 2f
                || point.y <= flowerPosition.y
                || point.y >= flowerPosition.y + flower.getScaledHeight();
    }
}
