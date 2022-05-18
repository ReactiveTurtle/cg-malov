package ru.reactiveturtle.task1.game;

import java.util.Objects;

public class ElementData {
    private final String id;
    private final String name;
    private final String textureFile;

    public ElementData(
            String id,
            String name,
            String textureFile) {
        this.id = id;
        this.name = name;
        this.textureFile = textureFile;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTextureFile() {
        return textureFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ElementData that = (ElementData) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(textureFile, that.textureFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, textureFile);
    }
}
