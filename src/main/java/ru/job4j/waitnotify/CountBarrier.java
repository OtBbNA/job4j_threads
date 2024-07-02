package ru.job4j.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class CountBarrier {

    private final Object monitor = this;

    @GuardedBy("this")
    private final int total;

    @GuardedBy("this")
    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
            monitor.notifyAll();
            count++;
            System.out.println(count);
        }
    }

    public void await() {
        synchronized (monitor) {
            while (!(count >= total)) {
                try {
                    System.out.println("wait");
                    monitor.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
