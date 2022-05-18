package ru.reactiveturtle.task1.game;

import ru.reactiveturtle.task1.GameFrame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class SaveUtils {
    public static final String GAME_SAVE_FILE_NAME = "save.json";
    public static final String DEFAULT_SAVE_FILE_PATH = "src/main/resources/defaultSave.json";
    public static final String ELEMENTS_DATA_FILE_PATH = "src/main/resources/elements.json";
    public static final String ELEMENT_RECIPES_FILE_PATH = "src/main/resources/recipes.json";

    public static File checkAndGetSaveFile() {
        File gameDir = SaveUtils.getGameSaveDir();
        if (!gameDir.exists() && !gameDir.mkdir()) {
            throw new RuntimeException("Can't load save because impossible to create game directory");
        }
        return SaveUtils.getSaveFile();
    }

    public static void createDefaultSave(File saveFile) throws IOException {
        if (!saveFile.createNewFile()) {
            throw new IOException("Can't load save because impossible to create save file");
        }
        File srcFile = new File(SaveUtils.DEFAULT_SAVE_FILE_PATH);
        FileChannel sourceChannel = new FileInputStream(srcFile).getChannel();
        FileChannel destChannel = new FileOutputStream(saveFile).getChannel();

        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());

        sourceChannel.close();
        destChannel.close();
    }

    public static File getSaveFile() {
        return new File(getGameSaveDir(), GAME_SAVE_FILE_NAME);
    }

    public static File getGameSaveDir() {
        return new File(getAppDataDir(), GameFrame.TITLE);
    }

    private static String getAppDataDir() {
        return System.getenv("APPDATA");
    }
}
