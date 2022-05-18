package ru.reactiveturtle.task1.game;

import java.io.*;
import java.util.*;

public class Game {
    private SaveWriter saveWriter;

    private List<ElementData> allElementDataList;
    private List<ElementRecipe> elementRecipes;
    private List<ElementData> openedElements;

    public void start() {
        saveWriter = new SaveWriter();
        GameLoader gameLoader = new GameLoader();
        try {
            allElementDataList = Collections.unmodifiableList(gameLoader.loadAllElements());
            elementRecipes = Collections.unmodifiableList(gameLoader.loadRecipes(allElementDataList));
            openedElements = gameLoader.loadSave(allElementDataList);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ElementData[] combine(ElementData first, ElementData second) {
        List<String> combineElements = Arrays.asList(first.getId(), second.getId());
        return elementRecipes.stream()
                .filter(x -> combineElements.containsAll(x.getRecipe()))
                .map(ElementRecipe::getResultElement)
                .toArray(ElementData[]::new);
    }

    public ElementData[] filterResult(ElementData[] result) {
        List<ElementData> filteredResult = new ArrayList<>();
        for (ElementData elementData : result) {
            if (!openedElements.contains(elementData)) {
                filteredResult.add(elementData);
            }
        }
        return filteredResult.toArray(new ElementData[0]);
    }

    public void sortElements() {
        openedElements.sort(Comparator.comparing(ElementData::getName));
    }

    public void addOpenedElements(ElementData[] result) {
        for (ElementData elementData : result) {
            if (!openedElements.contains(elementData)) {
                openedElements.add(elementData);
            }
        }
    }

    public List<ElementData> getOpenedElements() {
        return openedElements;
    }

    public int getAllElementCount() {
        return allElementDataList.size();
    }

    public void saveGame() {
        saveWriter.saveGame(openedElements);
    }
}
