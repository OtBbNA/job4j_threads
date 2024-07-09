package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class EmailNotification {

    private ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public synchronized void emailTo(User user) {
        pool.submit(() -> {
                send(user.username(),
                        String.format("subject = Notification %s, to email %s. %nbody = Add a new event to %s%n",
                                user.username(), user.email(), user.username()),
                        user.email());
                }
        );
    }

    public synchronized void close() {
        pool.shutdown();
    }

    public void send(String subject, String body, String email) {
        System.out.println(body);
    }
}
