package ru.job4j.nba;

import org.junit.jupiter.api.Test;
import java.util.concurrent.atomic.AtomicInteger;
import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    void whenTwoThreadIncrement50ThenGet100() throws InterruptedException {
        AtomicInteger buffer = new AtomicInteger();
        CASCount casCount = new CASCount();
        Thread increment0 = new Thread(
                () -> {
                    for (int i = 0; i < 50; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread increment1 = new Thread(
                () -> {
                    for (int i = 0; i < 50; i++) {
                        casCount.increment();
                    }
                }
        );
        Thread get = new Thread(
                () -> {
                        buffer.set(casCount.get());
                }
        );
        increment0.start();
        increment1.start();
        increment0.join();
        increment1.join();
        get.start();
        get.join();
        assertThat(buffer.get()).isEqualTo(100);
    }
}