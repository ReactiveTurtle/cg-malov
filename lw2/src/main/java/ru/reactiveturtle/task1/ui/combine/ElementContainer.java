package ru.reactiveturtle.task1.ui.combine;

import ru.reactiveturtle.task1.ElementView;
import ru.reactiveturtle.task1.game.ElementData;

import javax.swing.*;
import java.awt.*;

public class ElementContainer extends JPanel {
    private ElementView elementView;

    public ElementContainer() {
        super();
        Dimension elementContainerSize = new Dimension(72, 72);
        GridLayout gridLayout = new GridLayout(1, 1);
        setLayout(gridLayout);
        setPreferredSize(elementContainerSize);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    public void createElementView(ElementData elementData) {
        setElementView(new ElementView(elementData));
    }

    public void setElementView(ElementView elementView) {
        if (this.elementView != null) {
            remove(this.elementView);
        }
        if (elementView != null) {
            add(elementView);
        }
        this.elementView = elementView;
    }

    public ElementView getElementView() {
        return elementView;
    }

    public ElementData getElementData() {
        return getElementView().getElementData();
    }

    public boolean hasViews() {
        return elementView != null;
    }
}
