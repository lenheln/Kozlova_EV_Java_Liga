package com.bad.code2;

/**
 *  Описывает правильный многоугольник
 */
public abstract class Shape2D {
    /**
     *  Координата по оси x
     */
    double x;
    /**
     *  Координата по оси y
     */
    double y;
    /**
     *  Длина стороны многоугольника
     */
    double edgeSize;

    /**
     *  Возвращает площадь многоугольника
     *
     * @return площадь многоугольника
     */
    public abstract double calculateArea();

    /**
     *  Выводит в консоль площадь многоугольника
     */
    public abstract void printArea();

    public Shape2D(double x, double y, double edgeSize) {
        this.x = x;
        this.y = y;
        this.edgeSize = edgeSize;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getEdgeSize() {
        return edgeSize;
    }

    public void setEdgeSize(double edgeSize) {
        this.edgeSize = edgeSize;
    }

}
