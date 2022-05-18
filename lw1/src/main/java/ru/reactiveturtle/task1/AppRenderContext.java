package ru.reactiveturtle.task1;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.OrthographicCamera;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.Window;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class AppRenderContext extends RenderContext {
    private ShapeBatch shapeBatch;

    private LetterA firstLetter;
    private Vector2f firstLetterPosition;

    private LetterD secondLetter;

    private LetterA thirdLetter;
    private Vector2f thirdLetterPosition;

    private JumpAnimator jumpAnimatorFirstLetter;
    private JumpAnimator jumpAnimatorSecondLetter;
    private JumpAnimator jumpAnimatorThirdLetter;

    public AppRenderContext(Window window) {
        super(window);
    }

    @Override
    public void start() {
        setCamera(new OrthographicCamera(getWindow().getWidth(), getWindow().getHeight()));
        shapeBatch = new ShapeBatch(this);

        firstLetter = new LetterA(shapeBatch);
        firstLetter.setColor(new Color(1f, 0f, 0f, 1f).value());
        firstLetterPosition = new Vector2f(-16f * 10, 0);
        firstLetter.setPosition(firstLetterPosition);

        secondLetter = new LetterD(shapeBatch);

        thirdLetter = new LetterA(shapeBatch);
        thirdLetter.setColor(new Color(0.5f, 0f, 1f, 1f).value());
        thirdLetterPosition = new Vector2f(16f * 10, 0);
        thirdLetter.setPosition(thirdLetterPosition);

        jumpAnimatorFirstLetter = new JumpAnimator(firstLetter, 350f, -300f);
        jumpAnimatorSecondLetter = new JumpAnimator(secondLetter, 350f, -320f);
        jumpAnimatorThirdLetter = new JumpAnimator(thirdLetter, 350f, -340f);
    }

    @Override
    public void onChangeWindowSize(int width, int height) {
    }

    @Override
    public void render() {
        clearColorBufferBit();
        clearColor(0, 1, 0, 1);

        jumpAnimatorFirstLetter.update(getWindow().getDeltaTime());
        firstLetter.draw();

        jumpAnimatorSecondLetter.update(getWindow().getDeltaTime());
        secondLetter.draw();

        jumpAnimatorThirdLetter.update(getWindow().getDeltaTime());
        thirdLetter.draw();
    }

    @Override
    public void dispose() {

    }
}
