package com.pacific.data;

import android.annotation.TargetApi;

public final class IOError extends RuntimeException {
    public final int code;

    public IOError(int code) {
        this.code = code;
    }

    public IOError(String message, int code) {
        super(message);
        this.code = code;
    }

    public IOError(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public IOError(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    @TargetApi(24)
    public IOError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public static boolean isIOError(Throwable throwable) {
        return throwable instanceof IOError;
    }

    public static IOError from(Throwable throwable) {
        if (isIOError(throwable)) {
            return (IOError) throwable;
        }
        return new IOError(throwable.getMessage(), throwable.getCause(), -1);
    }
}
