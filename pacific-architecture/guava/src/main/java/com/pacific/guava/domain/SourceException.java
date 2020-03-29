package com.pacific.guava.domain;

public class SourceException extends RuntimeException {

    public final int code;

    public SourceException(int code) {
        super();
        this.code = code;
    }

    public SourceException(String s, int code) {
        super(s);
        this.code = code;
    }

    public SourceException(String s, Throwable throwable, int code) {
        super(s, throwable);
        this.code = code;
    }

    public SourceException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
