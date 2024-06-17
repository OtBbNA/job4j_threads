package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        try {
            Thread.sleep(5000);
            progress.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        var process = new char[] {'-', '\\', '|', '/'};
        byte i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(250);
                System.out.print("\r load: " + process[i]);
                i++;
                if (i > 3) {
                    i = 0;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
