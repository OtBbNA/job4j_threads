package ru.job4j.waitnotify;

public class CountBarrierUser {

    public static void main(String[] args) {
        CountBarrier barrier = new CountBarrier(5);
        Thread main = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    for (int i = 0; i < 10; i++) {
                        barrier.count();
                    }
                }
        );
        Thread await = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    barrier.await();
                }
        );
        main.start();
        await.start();
    }
}
