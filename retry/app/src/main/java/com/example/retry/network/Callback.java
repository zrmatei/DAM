package com.example.retry.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
