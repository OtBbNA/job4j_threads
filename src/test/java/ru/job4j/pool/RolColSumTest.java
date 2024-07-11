package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {

    @Test
    void asyncSum() throws ExecutionException, InterruptedException {
        long timeMillis = System.currentTimeMillis();
        int[][] matrix = new int[][]{{1, 7, 2}, {8, 1, 3}, {0, 2, 4}};
        int[] expected = new int[]{10, 9, 12, 10, 6, 9};
        RolColSum.Sums[] rsl = RolColSum.asyncSum(matrix);
        for (int i = 0; i < rsl.length; i++) {
                assertThat(rsl[i].getRowSum()).isEqualTo(expected[i * 2]);
                assertThat(rsl[i].getColSum()).isEqualTo(expected[i * 2 + 1]);
        }
        System.out.println(System.currentTimeMillis() - timeMillis);
    }

    @Test
    void mathSum() {
        long timeMillis = System.currentTimeMillis();
        int[][] matrix = new int[][]{{1, 7, 2}, {8, 1, 3}, {0, 2, 4}};
        int[] expected = new int[]{10, 9, 12, 10, 6, 9};
        RolColSum.Sums[] rsl = RolColSum.sum(matrix);
        for (int i = 0; i < rsl.length; i++) {
            assertThat(rsl[i].getRowSum()).isEqualTo(expected[i * 2]);
            assertThat(rsl[i].getColSum()).isEqualTo(expected[i * 2 + 1]);
        }
        System.out.println(System.currentTimeMillis() - timeMillis);
    }
}