package Integral;
import MapInterface.Main;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class IntegralCalculator {

    private static final int THRESHOLD = 10; // Порог для разделения задачи

    public static double calculateIntegral(double a, double b, int n, Function function) {
        // Получаем максимальное количество потоков
        int parallelism = ForkJoinPool.commonPool().getParallelism();
        // Создаем задачу и запускаем ее
        return new IntegralTask(a, b, n, function, parallelism).compute();
    }

    // Рекурсивная задача для вычисления интеграла
    private static class IntegralTask extends RecursiveTask<Double> {
        private double a;
        private double b;
        private int n;
        private Function function;
        private int parallelism;

        IntegralTask(double a, double b, int n, Function function, int parallelism) {
            this.a = a;
            this.b = b;
            this.n = n;
            this.function = function;
            this.parallelism = parallelism;
        }

        @Override
        protected Double compute() {
            // Если количество подзадач меньше порога, вычисляем интеграл напрямую
            if (n <= THRESHOLD) {
                return calculateIntegralSequential(a, b, n, function);
            } else {
                // Разделяем задачу на две подзадачи
                double mid = (a + b) / 2;
                IntegralTask leftTask = new IntegralTask(a, mid, n / 2, function, parallelism);
                IntegralTask rightTask = new IntegralTask(mid, b, n / 2, function, parallelism);
                // Запускаем подзадачи в разных потоках
                leftTask.fork();
                double rightResult = rightTask.compute();
                double leftResult = leftTask.join();
                // Объединяем результаты подзадач
                return leftResult + rightResult;
            }
        }
    }

    // Последовательное вычисление интеграла
    private static double calculateIntegralSequential(double a, double b, int n, Function function) {
        double h = (b - a) / n;
        double sum = 0;
        for (int i = 0; i < n; i++) {
            double x = a + i * h;
            sum += function.apply(x);
        }
        return h * sum;
    }

    // Интерфейс для функции
    public interface Function {
        double apply(double x);
    }
    public static void main(String[] args) {
        Function function = x -> Math.log((Math.sin(x * Math.PI))/(x + 3));
        double a = 0;
        double b = 1;
        int n = 10;

        double integral = IntegralCalculator.calculateIntegral(a, b, n, function);
        System.out.println("Интеграл: " + integral);
    }
}

