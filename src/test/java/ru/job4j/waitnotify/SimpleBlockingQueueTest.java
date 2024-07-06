package ru.job4j.waitnotify;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    void offer() throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueue = new SimpleBlockingQueue<>(10);
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 25; i++) {
                try {
                    simpleBlockingQueue.offer(i);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 24; i++) {
                try {
                    simpleBlockingQueue.poll();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        producer.start();
        consumer.join();
        producer.join();
        assertThat(simpleBlockingQueue.poll()).isEqualTo(24);
    }
}