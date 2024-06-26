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
            try (var input = new URL(url).openStream();
                 var output = new FileOutputStream(file)) {
                var dataBuffer = new byte[128];
                StringBuilder rslOutput = new StringBuilder();
                int bytesRead;
                var downloadAt = System.nanoTime();
                while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                    var interval = System.nanoTime() - downloadAt;
                    var downloadSpeed = (128000000 / interval);
                    rslOutput.append("Read 128 bytes : " + interval + " nano. Speed : " + downloadSpeed + " b/ms.");
                    if (downloadSpeed > speed && speed < 6000) {
                        Thread.sleep((downloadSpeed / speed));
                        rslOutput.append("Sleep : " + (downloadSpeed / speed) + " ms");
                    }
                    System.out.println(rslOutput);
                    rslOutput.delete(0, rslOutput.length());
                    output.write(dataBuffer, 0, bytesRead);
                    downloadAt = System.nanoTime();
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
