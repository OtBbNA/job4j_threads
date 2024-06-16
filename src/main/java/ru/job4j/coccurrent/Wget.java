package ru.job4j.coccurrent;

public class Wget {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start loading ... ");
        for (int index = 0; index < 100; index++) {
            Thread.sleep(1000);
            System.out.println("Loading : " + index  + "%");
        }
        System.out.println("Loaded.");
    }
}
