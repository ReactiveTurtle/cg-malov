package ru.reactiveturtle.task3;

import org.joml.Vector2f;
import org.joml.Vector4f;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

import java.util.Arrays;
import java.util.Random;

public class TetrominoRandomizer {
    private static final Vector4f STROKE_COLOR = new Vector4f(0f, 0f, 0f, 1f);
    private static final Vector4f[] FILL_COLORS = new Vector4f[]{
            new Vector4f(0f, 1f, 1f, 1f),
            new Vector4f(1f, 1f, 0f, 1f),
            new Vector4f(0.5f, 0f, 0.5f, 1f),
            new Vector4f(0f, 1f, 0f, 1f),
            new Vector4f(1f, 0f, 0f, 1f),
            new Vector4f(0f, 0f, 1f, 1f),
            new Vector4f(1f, 0.5f, 0f, 1f),
    };
    private static final Vector2f[][] TETROMINO_BLOCK_ORIGINS = new Vector2f[][]{
            // I
            new Vector2f[]{
                    new Vector2f(0f, 0f),
                    new Vector2f(0f, Block.BLOCK_SIZE),
                    new Vector2f(0f, Block.BLOCK_SIZE * 2f),
                    new Vector2f(0f, Block.BLOCK_SIZE * 3),
            },
            // T
            new Vector2f[]{
                    new Vector2f(-Block.BLOCK_SIZE, 0f),
                    new Vector2f(0f, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE * 2f, Block.BLOCK_SIZE),
            },
            // O
            new Vector2f[]{
                    new Vector2f(0f, 0f),
                    new Vector2f(0f, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE, 0f),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE),
            },
            // L
            new Vector2f[]{
                    new Vector2f(0f, 0f),
                    new Vector2f(0f, Block.BLOCK_SIZE),
                    new Vector2f(0f, Block.BLOCK_SIZE * 2f),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE * 2)
            },
            // J
            new Vector2f[]{
                    new Vector2f(-Block.BLOCK_SIZE, 0f),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE * 2f),
                    new Vector2f(0, Block.BLOCK_SIZE * 2)
            },
            // Z
            new Vector2f[]{
                    new Vector2f(0f, 0f),
                    new Vector2f(-Block.BLOCK_SIZE, 0f),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE * 2, Block.BLOCK_SIZE)
            },
            // S
            new Vector2f[]{
                    new Vector2f(0f, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE, Block.BLOCK_SIZE),
                    new Vector2f(-Block.BLOCK_SIZE, 0f),
                    new Vector2f(-Block.BLOCK_SIZE * 2, 0)
            }
    };

    private static final Vector2f[] TETROMINO_SIZES = new Vector2f[]{
            new Vector2f(Block.BLOCK_SIZE, Block.BLOCK_SIZE * 4f),
            new Vector2f(Block.BLOCK_SIZE * 3f, Block.BLOCK_SIZE * 2f),
            new Vector2f(Block.BLOCK_SIZE * 2f, Block.BLOCK_SIZE * 2f),
            new Vector2f(Block.BLOCK_SIZE * 2f, Block.BLOCK_SIZE * 3f),
            new Vector2f(Block.BLOCK_SIZE * 2f, Block.BLOCK_SIZE * 3f),
            new Vector2f(Block.BLOCK_SIZE * 3f, Block.BLOCK_SIZE * 2f),
            new Vector2f(Block.BLOCK_SIZE * 3f, Block.BLOCK_SIZE * 2f)
    };

    private static final float[] TETROMINO_CHANCES = new float[]{
            0.2f,
            0.2f,
            0.2f,
            0.1f,
            0.1f,
            0.1f,
            0.1f,
    };

    private final Random tetraminoRandom = new Random();
    private final Random colorRandom = new Random();

    public Tetromino generate(ShapeBatch shapeBatch) {
        Block block = new Block(nextFillColor(), new Vector4f(STROKE_COLOR));
        int tetrominoIndex = randomTetrominoIndex();
        return new Tetromino(
                shapeBatch,
                block,
                vector2fArrayCopy(TETROMINO_BLOCK_ORIGINS[tetrominoIndex]),
                new Vector2f(TETROMINO_SIZES[tetrominoIndex]));
    }

    private Vector4f nextFillColor() {
        return FILL_COLORS[Math.round(colorRandom.nextFloat() * (FILL_COLORS.length - 1))];
    }

    private int randomTetrominoIndex() {
        float value = tetraminoRandom.nextFloat();
        float sumChance = 0;
        for (int i = 0; i < TETROMINO_CHANCES.length; i++) {
            sumChance += TETROMINO_CHANCES[i];
            if (value < sumChance) {
                return i;
            }
        }
        throw new RuntimeException("Chances must be greater than 0 and chance count can't be zero");
    }

    private Vector2f[] vector2fArrayCopy(Vector2f[] array) {
        return Arrays.stream(array).map(Vector2f::new).toArray(Vector2f[]::new);
    }
}
