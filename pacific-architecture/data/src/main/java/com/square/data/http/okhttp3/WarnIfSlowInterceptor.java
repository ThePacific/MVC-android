package com.square.data.http.okhttp3;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

/**
 * @link https://gist.github.com/swankjesse/16389e5b57b04fd3ca28a798c1e90910
 */
public class WarnIfSlowInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startNanos = System.nanoTime();
        Response response = chain.proceed(request);
        long elapsedNanos = System.nanoTime() - startNanos;

        Invocation invocation = request.tag(Invocation.class);
        if (invocation == null) {
            return response;
        }

        WarnIfSlow warnIfSlow = invocation.method().getAnnotation(WarnIfSlow.class);
        if (warnIfSlow != null && elapsedNanos > warnIfSlow.timeUnit().toNanos(warnIfSlow.max())) {
            System.out.println(
                    String.format("SLOW METHOD WARNING: %s.%s %s HTTP %s (%.0f ms)%n",
                            invocation.method().getDeclaringClass().getSimpleName(),
                            invocation.method().getName(),
                            invocation.arguments(),
                            response.code(),
                            elapsedNanos / 1_000_000.0
                    )
            );
        }
        return response;
    }
}

