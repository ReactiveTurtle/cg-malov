package ru.reactiveturtle.sgl.ui.label;

import ru.reactiveturtle.sgl.Texture;
import ru.reactiveturtle.sgl.shader.ShaderProgram;
import ru.reactiveturtle.sgl.ui.AdaptType;
import ru.reactiveturtle.sgl.ui.Element;
import ru.reactiveturtle.sgl.ui.HorizontalAlign;
import ru.reactiveturtle.sgl.ui.VerticalAlign;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Label extends Element {
    private int horizontalTextAlign = HorizontalAlign.LEFT;
    private int verticalTextAlign = VerticalAlign.TOP;
    private String[] textLines = new String[0];

    public Label() {
        super();
        setAdaptType(AdaptType.FIT);
    }

    @Override
    public void draw(ShaderProgram shaderProgram) {
        super.draw(shaderProgram);
    }

    public void setHorizontalTextAlign(int horizontalTextAlign) {
        if (this.horizontalTextAlign == horizontalTextAlign) {
            return;
        }
        this.horizontalTextAlign = horizontalTextAlign;
        if (!HorizontalAlign.isValid(horizontalTextAlign)) {
            this.horizontalTextAlign = HorizontalAlign.LEFT;
        }
        invalidateTexture();
    }

    public void setVerticalTextAlign(int verticalTextAlign) {
        if (this.verticalTextAlign == verticalTextAlign) {
            return;
        }
        this.verticalTextAlign = verticalTextAlign;
        if (!VerticalAlign.isValid(verticalTextAlign)) {
            this.verticalTextAlign = VerticalAlign.TOP;
        }
        invalidateTexture();
    }

    public void setText(String text) {
        this.textLines = text == null ? new String[0] : text.split("\n");
        adaptSize();
        invalidateTexture();
    }

    public String getText() {
        return String.join("\n", textLines);
    }

    @Override
    protected void onAdaptFit() {
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setBackground(new Color(255, 255, 255, 0));
        graphics2D.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        Font font = new Font("TimesRoman", Font.PLAIN, 20);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        float textWidth = 0;
        float textHeight = 0;
        for (String line : textLines) {
            Rectangle2D stringBounds = font.getStringBounds(line, graphics2D.getFontRenderContext());
            textWidth = (float) Math.max(textWidth, stringBounds.getWidth());
            textHeight += stringBounds.getHeight();
        }
        setAdaptedSize((int) textWidth,(int) textHeight);
    }

    @Override
    protected void repaintTexture(Texture texture) {
        BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setBackground(new Color(255, 255, 255, 0));
        graphics2D.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        Font font = new Font("TimesRoman", Font.PLAIN, 20);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        float textWidth = 0;
        float textHeight = 0;
        float[] lineWidthArray = new float[textLines.length];
        float[] lineHeightArray = new float[textLines.length];
        for (int i = 0; i < textLines.length; i++) {
            String line = textLines[i];
            Rectangle2D stringBounds = font.getStringBounds(line, graphics2D.getFontRenderContext());
            textWidth = (float) Math.max(textWidth, stringBounds.getWidth());
            textHeight += stringBounds.getHeight();
            lineWidthArray[i] = (float) stringBounds.getWidth();
            lineHeightArray[i] = (float) stringBounds.getHeight();
        }

        bufferedImage = new BufferedImage(
                Math.max((int) getAdaptedWidth(), 1),
                Math.max((int) getAdaptedHeight(), 1),
                BufferedImage.TYPE_INT_ARGB);
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(getBackgroundColor().toAwtColor());
        graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        for (int i = 0; i < textLines.length; i++) {
            String line = textLines[i];
            graphics2D.drawString(
                    line,
                    (bufferedImage.getWidth() - lineWidthArray[i]) / 2f * ((horizontalTextAlign & HorizontalAlign.CENTER) >> 1)
                            + (bufferedImage.getWidth() - lineWidthArray[i]) * ((horizontalTextAlign & HorizontalAlign.RIGHT) >> 2),
                    (bufferedImage.getHeight() - textHeight) / 2f * ((verticalTextAlign & VerticalAlign.CENTER) >> 1)
                            + (bufferedImage.getHeight() - lineHeightArray[i]) * ((verticalTextAlign & VerticalAlign.BOTTOM) >> 2)
                            + lineHeightArray[i] * (i + 1));
        }

        texture.set(bufferedImage);
    }
}
