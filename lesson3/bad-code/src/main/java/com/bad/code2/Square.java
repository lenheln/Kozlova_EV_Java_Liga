package com.bad.code2;

/**
 *  Описывает квадрат
 */
public class Square extends Shape2D {

    @Override
    public double calculateArea() {
        return Math.pow(super.getEdgeSize(), 2);
    }

    @Override
    public void printArea() {
        System.out.printf("Square area: %.2f%n ", this.calculateArea());
    }

    public Square(double x, double y, double edgeSize) {
        super(x, y, edgeSize);
    }
}
