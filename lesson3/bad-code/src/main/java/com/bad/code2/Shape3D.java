package com.bad.code2;
/**
 *  Описывает правильный многогранник
 */
public abstract class Shape3D {
    /**
     *  Координата по оси x
     */
    private double x;
    /**
     *  Координата по оси y
     */
    private double y;
    /**
     *  Координата по оси z
     */
    private double z;
    /**
     *  Длина ребра
     */
    private double edgeSize;

    /**
     *  Возвращает объем многогранника
     *
     * @return объем многогранника
     */
    public abstract double calculateVolume();

    /**
     *  Выводит в консоль объем многогранника
     */
    public abstract void printVolume();

    public Shape3D(double x, double y, double z, double edgeSize) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getEdgeSize() {
        return edgeSize;
    }

    public void setEdgeSize(double edgeSize) {
        this.edgeSize = edgeSize;
    }
}
