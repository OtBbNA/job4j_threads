package ru.job4j.nba;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref, count.incrementAndGet()));
    }

    public int get() {
        return count.get();
    }
}
