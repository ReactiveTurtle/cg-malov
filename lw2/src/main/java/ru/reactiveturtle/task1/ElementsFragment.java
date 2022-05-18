package ru.reactiveturtle.task1;

import ru.reactiveturtle.task1.game.ElementData;
import ru.reactiveturtle.task1.game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElementsFragment extends JPanel {
    private static final int VH_GAP = 16;
    private static final int COLUMNS = 4;

    private JScrollPane scrollPane;
    private JPanel elementsPanel;

    private final List<JPanel> rowPanels = new ArrayList<>();
    private int elementCount;

    private OnClickListener onClickListener;

    private final Game game;

    public ElementsFragment(Game game) {
        super();
        this.game = game;
        initRoot();
        initContent();
        revalidate();
        repaint();
    }

    public void addElements(ElementData[] elements) {
        JPanel lastRow = rowPanels.get(rowPanels.size() - 1);
        int lastRowElementCount = elementCount % COLUMNS == 0 ? COLUMNS : elementCount % COLUMNS;
        if (elementCount == 0) {
            lastRowElementCount = 0;
        }
        int lastRowAddElementCount = Math.min(COLUMNS - lastRowElementCount, elements.length);
        for (int i = 0; i < lastRowAddElementCount; i++) {
            lastRow.add(getElementView(elements[i]));
        }
        if (elements.length != lastRowAddElementCount) {
            int moduloElements = elements.length - lastRowAddElementCount;
            int rows = (int) Math.ceil(moduloElements / (float) COLUMNS);
            for (int i = 0; i < rows; i++) {
                ElementData[] elementData = new ElementData[Math.min(COLUMNS, moduloElements - i * COLUMNS)];
                System.arraycopy(elements, lastRowAddElementCount + i * COLUMNS, elementData, 0, elementData.length);
                JPanel newRow = rowPanel(Arrays.asList(elementData));
                rowPanels.add(newRow);
                elementsPanel.add(newRow);
            }
        }
        elementCount += elements.length;
        GridLayout layout = (GridLayout) elementsPanel.getLayout();
        layout.setRows(rowPanels.size());
        update();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private void initRoot() {
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);
    }

    private void initContent() {
        scrollPane = new JScrollPane(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JLabel title = new JLabel("Открытые элементы");
        title.setFont(new Font(null, Font.PLAIN, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        add(title);

        final int scrollSpeed = (int) (ElementView.HEIGHT * 0.3f);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(scrollSpeed);
        scrollPane.getVerticalScrollBar().setUnitIncrement(scrollSpeed);
        scrollPane.setViewportBorder(null);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);

        JButton sortButton = new JButton("Сортировать");
        sortButton.addActionListener(e -> {
            game.sortElements();
            for (JPanel rowPanel : rowPanels) {
                elementsPanel.remove(rowPanel);
            }
            rowPanels.clear();
            elementCount = 0;
            addEmptyRowPanel();
            addElements(game.getOpenedElements().toArray(new ElementData[0]));
        });
        sortButton.setBackground(Color.BLACK);
        sortButton.setForeground(Color.WHITE);
        sortButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        sortButton.setFocusPainted(false);
        sortButton.setMargin(new Insets(10, 20, 10, 20));
        add(sortButton);

        initElementsPanel();
    }

    private void initElementsPanel() {
        elementsPanel = new JPanel();
        GridLayout gridLayout = new GridLayout(0, 1);
        gridLayout.setVgap(VH_GAP);
        addEmptyRowPanel();
        elementsPanel.setLayout(gridLayout);
        scrollPane.setViewportView(elementsPanel);
    }

    private void addEmptyRowPanel() {
        JPanel newRow = rowPanel(new ArrayList<>());
        elementsPanel.add(newRow);
        rowPanels.add(newRow);
    }

    private JPanel rowPanel(List<ElementData> row) {
        JPanel panel = new JPanel();
        GridLayout gridLayout = new GridLayout(1, COLUMNS);
        gridLayout.setHgap(VH_GAP);
        panel.setLayout(gridLayout);

        for (ElementData elementData : row) {
            ElementView elementView = getElementView(elementData);
            panel.add(elementView);
        }

        return panel;
    }

    private ElementView getElementView(ElementData elementData) {
        ElementView elementView = new ElementView(elementData);
        elementView.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ElementsFragment.this.onClickListener != null) {
                    onClickListener.onClick(((ElementView) e.getComponent()).getElementData());
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                elementView.setLocation(elementView.getLocation().x, elementView.getLocation().y - 4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                elementView.setLocation(elementView.getLocation().x, elementView.getLocation().y + 4);
            }
        });
        return elementView;
    }

    public interface OnClickListener {
        void onClick(ElementData elementData);
    }

    public void update() {
        scrollPane.setPreferredSize(new Dimension(
                ElementView.WIDTH * 4 + VH_GAP * 4 * 2,
                Math.min(
                        rowPanels.size() * (ElementView.HEIGHT + VH_GAP * 2),
                        getParent().getHeight())));
        scrollPane.setMaximumSize(scrollPane.getPreferredSize());
        setMaximumSize(new Dimension(
                scrollPane.getPreferredSize().width,
                getMaximumSize().height));
        revalidate();
        repaint();
    }
}
