package ru.reactiveturtle.sgl;

import org.joml.Vector4f;

public class Color {
    private final int redComponent;
    private final int greenComponent;
    private final int blueComponent;
    private final int alphaComponent;
    private final Vector4f components;

    public Color(int rgba) {
        this(
                (rgba & 0xFF000000) >> 24,
                (rgba & 0x00FF0000) >> 16,
                (rgba & 0x0000FF00) >> 8,
                (rgba & 0x000000FF));
    }

    public Color(float red, float green, float blue, float alpha) {
        this(Math.round(red * 255f),
                Math.round(green * 255f),
                Math.round(blue * 255f),
                Math.round(alpha * 255f));
    }

    public Color(int red, int green, int blue, int alpha) {
        validateRange(red);
        validateRange(green);
        validateRange(blue);
        validateRange(alpha);
        this.redComponent = red > 127 ? red - 256 : red;
        this.greenComponent = green > 127 ? green - 256 : green;
        this.blueComponent = blue > 127 ? blue - 256 : blue;
        this.alphaComponent = alpha > 127 ? alpha - 256 : alpha;

        components = new Vector4f(redFloat(), greenFloat(), blueFloat(), alphaFloat());
    }

    public Color(String hex) {
        this(parseHex(hex));
    }

    public float redFloat() {
        return (redComponent < 0 ? redComponent + 256 : redComponent) / 255f;
    }

    public float greenFloat() {
        return (greenComponent < 0 ? greenComponent + 256 : greenComponent) / 255f;
    }

    public float blueFloat() {
        return (blueComponent < 0 ? blueComponent + 256 : blueComponent) / 255f;
    }

    public float alphaFloat() {
        return (alphaComponent < 0 ? alphaComponent + 256 : alphaComponent) / 255f;
    }

    public Vector4f getComponentsAsVector() {
        return new Vector4f(components);
    }

    private void validateRange(int component) {
        if (component < 0 || component > 255) {
            throw new IllegalArgumentException(
                    String.format("Color component %d must be within range %d-%d", component, 0, 255));
        }
    }

    public int value() {
        int color = 0;
        color |= (redComponent << 24) & 0xFF000000;
        color |= (greenComponent << 16) & 0x00FF0000;
        color |= (blueComponent << 8) & 0x0000FF00;
        color |= (alphaComponent) & 0x000000FF;
        return color;
    }

    private static int parseHex(String hex) {
        if (!hex.startsWith("#")) {
            throw new IllegalArgumentException(hex);
        }
        String formattedHex = hex;
        if (formattedHex.length() == 7) {
            formattedHex = formattedHex + "FF";
        }
        if (formattedHex.length() != 9) {
            throw new IllegalArgumentException("Invalid string length of " + hex);
        }
        return Integer.parseUnsignedInt(formattedHex.substring(1), 16);
    }

    public java.awt.Color toAwtColor() {
        return new java.awt.Color(redFloat(), greenFloat(), blueFloat(), alphaFloat());
    }
}
