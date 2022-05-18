package ru.reactiveturtle.task1.ui.combine;

import ru.reactiveturtle.task1.ElementView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultElementsContainer extends JPanel {
    private final List<ElementView> elementViews;

    public ResultElementsContainer() {
        super();
        Dimension elementContainerSize = new Dimension(72, 72);
        GridLayout gridLayout = new GridLayout(1, 1);
        setLayout(gridLayout);
        setPreferredSize(elementContainerSize);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        elementViews = new ArrayList<>();
    }

    public void addElementViews(List<ElementView> elementViews) {
        this.elementViews.addAll(elementViews);
        for (ElementView elementView : elementViews) {
            add(elementView);
        }
    }

    public void clear() {
        for (ElementView elementView : elementViews) {
            remove(elementView);
        }
        elementViews.clear();
    }

    public List<ElementView> getElementViews() {
        return Collections.unmodifiableList(elementViews);
    }

    public boolean hasViews() {
        return elementViews.size() > 0;
    }
}
