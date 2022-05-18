package ru.reactiveturtle.task1.game;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLoader {
    public List<ElementData> loadAllElements() throws IOException {
        List<ElementData> elementDataList = new ArrayList<>();
        JSONArray jsonArray = readJsonArray(SaveUtils.ELEMENTS_DATA_FILE_PATH);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            elementDataList.add(new ElementData(
                    object.getString("id"),
                    object.getString("name"),
                    object.getString("texture")));
        }

        return elementDataList;
    }

    public List<ElementRecipe> loadRecipes(List<ElementData> elementDataList) throws IOException {
        List<ElementRecipe> elementRecipes = new ArrayList<>();
        JSONArray jsonArray = readJsonArray(SaveUtils.ELEMENT_RECIPES_FILE_PATH);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            String resultElementId = object.getString("result_element_id");
            elementRecipes.add(new ElementRecipe(
                    object.getJSONArray("recipe")
                            .toList()
                            .stream()
                            .map(Object::toString)
                            .toArray(String[]::new),
                    findElementDataById(elementDataList, resultElementId)));
        }
        return Collections.unmodifiableList(elementRecipes);
    }

    public List<ElementData> loadSave(List<ElementData> elementDataList) throws IOException {
        File saveFile = SaveUtils.checkAndGetSaveFile();
        if (!saveFile.exists()) {
            SaveUtils.createDefaultSave(saveFile);
        }
        List<ElementData> openedElements = new ArrayList<>();
        JSONArray jsonArray = readJsonArray(saveFile.getAbsolutePath());
        for (int i = 0; i < jsonArray.length(); i++) {
            ElementData elementData = findElementDataById(elementDataList, jsonArray.getString(i));
            openedElements.add(elementData);
        }

        return openedElements;
    }

    private static ElementData findElementDataById(List<ElementData> elementDataList, String id) {
        return elementDataList.stream()
                .filter(x -> x.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    private JSONArray readJsonArray(String filePath) throws IOException {
        File elementsFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(elementsFile));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        return new JSONArray(stringBuilder.toString());
    }
}
