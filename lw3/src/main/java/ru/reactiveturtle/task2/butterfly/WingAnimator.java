package ru.reactiveturtle.task2.butterfly;

public class WingAnimator {
    private Butterfly butterfly;
    private int swingDirection = -1;
    private float startRotationRadians = 0;

    public WingAnimator(Butterfly butterfly) {
        this.butterfly = butterfly;
    }

    private static final float MIN_SWING = (float) -Math.PI / 2.1f;
    private static final float MAX_SWING = (float) 0;

    public void updateRotation(double deltaTime) {
        startRotationRadians += (float) (Math.PI * deltaTime * butterfly.getSwingSpeed()) * swingDirection;
        if (startRotationRadians <= MIN_SWING) {
            swingDirection = 1;
            startRotationRadians = MIN_SWING;
        } else if (startRotationRadians >= MAX_SWING) {
            swingDirection = -1;
            startRotationRadians = 0;
        }
    }

    public void updateFirstWing() {
        butterfly.setRotationRadiansX((float) (-startRotationRadians * Math.sin(butterfly.getRotationRadiansZ())));
        butterfly.setRotationRadiansY((float) (-startRotationRadians * Math.cos(butterfly.getRotationRadiansZ())));
    }

    public void updateSecondWing() {
        butterfly.setRotationRadiansX((float) ((-Math.PI - startRotationRadians) * Math.sin(butterfly.getRotationRadiansZ())));
        butterfly.setRotationRadiansY((float) ((-Math.PI - startRotationRadians) * Math.cos(butterfly.getRotationRadiansZ())));
    }
}
