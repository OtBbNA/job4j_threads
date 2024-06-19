package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            var file = new File("tmp.xml");
            try (var input = new URL(url).openStream();
            var output = new FileOutputStream(file)) {
                var dataBuffer = new byte[512];
                int bytesRead;
                while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    var downloadAt = System.nanoTime();
                    output.write(dataBuffer, 0, bytesRead);
                    long millis = 512000000 / (System.nanoTime() - downloadAt);
                    if (millis > speed && speed < 6000) {
                        Thread.sleep(millis / speed);
                        System.out.println("Read 512 bytes : " + millis + " ms. Sleep time " + millis / speed + " ms");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String url, int speed) {
        if (url.isBlank()) {
            throw new IllegalArgumentException("empty URL");
        }
        if (!url.contains("https://") && !url.contains("http://")) {
            throw new IllegalArgumentException("wrong URL");
        }
        if (speed < 0) {
            throw new IllegalArgumentException("speed limit are illegal");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        validate(url, speed);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
