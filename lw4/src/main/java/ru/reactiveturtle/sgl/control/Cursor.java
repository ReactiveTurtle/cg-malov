package ru.reactiveturtle.sgl.control;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import ru.reactiveturtle.sgl.Window;

import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Cursor {
    private final Window window;
    private final Vector2f lastCursorPosition = new Vector2f();
    private GLFWCursorPosCallbackI cursorPosCallback;

    public Cursor(Window window) {
        Objects.requireNonNull(window);
        this.window = window;
    }

    public void setListener(Listener listener) {
        if (listener == null) {
            cursorPosCallback = null;
            glfwSetCursorPosCallback(window.getWindowId(), null);
            glfwSetMouseButtonCallback(window.getWindowId(), null);
            return;
        }
        lastCursorPosition.set(getPosition());
        cursorPosCallback = (windowId, xpos, ypos) -> {
            listener.onMove(new Vector2f((float) xpos, (float) ypos),
                    xpos - lastCursorPosition.x, ypos - lastCursorPosition.y);
            lastCursorPosition.set(xpos, ypos);
        };
        glfwSetCursorPosCallback(window.getWindowId(), cursorPosCallback);
        glfwSetMouseButtonCallback(window.getWindowId(), (windowId, button, action, mods) -> {
            Vector2f cursorPosition = getPosition();
            switch (action) {
                case GLFW_RELEASE:
                    listener.onUp(cursorPosition, map(button));
                    break;
                case GLFW_PRESS:
                    listener.onDown(cursorPosition, map(button));
                    break;
                case GLFW_REPEAT:
                    listener.onRepeat(cursorPosition, map(button));
                    break;
            }
        });
    }

    public void show() {
        glfwSetInputMode(window.getWindowId(), GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public void hide() {
        glfwSetInputMode(window.getWindowId(), GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    private static Key map(int glfwKey) {
        switch (glfwKey) {
            case GLFW_MOUSE_BUTTON_LEFT:
                return Key.LEFT;
            case GLFW_MOUSE_BUTTON_MIDDLE:
                return Key.MIDDLE;
            case GLFW_MOUSE_BUTTON_RIGHT:
                return Key.RIGHT;
            default:
                throw new RuntimeException("Key " + glfwKey + " not present.");
        }
    }

    public Vector2f getPosition() {
        double[] xPos = new double[1];
        double[] yPos = new double[1];
        glfwGetCursorPos(window.getWindowId(), xPos, yPos);
        return new Vector2f((float) xPos[0], (float) yPos[0]);
    }

    public void moveToCenter() {
        Vector2i windowPosition = window.getPosition();
        glfwSetCursorPosCallback(window.getWindowId(), null);
        lastCursorPosition.set(
                windowPosition.x + window.getWidth() / 2f,
                windowPosition.y + window.getHeight() / 2f);
        glfwSetCursorPos(
                window.getWindowId(), lastCursorPosition.x, lastCursorPosition.y);
        glfwSetCursorPosCallback(window.getWindowId(), cursorPosCallback);
    }

    public static class Listener {
        public void onDown(Vector2f cursorPosition, Key key) {
        }

        public void onMove(Vector2f cursorPosition, double biasX, double biasY) {
        }

        public void onUp(Vector2f cursorPosition, Key key) {
        }

        public void onRepeat(Vector2f cursorPosition, Key key) {
        }
    }

    public enum Key {
        LEFT,
        MIDDLE,
        RIGHT
    }
}
