package ru.reactiveturtle.task1.game;

import org.json.JSONArray;

import java.io.*;
import java.util.List;

public class SaveWriter {
    public void saveGame(List<ElementData> openedElements) {
        JSONArray jsonArray = new JSONArray();
        for (ElementData data : openedElements) {
            jsonArray.put(data.getId());
        }
        File saveFile = SaveUtils.checkAndGetSaveFile();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile));
            writer.write(jsonArray.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
