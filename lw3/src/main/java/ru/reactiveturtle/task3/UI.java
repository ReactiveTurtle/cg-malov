package ru.reactiveturtle.task3;

import ru.reactiveturtle.sgl.Color;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.ui.AdaptType;
import ru.reactiveturtle.sgl.ui.HorizontalAlign;
import ru.reactiveturtle.sgl.ui.label.Label;
import ru.reactiveturtle.sgl.ui.VerticalAlign;

public class UI extends ru.reactiveturtle.sgl.ui.UI {
    private Label pauseLabel;
    private Label endLabel;
    private Label gameProgressLabel;
    private Label nextTetrominoLabel;

    private final Game game;

    public UI(RenderContext renderContext, Game game) {
        super(renderContext);
        this.game = game;

        gameProgressLabel = new Label();
        gameProgressLabel.setMarginLeft((int) (getAdaptedWidth() / 2f + game.getBottleWidth() / 2f));
        gameProgressLabel.setMarginTop((int) (getAdaptedHeight() / 2f - game.getBottleHeight() / 2f));
        gameProgressLabel.setVerticalTextAlign(VerticalAlign.CENTER);
        updateText();
        add(gameProgressLabel);

        nextTetrominoLabel = new Label();
        nextTetrominoLabel.setText("Next: ");
        nextTetrominoLabel.setVerticalTextAlign(VerticalAlign.CENTER);
        nextTetrominoLabel.setMarginTop((int) (
                gameProgressLabel.getAdaptedHeight()
                        + gameProgressLabel.getMarginTop()));
        nextTetrominoLabel.setMarginLeft(gameProgressLabel.getMarginLeft());
        add(nextTetrominoLabel);
        game.setNextTetrominoPosition(
                nextTetrominoLabel.getAdaptedWidth() + nextTetrominoLabel.getX(),
                nextTetrominoLabel.getY() - nextTetrominoLabel.getAdaptedHeight() / 2f);

        pauseLabel = new Label();
        pauseLabel.setBackgroundColor(new Color(0, 0, 0, 0.5f));
        pauseLabel.setVerticalTextAlign(VerticalAlign.CENTER);
        pauseLabel.setHorizontalTextAlign(HorizontalAlign.CENTER);
        pauseLabel.setAdaptType(AdaptType.PARENT);
        pauseLabel.setText("Пауза");

        endLabel = new Label();
        endLabel.setBackgroundColor(pauseLabel.getBackgroundColor());
        endLabel.setVerticalTextAlign(VerticalAlign.CENTER);
        endLabel.setHorizontalTextAlign(HorizontalAlign.CENTER);
        endLabel.setAdaptType(AdaptType.PARENT);
        endLabel.setText("Нажмите N, чтобы начать новую игру\n" +
                "Нажмите Escape, чтобы выйти из игры");
    }

    public void showPause() {
        add(pauseLabel);
    }

    public void showEnd() {
        add(endLabel);
    }

    public void hideLabels() {
        remove(pauseLabel);
        remove(endLabel);
    }

    public void updateText() {
        String text = "Score: " + game.getScore() + "\n"
                + "Level: " + game.getLevel() + "\n"
                + "Lines: " + game.getLines() + "\n"
                + "Need lines: " + game.getNeedLines();
        gameProgressLabel.setText(text);
    }
}
