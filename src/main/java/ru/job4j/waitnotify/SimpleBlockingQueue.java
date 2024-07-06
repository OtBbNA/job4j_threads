package ru.job4j.waitnotify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private final Queue<T> queue = new LinkedList<>();

    @GuardedBy("this")
    private final int maxsize;

    public SimpleBlockingQueue(int maxsize) {
        this.maxsize = maxsize;
    }

    public synchronized void offer(T value) throws InterruptedException {
        while (queue.size() == maxsize) {
                System.out.println("offerwait");
                this.wait();
        }
        notifyAll();
        queue.add(value);
        System.out.println("off " + value);
    }

    public synchronized T poll() throws InterruptedException {
        while (queue.isEmpty()) {
            System.out.println("pollwait");
            this.wait();
        }
        T rsl = queue.poll();
        notifyAll();
        System.out.println("poll " + rsl);
        return rsl;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
