package ru.reactiveturtle.task2.butterfly;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.shape.ShapeBatch;
import ru.reactiveturtle.task2.Randomizer;
import ru.reactiveturtle.task2.flower.FlowerMap;

public class ButterflyMap implements Drawable, Disposable {
    private RenderContext renderContext;

    private FlowerMap flowerMap;

    private Butterfly[] butterflies;
    private Integer[] selectedFlowerIndices;
    private Vector2f[] selectedSkyPositions;
    private ShapeBatch shapeBatch;

    public ButterflyMap(RenderContext renderContext, ShapeBatch shapeBatch) {
        this.renderContext = renderContext;
        butterflies = new Butterfly[]{
                new Butterfly(),
                new Butterfly(),
                new Butterfly(),
                new Butterfly()
        };
        this.shapeBatch = shapeBatch;
        generateButterflies();

        flowerMap = new FlowerMap(renderContext);
    }

    public void update(double deltaTime) {
        for (int i = 0; i < butterflies.length; i++) {
            Butterfly butterfly = butterflies[i];
            butterfly.update(deltaTime);
            if (selectedSkyPositions[i] != null) {
                Vector2f skyPosition = selectedSkyPositions[i];
                Vector2f flyDirection = new Vector2f(skyPosition).sub(butterfly.getPosition());
                float distance = flyDirection.length();
                if (distance == 0) {
                    selectedSkyPositions[i] = null;
                    selectedFlowerIndices[i] = flowerMap.getRandomFlowerIndex();
                } else {
                    Vector2f newDistanceVector = new Vector2f(flyDirection)
                            .normalize((float) Math.max(0, flyDirection.length() - butterfly.getMoveSpeed() * deltaTime));

                    float rotationZ = (float) Math.atan2(flyDirection.y, flyDirection.x);
                    butterfly.setRotationRadiansZ((float) (Math.round((float) (rotationZ / (Math.PI / 2f)) + 3) * Math.PI / 2f));

                    butterfly.setPosition(new Vector2f(skyPosition).sub(newDistanceVector));
                }
            } else if (selectedFlowerIndices[i] != null) {
                Vector2f flowerPosition = flowerMap.getFlowerPosition(selectedFlowerIndices[i]);
                Vector2f flyDirection = new Vector2f(flowerPosition).sub(butterfly.getPosition());
                float distance = flyDirection.length();
                if (distance == 0) {
                    selectedFlowerIndices[i] = null;
                    selectedSkyPositions[i] = getRandomSkyPosition();
                } else {
                    Vector2f newDistanceVector = new Vector2f(flyDirection)
                            .normalize((float) Math.max(0, flyDirection.length() - butterfly.getMoveSpeed() * deltaTime));

                    float rotationZ = (float) Math.atan2(flyDirection.y, flyDirection.x);
                    butterfly.setRotationRadiansZ((float) (Math.round((float) (rotationZ / (Math.PI / 2f)) + 3) * Math.PI / 2f));

                    butterfly.setPosition(new Vector2f(flowerPosition).sub(newDistanceVector));
                }
            } else {
                boolean isSelect = Randomizer.getInstance().nextFloat() >= 0.9f;
                if (isSelect) {
                    boolean isSelectFlower = Randomizer.getInstance().nextBoolean();
                    if (isSelectFlower) {
                        selectedFlowerIndices[i] = flowerMap.getRandomFlowerIndex();
                    } else {
                        selectedSkyPositions[i] = getRandomSkyPosition();
                    }
                }
            }
        }
    }

    @Override
    public void draw() {
        flowerMap.draw();
        shapeBatch.begin();
        for (Butterfly butterfly : butterflies) {
            shapeBatch.draw(butterfly);
        }
        shapeBatch.end();
    }

    @Override
    public void dispose() {

    }

    private void generateButterflies() {
        butterflies = new Butterfly[Randomizer.random(20, 40)];
        selectedFlowerIndices = new Integer[butterflies.length];
        selectedSkyPositions = new Vector2f[butterflies.length];
        for (int i = 0; i < butterflies.length; i++) {
            butterflies[i] = new Butterfly();
            Vector2f position = getRandomSkyPosition();
            butterflies[i].setPosition(position);
        }
    }

    private Vector2f getRandomSkyPosition() {
        return new Vector2f(
                Randomizer.random(renderContext.getWindow().getWidth() * -0.45f, renderContext.getWindow().getWidth() * 0.45f),
                Randomizer.random(renderContext.getWindow().getHeight() * -0.2f, renderContext.getWindow().getHeight() * 0.4f));
    }
}
