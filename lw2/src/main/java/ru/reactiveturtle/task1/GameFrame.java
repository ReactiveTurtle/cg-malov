package ru.reactiveturtle.task1;

import ru.reactiveturtle.task1.game.ElementData;
import ru.reactiveturtle.task1.game.Game;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
    public static final String TITLE = "Alchemy";

    private static final int WIDTH = 800;
    public static final int HEIGHT = 480;

    private ElementsFragment elementsFragment;

    private Game game;
    private SoundPlayer soundPlayer;

    public GameFrame() {
        super(TITLE);
        setSize(WIDTH, HEIGHT);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                elementsFragment.update();
            }
        });
    }

    public void start() {
        soundPlayer = new SoundPlayer();
        game = new Game();
        game.start();
        setContentPane(buildContentPane());
        setVisible(true);
        elementsFragment.addElements(game.getOpenedElements().toArray(new ElementData[0]));
    }

    private JPanel buildContentPane() {
        JPanel panel = new JPanel();

        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.X_AXIS);
        panel.setLayout(boxLayout);

        elementsFragment = new ElementsFragment(game);
        CombineFragment combineFragment = new CombineFragment();
        elementsFragment.setOnClickListener(combineFragment::setElement);
        combineFragment.setCombineListener((first, second) -> {
            ElementData[] result = game.combine(first, second);
            int oldOpenedElementCount = game.getOpenedElements().size();
            if (result.length != 0) {
                ElementData[] filteredResult = game.filterResult(result);
                if (filteredResult.length > 0) {
                    soundPlayer.playNewElement();
                } else {
                    soundPlayer.playElementCreated();
                }
                game.addOpenedElements(filteredResult);
                elementsFragment.addElements(filteredResult);
                game.saveGame();
            }
            combineFragment.setResult(result);

            boolean isWin = oldOpenedElementCount != game.getOpenedElements().size() &&
                    game.getOpenedElements().size() == game.getAllElementCount();
            if (isWin) {
                combineFragment.showWin();
            }
        });
        panel.add(elementsFragment);
        panel.add(combineFragment);

        return panel;
    }


}
