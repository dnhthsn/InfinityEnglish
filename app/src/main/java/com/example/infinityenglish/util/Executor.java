package com.example.infinityenglish.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    private static ExecutorService executorService;

    public static synchronized ExecutorService getInstance(){
        if (executorService == null){
            executorService = Executors.newFixedThreadPool(20);
        } else {
            return executorService;
        }

        return executorService;
    }
}
