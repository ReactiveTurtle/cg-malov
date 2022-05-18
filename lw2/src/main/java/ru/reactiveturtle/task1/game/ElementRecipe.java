package ru.reactiveturtle.task1.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ElementRecipe {
    private final List<String> recipe;
    private final ElementData resultElement;

    public ElementRecipe(String[] recipe, ElementData resultElement) {
        this.recipe = Collections.unmodifiableList(Arrays.asList(Objects.requireNonNull(recipe)));
        validateRecipe(recipe);
        this.resultElement = Objects.requireNonNull(resultElement);
    }

    public List<String> getRecipe() {
        return recipe;
    }

    public ElementData getResultElement() {
        return resultElement;
    }

    private static void validateRecipe(String[] recipe) {
        boolean containsNull = Arrays.stream(recipe).anyMatch(Objects::isNull);
        if (containsNull) {
            throw new IllegalArgumentException("Recipe can't contains null element id");
        }
    }
}
