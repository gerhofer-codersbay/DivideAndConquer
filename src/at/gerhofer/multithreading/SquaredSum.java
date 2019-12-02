package at.gerhofer.multithreading;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class SquaredSum extends RecursiveTask<Integer> {

    private int[] arr;
    private static final int THRESHOLD = 10;

    public SquaredSum(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length > THRESHOLD) {
            Collection<SquaredSum> squaredSums = ForkJoinTask.invokeAll(createSubtasks());
            return squaredSums
                    .stream()
                    .mapToInt(ForkJoinTask::join) // task to int
                    .sum();
        } else {
            return process(arr);
        }
    }

    private Collection<SquaredSum> createSubtasks() {
        System.out.println(Thread.currentThread() + ": too much work I'm splitting this array with size " + arr.length);
        List<SquaredSum> dividedTasks = new ArrayList<>();
        dividedTasks.add(new SquaredSum(Arrays.copyOfRange(arr, 0, arr.length / 2)));
        dividedTasks.add(new SquaredSum(Arrays.copyOfRange(arr, arr.length / 2, arr.length)));
        return dividedTasks;
    }

    private Integer process(int[] arr) {
        System.out.println(Thread.currentThread() + ": okay, I'm processing this array with size " + arr.length);
        return Arrays.stream(arr)
                .map(a -> a * a)
                .sum();
    }

}
