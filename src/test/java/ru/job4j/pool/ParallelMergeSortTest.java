package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class ParallelMergeSortTest {

    @Test
    void compute() {
        ParallelMergeSort pms = new ParallelMergeSort(IntStream.range(0, 100).toArray(), 0, 99);
        pms.compute();
    }
}