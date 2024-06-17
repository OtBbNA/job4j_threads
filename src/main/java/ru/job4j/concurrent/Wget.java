package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        System.out.println("Start loading ... ");
        for (int index = 0; index < 100; index++) {
            try {
                Thread.sleep(1000);
                System.out.println("Loading : " + index  + "%");
            } catch (InterruptedException e) {
                    e.printStackTrace();
            }
        }
        System.out.println("Loaded.");
    }
}
