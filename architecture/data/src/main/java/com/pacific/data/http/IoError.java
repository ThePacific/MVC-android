package com.pacific.data.http;

import android.annotation.TargetApi;
import javax.annotation.concurrent.Immutable;

@Immutable
public final class IoError extends RuntimeException {

  public final int code;

  public IoError(int code) {
    this.code = code;
  }

  public IoError(String message, int code) {
    super(message);
    this.code = code;
  }

  public IoError(String message, Throwable cause, int code) {
    super(message, cause);
    this.code = code;
  }

  public IoError(Throwable cause, int code) {
    super(cause);
    this.code = code;
  }

  @TargetApi(24)
  public IoError(String message, Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace,
      int code) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }

  public static boolean isIoError(Throwable throwable) {
    return throwable instanceof IoError;
  }

  public static IoError from(Throwable throwable) {
    if (isIoError(throwable)) {
      return (IoError) throwable;
    }
    return new IoError(throwable.getMessage(), throwable.getCause(), -1);
  }
}
