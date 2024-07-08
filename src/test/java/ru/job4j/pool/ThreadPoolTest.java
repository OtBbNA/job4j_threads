package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class ThreadPoolTest {

    @Test
    void ifAddFiveTasksThenBufferIs5() {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        ThreadPool tp = new ThreadPool();
        IntStream.range(0, 5).mapToObj(buffer::add).forEach((x) -> tp.work(() -> System.out.println(x)));
        assertThat(buffer.size()).isEqualTo(5);
    }

    @Test
    void ifAddFiveTasksAndShutdownThenInterruptException() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        ThreadPool tp = new ThreadPool();
        tp.shutdown();
        IntStream.range(0, 5).mapToObj(buffer::add).forEach((x) -> tp.work(() -> System.out.println(x)));
        assertThat(buffer.size()).isEqualTo(5);
    }
}