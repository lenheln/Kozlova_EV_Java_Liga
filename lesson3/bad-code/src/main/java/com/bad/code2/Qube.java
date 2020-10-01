package com.bad.code2;

/**
 *  Описывает куб
 */
public class Qube extends Shape3D {

    @Override
    public double calculateVolume() {
        return Math.pow(edgeSize , 3);
    }

    @Override
    public void printVolume() {
        System.out.printf("Qube volume: %.2f%n", this.calculateVolume());
    }

    public Qube(double x, double y, double z, double edgeSize) {
        super(x, y, z, edgeSize);
    }
}
