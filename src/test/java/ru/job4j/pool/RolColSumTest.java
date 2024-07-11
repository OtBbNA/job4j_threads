package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void asyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][]{{1, 7, 2}, {8, 1, 3}, {0, 2, 4}};
        Sums[] expected = new Sums[]{
                new Sums(10, 9),
                new Sums(12, 10),
                new Sums(6, 9)
        };
        long timeMillis = System.currentTimeMillis();
        Sums[] rsl = RolColSum.asyncSum(matrix);
        System.out.println(System.currentTimeMillis() - timeMillis);
        assertThat(rsl).isEqualTo(expected);
    }

    @Test
    void mathSum() {
        int[][] matrix = new int[][]{{1, 7, 2}, {8, 1, 3}, {0, 2, 4}};
        Sums[] expected = new Sums[]{
                new Sums(10, 9),
                new Sums(12, 10),
                new Sums(6, 9)
        };
        long timeMillis = System.currentTimeMillis();
        Sums[] rsl = RolColSum.sum(matrix);
        System.out.println(System.currentTimeMillis() - timeMillis);
        assertThat(rsl).isEqualTo(expected);
    }
}