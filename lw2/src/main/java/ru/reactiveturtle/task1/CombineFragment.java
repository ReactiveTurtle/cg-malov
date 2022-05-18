package ru.reactiveturtle.task1;

import ru.reactiveturtle.task1.game.ElementData;
import ru.reactiveturtle.task1.ui.combine.ElementContainer;
import ru.reactiveturtle.task1.ui.combine.ResultElementsContainer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class CombineFragment extends JPanel {
    private final ElementContainer firstElementContainer;
    private final ElementContainer secondElementContainer;
    private final ResultElementsContainer resultElementsContainer;

    private final JLabel errorLabel;
    private boolean isError = false;

    private final JLabel resultLabel;
    private final JLabel winLabel;

    private CombineListener combineListener;

    public CombineFragment() {
        super();
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxLayout);

        JPanel rootPanel = new JPanel();

        resultLabel = new JLabel();
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBackground(Color.decode("#4F81BD"));
        resultLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        resultLabel.setBorder(BorderFactory.createLineBorder(resultLabel.getBackground(), 12));
        resultLabel.setOpaque(true);
        resultLabel.setVisible(false);

        winLabel = new JLabel("Открыты все елементы. Вы победили!!!");
        winLabel.setForeground(Color.WHITE);
        winLabel.setBackground(Color.decode("#0BE400"));
        winLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        winLabel.setBorder(BorderFactory.createLineBorder(winLabel.getBackground(), 12));
        winLabel.setOpaque(true);
        winLabel.setVisible(false);

        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(Box.createVerticalGlue());
        box.add(Box.createVerticalGlue());
        box.add(rootPanel);
        box.add(Box.createVerticalGlue());
        box.add(winLabel);
        box.add(Box.createVerticalGlue());
        box.add(resultLabel);
        box.add(Box.createVerticalGlue());
        box.add(Box.createVerticalGlue());
        add(box);

        firstElementContainer = new ElementContainer();
        rootPanel.add(firstElementContainer);

        JLabel plusLabel = new JLabel("+");
        plusLabel.setFont(new Font(null, Font.PLAIN, 24));
        rootPanel.add(plusLabel);

        secondElementContainer = new ElementContainer();
        rootPanel.add(secondElementContainer);

        JLabel equalsLabel = new JLabel("=");
        equalsLabel.setFont(new Font(null, Font.PLAIN, 24));
        rootPanel.add(equalsLabel);

        resultElementsContainer = new ResultElementsContainer();
        rootPanel.add(resultElementsContainer);

        errorLabel = new JLabel(new ImageIcon(
                new ImageIcon("src/main/resources/texture/error.png")
                        .getImage()
                        .getScaledInstance(ElementView.ICON_SIZE, ElementView.ICON_SIZE, Image.SCALE_DEFAULT)));
    }

    public void setElement(ElementData elementData) {
        if (isError || resultElementsContainer.hasViews()) {
            clear();
        }
        resultElementsContainer.remove(errorLabel);
        if (!firstElementContainer.hasViews()) {
            firstElementContainer.createElementView(elementData);
        } else if (!secondElementContainer.hasViews()) {
            secondElementContainer.createElementView(elementData);
            if (this.combineListener != null) {
                this.combineListener.onFill(
                        firstElementContainer.getElementData(),
                        secondElementContainer.getElementData());
            }
        }
        revalidate();
        repaint();
    }

    public void clear() {
        firstElementContainer.setElementView(null);
        secondElementContainer.setElementView(null);
        if (isError) {
            resultElementsContainer.remove(errorLabel);
            isError = false;
        }
        if (resultElementsContainer.hasViews()) {
            for (ElementView view : resultElementsContainer.getElementViews()) {
                resultElementsContainer.remove(view);
            }
            GridLayout gridLayout = (GridLayout) resultElementsContainer.getLayout();
            gridLayout.setColumns(1);
            resultElementsContainer.setPreferredSize(firstElementContainer.getPreferredSize());
            resultElementsContainer.clear();
        }
        update();
    }

    private void update() {
        revalidate();
        repaint();
    }

    public void setCombineListener(CombineListener combineListener) {
        this.combineListener = combineListener;
    }

    public void setResult(ElementData[] result) {
        if (result.length == 0) {
            error();
            return;
        }
        GridLayout gridLayout = (GridLayout) resultElementsContainer.getLayout();
        gridLayout.setColumns(result.length);
        resultElementsContainer.setPreferredSize(new Dimension(
                (int) resultElementsContainer.getPreferredSize().getWidth() * result.length,
                (int) resultElementsContainer.getPreferredSize().getHeight()));
        resultElementsContainer.addElementViews(
                Arrays.stream(result)
                        .map(ElementView::new)
                        .collect(Collectors.toList()));

        String resultText = firstElementContainer.getElementData().getName() +
                " + " +
                secondElementContainer.getElementData().getName() + " = " +
                Arrays.stream(result)
                        .map(ElementData::getName)
                        .collect(Collectors.joining(", "));
        resultLabel.setVisible(true);
        resultLabel.setText(resultText);
        update();
    }

    private void error() {
        resultElementsContainer.add(errorLabel);
        isError = true;
        update();
    }

    public void showWin() {
        winLabel.setVisible(true);
    }

    public interface CombineListener {
        void onFill(ElementData first, ElementData second);
    }
}
