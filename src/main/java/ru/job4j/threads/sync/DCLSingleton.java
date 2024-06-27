package ru.job4j.threads.sync;

public final class DCLSingleton {

    private static volatile DCLSingleton instance;

    public static DCLSingleton getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new DCLSingleton();
            }
        }
        return instance;
    }

    private DCLSingleton() {
    }
}
