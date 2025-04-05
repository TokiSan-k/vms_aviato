package com.aviato.Utils.concurrency;

import javafx.concurrent.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Worker {

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private Worker() {}

    public static <T> void submitTask(Task<T> task) {
        executor.submit(task);
    }

    //Clean Up
    public static void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}