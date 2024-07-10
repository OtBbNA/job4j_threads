package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    private final int left;
    private final int right;
    private final T[] list;
    private final T value;

    public ParallelIndexSearch(int left, int right, T[] list, T value) {
        this.left = left;
        this.right = right;
        this.list = list;
        this.value = value;
    }

    private int linearSearch() {
        int rsl = -1;
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(value)) {
                return i;
            }
        }
        return rsl;
    }

    @Override
    protected Integer compute() {
        if (right - left <= 10) {
            return linearSearch();
        }
        int middle = (left + right) / 2;
        ParallelIndexSearch<T> searchIndex1 = new ParallelIndexSearch<>(left, middle, list, value);
        ParallelIndexSearch<T> searchIndex2 = new ParallelIndexSearch<>(middle + 1, right, list, value);
        searchIndex1.fork();
        searchIndex2.fork();
        return Math.max(searchIndex1.join(), searchIndex2.join());
    }

    public static <T> int findIndex(int left, int right, T[] list, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(left, right, list, value));
    }
}
