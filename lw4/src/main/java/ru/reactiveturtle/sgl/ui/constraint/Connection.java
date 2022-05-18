package ru.reactiveturtle.sgl.ui.constraint;

import ru.reactiveturtle.sgl.ui.Element;

import java.util.Objects;

public class Connection {
    private Element fromElement;
    private BorderType fromBorderType;

    private Element toElement;
    private BorderType toBorderType;

    public Connection(Element fromElement,
                      BorderType fromBorderType,
                      Element toElement,
                      BorderType toBorderType) {
        this.fromElement = fromElement;
        this.fromBorderType = fromBorderType;
        this.toElement = toElement;
        this.toBorderType = toBorderType;
    }

    public Element getFromElement() {
        return fromElement;
    }

    public BorderType getFromBorderType() {
        return fromBorderType;
    }

    public Element getToElement() {
        return toElement;
    }

    public BorderType getToBorderType() {
        return toBorderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection that = (Connection) o;
        return Objects.equals(fromElement, that.fromElement) && fromBorderType == that.fromBorderType && Objects.equals(toElement, that.toElement) && toBorderType == that.toBorderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromElement, fromBorderType, toElement, toBorderType);
    }
}
