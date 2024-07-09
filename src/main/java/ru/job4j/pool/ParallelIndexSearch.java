package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {

    int left;
    int right;
    T[] list;
    T value;

    public ParallelIndexSearch(int left, int right, T[] list, T value) {
        this.left = left;
        this.right = right;
        this.list = list;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        int rsl = -1;
        if (right - left <= 10) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].equals(value)) {
                    return i;
                }
            }
            return rsl;
        }
        int middle = (left + right) / 2;
        ParallelIndexSearch<T> searchIndex1 = new ParallelIndexSearch<>(left, middle, list, value);
        ParallelIndexSearch<T> searchIndex2 = new ParallelIndexSearch<>(middle + 1, right, list, value);
        searchIndex1.fork();
        searchIndex2.fork();
        int index1 = searchIndex1.join();
        int index2 = searchIndex2.join();
        if (index1 > 0 && list[index1].equals(value)) {
            return index1;
        } else if (index2 > 0 && list[index2].equals(value)) {
            return index2;
        }
        return Math.min(index1, index2);
    }

    public static <T> int findIndex(int left, int right, T[] list, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelIndexSearch<>(left, right, list, value));
    }
}
