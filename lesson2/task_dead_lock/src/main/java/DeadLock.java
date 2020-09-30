package ru.philosophyit;

public class DeadLock {

  static class Friend {
    private final String name;

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    /**
     * Вызов alphonse.bow(gaston) блокирует объект alphonse и для этого объекта мы не можем вызвать другой
     * синхронизированный метод.
     * Выхов gaston.bow(alphonse) блокирует объект gastone.
     * После этого мы не можем вызвать синхронизированный метод bowBack для этих объектов,
     * так как они заблокированы.
     * Ко времени вызова метода bower.bowBack(this) текущий объект уже выполнил все нужные нам действия,
     * поэтому вызов метода нужно вывести из блока синхронизации.
     * Решение: синхронизируем блок кода, где объект реально занят, а не весь метод.
     */

    public void bow(Friend bower) {
      synchronized(this) {
        System.out.format("Bow! %s говорит: %s подстрелил меня!\n", this.name, bower.getName());
        System.out.format("Bow! %s говорит: стреляю в ответ!\n", this.name);
      }

      //Текщий объект произвел все нужные нам действия и мы можем его освободить от гнёта синхронизации
      bower.bowBack(this);
    }

    public void bowBack(Friend bower) {

      /**
       * Здесь синхронизация нужна для того, чтобы случайно не вызвать этот метод у объекта,
       * пока тот медленно падает от сразившей его пули
       */

      synchronized (this) {
        System.out.format("Bow back! %s говорит: %s подстрелил меня!\n", this.name, bower.getName());
      }
    }
  }

  /**
   * Точка входа в программу
   *
   * @param args аргументы командной строки
   */
  public static void main(String[] args) throws InterruptedException {
    Friend alphonse = new Friend("Alphonse");
    Friend gaston = new Friend("Gaston");

    new Thread(() -> alphonse.bow(gaston)).start();
    new Thread(() -> gaston.bow(alphonse)).start();

  }
}

