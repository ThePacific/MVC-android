package com.thepacific.data.http;

public final class Source<T> {

  public final Status status;
  public final Throwable error;
  public final T data;

  private Source(Status status, Throwable error, T data) {
    this.status = status;
    this.error = error;
    this.data = data;
  }

  public static <T> Source<T> inProgress() {
    return new Source<>(Status.IN_PROGRESS, null, null);
  }

  public static <T> Source<T> success(T data) {
    return new Source<>(Status.SUCCESS, null, data);
  }

  public static <T> Source<T> failure(Throwable error) {
    return new Source<>(Status.ERROR, error, null);
  }

  public static <T> Source<T> irrelevant() {
    return new Source<>(Status.IRRELEVANT, null, null);
  }
}