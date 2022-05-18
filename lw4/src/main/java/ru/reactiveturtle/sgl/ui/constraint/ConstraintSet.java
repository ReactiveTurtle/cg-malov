package ru.reactiveturtle.sgl.ui.constraint;

import ru.reactiveturtle.sgl.ui.Element;

import java.util.HashSet;
import java.util.Set;

public class ConstraintSet {
    private final Set<Connection> connectionList = new HashSet<>();

    public void addConnection(Element fromElement, BorderType fromBorderType, Element toElement, BorderType toBorderType) {
        connectionList.add(new Connection(fromElement, fromBorderType, toElement, toBorderType));
    }

    public Set<Connection> getConnectionList() {
        return connectionList;
    }
}
