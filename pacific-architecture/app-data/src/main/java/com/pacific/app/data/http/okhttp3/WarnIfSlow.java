package com.pacific.app.data.http.okhttp3;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @link https://gist.github.com/swankjesse/16389e5b57b04fd3ca28a798c1e90910
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface WarnIfSlow {
    long max();

    TimeUnit timeUnit();
}
