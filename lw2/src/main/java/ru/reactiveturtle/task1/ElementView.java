package ru.reactiveturtle.task1;

import ru.reactiveturtle.task1.game.ElementData;

import javax.swing.*;
import java.awt.*;

public class ElementView extends JLabel {
    public static final int WIDTH = 48;
    public static final int HEIGHT = 72;
    public static final int ICON_SIZE = 48;
    private final ElementData elementData;

    public ElementView(ElementData elementData) {
        super(elementData.getName(), getImageIcon(elementData), JLabel.CENTER);
        this.elementData = elementData;
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.BOTTOM);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public ElementData getElementData() {
        return elementData;
    }

    private static ImageIcon getImageIcon(ElementData elementData) {
        return new ImageIcon(
                new ImageIcon("src/main/resources/texture/" + elementData.getTextureFile())
                        .getImage()
                        .getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_DEFAULT));
    }
}
