package com.bad.code2;

//TODO: квадрат - не трехмерный. Можно сделать класс фигура, и расширения на него трехмерная и двумерная
//Описание класса
public class Square implements Shape3D {
    private Double x;
    private Double y;
    private Double edgeSize;
    public Square(Double x, Double y, Double edgeSize) {
        this.x = x;
        this.y = y;
        this.edgeSize = edgeSize;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    //YAGNI
    @Override
    public Double getZ() {
        return null;
    }

    //YAGNI
    @Override
    public Double getVolume() {
        return null;
    }

    public Double getPerimeter() {
        return edgeSize * edgeSize;
    }
}
