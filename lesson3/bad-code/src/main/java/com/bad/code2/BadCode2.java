package com.bad.code2;

/**
 *  Превращение плохого кода в хороший (я надеюсь =))
 */
public class BadCode2 {
    public static void main(String... args) {

        /**
         *  Создаем куб и выводим в консоль его объем
         */
        Qube qube = new Qube(1d, 1d, 1d, 5d);
        qube.printVolume();

        /**
         *  Создаем квадрат и выводим в консоль его площадь
         */
        Square square = new Square(1d, 1d, 5d);
        square.printArea();
    }
}
