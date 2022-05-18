package ru.reactiveturtle.task3;

import ru.reactiveturtle.sgl.OrthographicCamera;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.Window;
import ru.reactiveturtle.sgl.control.Keyboard;
import ru.reactiveturtle.sgl.shape.ShapeBatch;

public class TetrisRenderContext extends RenderContext {
    private ShapeBatch shapeBatch;
    private Game game;
    private UI ui;
    private SoundPlayer soundPlayer;

    public TetrisRenderContext(Window window) {
        super(window);
    }

    @Override
    public void start() {
        setCamera(new OrthographicCamera(getWindow().getWidth(), getWindow().getHeight()));
        getWindow().getKeyboard().setListener(keyboardListener);
        shapeBatch = new ShapeBatch(this);
        game = new Game(this, shapeBatch);
        game.start();
        ui = new UI(this, game);
        soundPlayer = new SoundPlayer();

        game.setListener(new Game.Listener() {
            @Override
            public void onNewGame() {
                soundPlayer.playNewGame();
            }

            @Override
            public void onContinue() {
                ui.hideLabels();
            }

            @Override
            public void onPause() {
                ui.showPause();
            }

            @Override
            public void onEnd() {
                ui.showEnd();
                soundPlayer.playGameOver();
            }

            @Override
            public void onScoreUpdated() {
                ui.updateText();
            }

            @Override
            public void onNewLevel() {
                soundPlayer.playNewLevel();
            }

            @Override
            public void onTetrominoFall() {
                soundPlayer.playFall();
            }

            @Override
            public void onLineDestroyed() {
                soundPlayer.playLineDestroy();
            }
        });
    }

    @Override
    public void render() {
        clearColorBufferBit();
        clearColor(0.5f, 0f, 0f, 1f);
        game.update(getDeltaTime());
        game.draw();
        ui.draw();
    }

    @Override
    public void dispose() {
        game.dispose();
        shapeBatch.dispose();
        ui.dispose();
    }

    private final Keyboard.Listener keyboardListener = new Keyboard.Listener() {
        @Override
        public void onDown(Keyboard.Key key) {
            handleKey(key);
        }

        @Override
        public void onRepeat(Keyboard.Key key) {
            handleKey(key);
        }

        private void handleKey(Keyboard.Key key) {
            if (game.isPause() && key != Keyboard.Key.P && key != Keyboard.Key.ESCAPE ||
                    game.isEnd() && key != Keyboard.Key.N && key != Keyboard.Key.ESCAPE) {
                return;
            }
            switch (key) {
                case UP:
                    game.rotateTetromino();
                    break;
                case LEFT:
                    game.moveTetrominoLeft();
                    break;
                case RIGHT:
                    game.moveTetrominoRight();
                    break;
                case DOWN:
                    game.moveTetrominoDown();
                    break;
                case P:
                    game.pause();
                    break;
                case N:
                    game.newGame();
                    break;
                case ESCAPE:
                    getWindow().close();
                    break;
            }
        }
    };
}
