package at.gerhofer.multithreading;

import java.util.concurrent.ForkJoinPool;

public class Main {

    public static void main(String[] args) {
        int[] numbers = new int[100];
        for (int i = 0; i < 100; i++) {
            numbers[i] = i + 1;
        }

        Integer sum = ForkJoinPool.commonPool().invoke(new SquaredSum(numbers));
        System.out.println("the total Sum of the Squares is: " + sum);

    }

}
