package ru.reactiveturtle.sgl;

public interface ApplicationImpl {
    void createWindow(String title, int width, int height, boolean isFullscreen, Window.WindowListener windowListener);

    DisplayMetrics getDisplayMetrics();
}
