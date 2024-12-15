package com.example.retry.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.retry.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncTaskRun {
    //pt pornire fire paralele
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    //pt impingere rezultate procesarii paralele
    private final Handler handler = new Handler(Looper.getMainLooper());

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback){
        //fir secundar
        executorService.execute(() -> {
            try {
                R result = callable.call();
                handler.post(() -> {
                    //fir principal
                    callback.runOnUIThread(result);
                });
            } catch (Exception e) {
                Log.e("AsyncTaskRun", "Async failed");
            }
        });
    }
}
