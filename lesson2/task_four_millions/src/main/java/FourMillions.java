package ru.philosophyit;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class FourMillions {


  /**
   * Класс-счётчик.
   */
  static class Counter {

    /**
     * Буфер счёта
     */

    //private long count = 0;
    private AtomicLong count;

    public Counter() {
      count = new AtomicLong(0);
    }


    /**
     * Чтобы произвести операцию инкремента переменной каждый поток сначала копирует
     * значение переменной в свой локальный кэш, затем инкрементирует и копирует
     * полученное значение обратно в общий стек.
     *
     * Получается, что какой-то поток вытаскивает значение переменной count
     * (пускай оно равно на тот момент 10)
     * Пока первый поток производит операцию инкремента, второй поток также вытаскивает значение
     * переменной count, которое пока равно 10. После инкрементации первый поток положит в значение
     * count 11, а затем и второй поток положит 11. Другими словами так как переменную используют сразу
     * несколько потоков одновременно, то данные в ней будут постоянно перезатираться.
     *
     * Решение 1: обозначить переменную count как атомарную.
     * Решение 2: обозначить метод increment() синхронизированным
     */

    /**
     * Считаем +1 Вариант 1. Когда count неАтомарна
     */
//    public synchronized void increment(){
//      count++;
//    }

    /**
     * Считаем +1. Вариант 2. Когда count атомарна
     */
    public void increment() {
      count.getAndIncrement();
    }

    /**
     * Получить текущее значение счётчика
     */
    public long getCount() {
      return count.get();
//      return count;
    }
  }

  private final static int N_THREADS = 4;

  /**
   * Точка входа в программу
   *
   * @param args арг-ты командной строки
   */
  public static void main(String[] args) {
    Counter counter = new Counter();

    ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);

    // создаём java.Util.Stream для интов от 0 до 4 (искл.)
    // * не путать Stream и Thread
    CompletableFuture<?>[] futures = IntStream.range(0, N_THREADS)
        // вместо каждой цифры запускаем инкременты счётчика
        .mapToObj(ignored -> runCounting(counter, executorService))
        // собираем CompletableFuture'ы в массив
        .toArray(CompletableFuture[]::new);

    // когда все потоки завершат свою работу
    CompletableFuture.allOf(futures).thenRun(() -> {
      // имеем шанс не получить 4 млн
      System.out.println("Total count: " + counter.getCount());
      executorService.shutdown();
    });
  }

  /**
   * Запускает миллион инкрементов счётчика в отдельном потоке
   *
   * @param counter         счётчик для инкрементов
   * @param executorService пул потоков для работы
   *
   * @return CompletableFuture без результата, разрешаемый после завершения инкрементаций
   */
  private static CompletableFuture<?> runCounting(Counter counter, ExecutorService executorService) {
    return CompletableFuture.runAsync(
        () -> {
          for (int j = 0; j < 1000000; j++) {
            counter.increment();
          }
        },
        executorService
    );
  }
}

