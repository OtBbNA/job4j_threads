package ru.job4j.net;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String fileName;
    private final int speed;

    public Wget(String url, String fileName, int speed) {
        this.url = url;
        this.fileName = fileName;
        this.speed = speed;
    }

    @Override
    public void run() {
        try {
            var file = new File(fileName);
            try (var input = new URL(url + fileName).openStream();
                 var output = new FileOutputStream(file)) {
                long counter = 0;
                int bytesRead;
                var startAt = System.nanoTime();
                while ((bytesRead = input.read()) != -1) {
                    counter += bytesRead;
                    if (speed < 6000 && counter >= speed) {
                        double interval = (double) (System.nanoTime() - startAt) / 1000000;
                        startAt = System.nanoTime();
                        if (interval < 1000) {
                            long pause = (long) (counter / interval) / 1000;
                            System.out.println("Read " + counter + " bytes : " + interval + " ms | pause : " + pause);
                            Thread.sleep(pause);
                            startAt = System.nanoTime();
                            counter = 0;
                        }
                    }
                    output.write(bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void validate(String url, String fileName, int speed) {
        if (url.isBlank()) {
            throw new IllegalArgumentException("empty URL");
        }
        if (!url.contains("https://") && !url.contains("http://")) {
            throw new IllegalArgumentException("wrong URL");
        }
        if (speed < 0) {
            throw new IllegalArgumentException("speed limit are illegal");
        }
        if (fileName.length() < 2) {
            throw new IllegalArgumentException("wrong name of file");
        }
        if (!fileName.contains(".")) {
            throw new IllegalArgumentException("wrong name of file");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length > 2) {
            String url = args[0];
            String fileName = args[1];
            int speed = Integer.parseInt(args[2]);
            validate(url, fileName, speed);
            Thread wget = new Thread(new Wget(url, fileName, speed));
            wget.start();
            wget.join();
        }
    }
}
