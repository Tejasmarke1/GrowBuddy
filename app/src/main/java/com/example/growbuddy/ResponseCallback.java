package com.example.growbuddy;

public interface ResponseCallback {
    void onResponse(String response);
    void onError(Throwable throwable);
}
