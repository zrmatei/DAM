package com.example.finala.network;

public interface Callback<R> {
    void runOnUIThread(R result);
}
