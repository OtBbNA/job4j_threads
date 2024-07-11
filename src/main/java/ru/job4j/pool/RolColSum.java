package ru.job4j.pool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.UnaryOperator;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(sumMath(matrix, i, 0, (row) -> row, (col) -> col + 1));
            sums[i].setColSum(sumMath(matrix, 0, i, (row) -> row + 1, (col) -> col));
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
            sums[i].setRowSum(asyncMath(matrix, i, 0, (row) -> row, (col) -> col + 1).get());
            sums[i].setColSum(asyncMath(matrix, 0, i, (row) -> row + 1, (col) -> col).get());
        }
        return sums;
    }

    private static CompletableFuture<Integer> asyncMath(int[][] data, int row, int col, UnaryOperator<Integer> rowChange, UnaryOperator<Integer> colChange) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            int rowFor = row;
            int colFor = col;
            for (int i = 0; i < data.length; i++) {
                if (i != 0) {
                    rowFor = rowChange.apply(rowFor);
                    colFor = colChange.apply(colFor);
                }
                sum += data[rowFor][colFor];
            }
            return sum;
        });
    }

    private static int sumMath(int[][] data, int row, int col, UnaryOperator<Integer> rowChange, UnaryOperator<Integer> colChange) {
        int sum = 0;
        int rowFor = row;
        int colFor = col;
        for (int i = 0; i < data.length; i++) {
            if (i != 0) {
                rowFor = rowChange.apply(rowFor);
                colFor = colChange.apply(colFor);
            }
            sum += data[rowFor][colFor];
        }
        return sum;
    }

    public static class Sums {
        private int rowSum;
        private int colSum;

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }
    }
}
