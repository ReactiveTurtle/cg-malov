package ru.reactiveturtle.task1;

import ru.reactiveturtle.sgl.Transform2D;

public class JumpAnimator {
    private Transform2D transform2D;

    private float startVelocity;
    private float acceleration;
    private double timeLeft = 0;

    public JumpAnimator(Transform2D transform2D,
                        float startVelocity,
                        float acceleration) {
        this.transform2D = transform2D;
        this.startVelocity = startVelocity;
        this.acceleration = acceleration;
    }

    public void update(double deltaTime) {
        timeLeft += deltaTime;
        float currentY = (float) ((startVelocity + acceleration * timeLeft / 2) * timeLeft);
        if (currentY <= 0) {
            timeLeft = 0;
        }
        transform2D.setY(currentY);
    }
}
