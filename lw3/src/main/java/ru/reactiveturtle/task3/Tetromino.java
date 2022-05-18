package ru.reactiveturtle.task3;

import org.joml.Vector2f;
import org.joml.Vector3f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class Tetromino implements Drawable, Disposable {
    private final ShapeBatch shapeBatch;
    private final Block block;
    private final Vector2f[] blockOrigins;
    private Vector2f tetrominoSize;

    public Tetromino(ShapeBatch shapeBatch, Block block, Vector2f[] blockOrigins, Vector2f tetrominoSize) {
        this.shapeBatch = shapeBatch;
        this.block = block;
        this.blockOrigins = blockOrigins;
        this.tetrominoSize = tetrominoSize;
    }

    @Override
    public void draw() {
        shapeBatch.begin();
        for (Vector2f vector2f : blockOrigins) {
            block.setOrigin(vector2f);
            shapeBatch.draw(block);
        }
        shapeBatch.end();
    }

    public float getScaledWidth() {
        return block.getScaleX() * tetrominoSize.x;
    }

    public float getScaledHeight() {
        return block.getScaleY() * tetrominoSize.y;
    }

    public Block getBlock() {
        return block;
    }

    public Block[] getBlocks() {
        Block[] blocks = new Block[blockOrigins.length];
        for (int i = 0; i < blockOrigins.length; i++) {
            Vector2f origin = blockOrigins[i];
            Block block = this.block.copy();
            block.setPosition(this.block.getPosition());
            block.setPosition(block.getX() - origin.x * block.getScaleX(),
                    block.getY() - origin.y * block.getScaleY());
            blocks[i] = block;
        }
        return blocks;
    }

    public Vector2f[] getBlockOrigins() {
        return blockOrigins;
    }

    @Override
    public void dispose() {
        block.dispose();
    }

    public void rotate() {
        tetrominoSize = new Vector2f(tetrominoSize.y, tetrominoSize.x);
        float maxX = 0, minY = 0;
        for (int i = 0; i < blockOrigins.length; i++) {
            Vector3f newOrigin = new Vector3f(blockOrigins[i].x, blockOrigins[i].y, 0);
            newOrigin.rotateZ((float) (-Math.PI / 2f));
            blockOrigins[i].x = newOrigin.x;
            blockOrigins[i].y = newOrigin.y;
            if (i == 0) {
                maxX = newOrigin.x;
                minY = newOrigin.y;
            } else {
                if (newOrigin.x > maxX) {
                    maxX = newOrigin.x;
                }
                if (newOrigin.y < minY) {
                    minY = newOrigin.y;
                }
            }
        }
        for (Vector2f blockOrigin : blockOrigins) {
            blockOrigin.x -= maxX;
            blockOrigin.y -= minY;
        }
    }

    public void moveLeft() {
        block.setX(block.getX() - block.getScaledSize());
    }

    public void moveRight() {
        block.setX(block.getX() + block.getScaledSize());
    }

    public void moveDown() {
        block.setY(block.getY() - block.getScaledSize());
    }

    public void setPosition(float x, float y) {
        block.setPosition(x, y);
    }

    public void setPosition(Vector2f position) {
        block.setPosition(position);
    }

    public float getX() {
        return block.getX();
    }

    public float getY() {
        return block.getY();
    }

    public Vector2f getPosition() {
        return block.getPosition();
    }
}
