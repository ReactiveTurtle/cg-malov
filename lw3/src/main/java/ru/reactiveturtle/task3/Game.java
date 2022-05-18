package ru.reactiveturtle.task3;

import org.joml.Vector2f;
import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Drawable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Drawable, Disposable {
    private final RenderContext renderContext;
    private final ShapeBatch shapeBatch;
    private final TetrominoRandomizer tetrominoRandomizer;

    private final Bottle bottle;
    private Tetromino tetromino;
    private Tetromino nextTetromino;
    private final List<Block> blockList = new ArrayList<>();

    private static final double START_TICK_TIME = 1.5f;
    private double timeMod = 0;
    private double tickTime = START_TICK_TIME;

    private boolean isPause = false;
    private boolean isEnd = false;
    private int score = 0;
    private int level = 1;
    private int lines = 0;
    private static final int START_NEED_LINES = 20;
    private int needLines = START_NEED_LINES;

    private Listener listener;

    public Game(RenderContext renderContext, ShapeBatch shapeBatch) {
        this.renderContext = renderContext;
        this.shapeBatch = shapeBatch;
        this.tetrominoRandomizer = new TetrominoRandomizer();
        this.bottle = new Bottle(shapeBatch);
    }

    public void start() {
        newTetromino();
    }

    public void update(double deltaTime) {
        if (isPause || isEnd) {
            return;
        }
        int ticksLeft = (int) Math.ceil((timeMod + deltaTime) / tickTime);
        timeMod = (timeMod + deltaTime) - ticksLeft * tickTime;

        for (int i = 0; i < ticksLeft && !isTetrominoFell(); i++) {
            tetromino.moveDown();
        }
        if (isTetrominoFell()) {
            nextTetromino();
            if (isTetrominoIntersectsBlock(tetromino.getX(), tetromino.getY())) {
                isEnd = true;
                tetromino = null;
                listener.onEnd();
            } else {
                listener.onTetrominoFall();
            }
        }
    }

    @Override
    public void draw() {
        bottle.draw();
        shapeBatch.begin();
        for (Block block : blockList) {
            shapeBatch.draw(block);
        }
        shapeBatch.end();
        if (tetromino != null) {
            tetromino.draw();
        }
        nextTetromino.draw();
    }

    @Override
    public void dispose() {
        bottle.dispose();
        if (tetromino != null) {
            tetromino.dispose();
        }
    }

    public void newGame() {
        isEnd = false;
        isPause = false;
        listener.onNewGame();
        listener.onContinue();
        listener.onScoreUpdated();
        blockList.clear();
        nextTetromino();
        reset();
    }

    public void pause() {
        isPause = !isPause;
        if (isPause) {
            listener.onPause();
        } else {
            listener.onContinue();
        }
    }

    public boolean isPause() {
        return isPause;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void rotateTetromino() {
        if (bottle.isTetrominoNearRightBorder(tetromino)) {
            tetromino.setPosition(tetromino.getX()
                            - (tetromino.getScaledHeight() - tetromino.getScaledWidth())
                            * Math.max(0, Math.signum(tetromino.getScaledHeight() - tetromino.getScaledWidth())),
                    tetromino.getY());
        }
        tetromino.rotate();
        if (isTetrominoIntersectsBlock(tetromino.getX(), tetromino.getY())) {
            tetromino.rotate();
            tetromino.rotate();
            tetromino.rotate();
        }
    }

    public void moveTetrominoLeft() {
        if (!bottle.isTetrominoNearLeftBorder(tetromino) &&
                !isTetrominoIntersectsBlock(tetromino.getX() - tetromino.getBlock().getScaledSize(),
                        tetromino.getY())) {
            tetromino.moveLeft();
        }
    }

    public void moveTetrominoRight() {
        if (!bottle.isTetrominoNearRightBorder(tetromino) &&
                !isTetrominoIntersectsBlock(tetromino.getX() + tetromino.getBlock().getScaledSize(),
                        tetromino.getY())) {
            tetromino.moveRight();
        }
    }

    public void moveTetrominoDown() {
        Vector2f oldPosition = tetromino.getPosition();
        if (!isTetrominoFell()) {
            tetromino.moveDown();
        }
        if (isTetrominoIntersectsBlock(tetromino.getX(), tetromino.getY())) {
            tetromino.setPosition(oldPosition);
        }
    }

    public void setNextTetrominoPosition(float x, float y) {
        nextTetromino.setPosition(x, y);
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getLines() {
        return lines;
    }

    public int getNeedLines() {
        return needLines;
    }

    private void reset() {
        tickTime = START_TICK_TIME;
        timeMod = 0;
        level = 1;
        score = 0;
        lines = 0;
        needLines = START_NEED_LINES;
    }

    private void nextTetromino() {
        if (tetromino != null) {
            if (isTetrominoIntersectsBlock(tetromino.getX(), tetromino.getY())) {
                tetromino.setPosition(tetromino.getX(), tetromino.getY() + tetromino.getBlock().getScaledSize());
            }
            Block[] blocks = tetromino.getBlocks();
            blockList.addAll(Arrays.asList(blocks));
        }
        newTetromino();
        handleFilledRows();
    }

    private void newTetromino() {
        if (tetromino != null) {
            tetromino.dispose();
        }
        tetromino = nextTetromino != null
                ? nextTetromino
                : tetrominoRandomizer.generate(shapeBatch);
        nextTetromino = tetrominoRandomizer.generate(shapeBatch);
        nextTetromino.setPosition(tetromino.getPosition());
        tetromino.setPosition(0, Bottle.BORDER_HEIGHT / 2f * tetromino.getBlock().getScaledSize());
    }

    private boolean isTetrominoIntersectsBlock(float x, float y) {
        boolean isIntersects = false;
        for (int i = 0; i < blockList.size() && !isIntersects; i++) {
            Vector2f[] origins = tetromino.getBlockOrigins();
            for (int j = 0; j < origins.length && !isIntersects; j++) {
                isIntersects = blockList.get(i).getX() == (x - origins[j].x * tetromino.getBlock().getScaleX()) &&
                        blockList.get(i).getY() == (y - origins[j].y * tetromino.getBlock().getScaleY());
            }
        }
        return isIntersects;
    }

    private boolean isTetrominoFell() {
        boolean isTetrominoInBottomBorder = bottle.isTetrominoInBottomBorder(tetromino);
        if (isTetrominoInBottomBorder) {
            tetromino.setPosition(tetromino.getX(), tetromino.getY() + tetromino.getBlock().getScaledSize());
        }
        return isTetrominoInBottomBorder ||
                isTetrominoIntersectsBlock(tetromino.getX(), tetromino.getY());
    }

    private void handleFilledRows() {
        List<Float> yList = new ArrayList<>();
        List<Float> fullYList = new ArrayList<>();
        for (Block block : blockList) {
            if (!yList.contains(block.getY())) {
                yList.add(block.getY());
            }
        }
        yList.sort((o1, o2) -> (int) Math.signum(o2 - o1));
        for (Float y : yList) {
            List<Block> row = blockList.stream().filter(x -> x.getY() == y).collect(Collectors.toList());
            boolean isFullRow = row.size() == Bottle.BORDER_WIDTH;
            if (isFullRow) {
                blockList.removeIf(x -> x.getY() == y);
                blockList.stream().filter(x -> x.getY() > y).forEach(x -> {
                    x.setY(x.getY() - x.getScaledSize());
                });
                fullYList.add(y);
            }
        }
        if (fullYList.size() > 4) {
            throw new RuntimeException();
        }
        for (int i = 0; i < fullYList.size(); i++) {
            score += 10 * Math.pow(2, i);
        }
        lines += fullYList.size();
        if (lines >= needLines) {
            level++;
            needLines += 10;
            tickTime = START_TICK_TIME - (Math.sqrt(1 - 1f / level) * (START_TICK_TIME - 0.2));
            blockList.clear();
            listener.onNewLevel();
        } else if (fullYList.size() > 0) {
            listener.onLineDestroyed();
        }
        if (listener != null) {
            listener.onScoreUpdated();
        }
    }

    public float getBottleWidth() {
        return bottle.getScaledWidth();
    }

    public float getBottleHeight() {
        return bottle.getScaledHeight();
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onNewGame();

        void onContinue();

        void onPause();

        void onEnd();

        void onScoreUpdated();

        void onNewLevel();

        void onTetrominoFall();

        void onLineDestroyed();
    }
}
