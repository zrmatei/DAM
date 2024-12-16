package com.example.finala.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public <R> void asyncTask(Callable<R>callable, Callback<R>callback){
        executorService.execute(() -> {
            try {
                R result = callable.call();
                handler.post(() -> {
                   callback.runOnUIThread(result);
                });
            } catch (Exception e) {
                Log.e("AsyncTaskRunner", "Async failed");
            }
        });
    }
}
