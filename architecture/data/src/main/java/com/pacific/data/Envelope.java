package com.pacific.data;

public interface Envelope<T> {
    boolean isSuccess();

    int code();

    String message();

    T data();
}