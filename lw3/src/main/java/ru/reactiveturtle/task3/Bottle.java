package ru.reactiveturtle.task3;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Bottle implements Drawable, Disposable {
    public static final int BORDER_WIDTH = 10;
    public static final int BORDER_HEIGHT = 20;

    private final ShapeBatch shapeBatch;

    private BorderBlock borderBlock;
    private Vector2f[] borderBlockOrigins;

    public Bottle(ShapeBatch shapeBatch) {
        this.shapeBatch = shapeBatch;
        initBorder();
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        for (Vector2f vector2f : borderBlockOrigins) {
            borderBlock.setOrigin(vector2f);
            shapeBatch.draw(borderBlock);
        }
        shapeBatch.end();
    }

    @Override
    public void dispose() {
        borderBlock.dispose();
    }

    public float getScaledWidth() {
        return borderBlock.getScaledSize() * (BORDER_WIDTH + 4);
    }

    public float getScaledHeight() {
        return borderBlock.getScaledSize() * (BORDER_HEIGHT + 2);
    }

    public boolean isTetrominoNearLeftBorder(Tetromino tetromino) {
        float x = tetromino.getX();
        float leftBorder = (-(BORDER_WIDTH - 2) / 2f * Block.BLOCK_SIZE) * borderBlock.getScaleX();
        return leftBorder == x;
    }

    public boolean isTetrominoNearRightBorder(Tetromino tetromino) {
        float x = tetromino.getX();
        float leftBorder = (-(BORDER_WIDTH - 2) / 2f * Block.BLOCK_SIZE) * borderBlock.getScaleX();
        float rightBorder = leftBorder + (BORDER_WIDTH * Block.BLOCK_SIZE) * borderBlock.getScaleX()
                - tetromino.getScaledWidth();
        return rightBorder == x;
    }

    public boolean isTetrominoInBottomBorder(Tetromino tetromino) {
        float y = tetromino.getY();
        float bottomBorder = -(BORDER_HEIGHT + 2) / 2f * Block.BLOCK_SIZE * borderBlock.getScaleY()
                + tetromino.getScaledHeight();
        return bottomBorder == y;
    }

    private void initBorder() {
        borderBlock = new BorderBlock();

        borderBlockOrigins = new Vector2f[(BORDER_HEIGHT + 1) * 2 + BORDER_WIDTH];
        float xBias = getXBias();
        for (int i = 0, size = BORDER_HEIGHT + 1; i < size; i++) {
            borderBlockOrigins[i] = new Vector2f(
                    Block.BLOCK_SIZE * (BORDER_WIDTH + 1) / 2f - xBias,
                    -Block.BLOCK_SIZE * (BORDER_HEIGHT / 2f - i));
            borderBlockOrigins[BORDER_HEIGHT + 1 + i] = new Vector2f(
                    (borderBlockOrigins[i].x - (BORDER_WIDTH + 1) * Block.BLOCK_SIZE),
                    borderBlockOrigins[i].y);
        }

        float y = BORDER_HEIGHT / 2f * Block.BLOCK_SIZE;
        int startIndex = (BORDER_HEIGHT + 1) * 2;
        for (int i = 0; i < BORDER_WIDTH; i++) {
            borderBlockOrigins[startIndex + i] = new Vector2f(
                    Block.BLOCK_SIZE * ((BORDER_WIDTH + 1) / 2f - 1 - i) - xBias,
                    y);
        }
    }

    private float getXBias() {
        return Block.BLOCK_SIZE / 2f * Math.abs(BORDER_WIDTH % 2f - 1);
    }
}
