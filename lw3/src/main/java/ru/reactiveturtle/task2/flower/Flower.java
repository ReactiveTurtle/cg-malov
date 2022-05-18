package ru.reactiveturtle.task2.flower;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.Transform2D;
import ru.reactiveturtle.sgl.model.Model2d;

public abstract class Flower extends Model2d implements IFlower, Disposable {
    public abstract float getCenter();
}
