package com.pacific.data.http;

public interface Envelope<T> {

  boolean isSuccess();

  int code();

  String message();

  T data();
}
