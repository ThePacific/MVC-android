package com.pacific.app.architecture.store.http.okhttp3;

import com.pacific.app.architecture.store.http.entities.ApiRequest;
import com.pacific.app.architecture.store.http.entities.ApiResponse;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ApiConverterFactory extends Converter.Factory {

    public static ApiConverterFactory create() {
        return new ApiConverterFactory();
    }

    private ApiConverterFactory() {
        super();
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public Converter<ResponseBody, ?> responseBodyConverter(
            Type type,
            Annotation[] annotations,
            Retrofit retrofit
    ) {
        Converter<ResponseBody, ApiResponse<?>> delegate = retrofit.nextResponseBodyConverter(
                this,
                Types.newParameterizedType(ApiResponse.class, type),
                annotations
        );
        return new Converter<ResponseBody, Object>() {
            @Nullable
            @Override
            public Object convert(ResponseBody value) throws IOException {
                return delegate.convert(value).getData();
            }
        };
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public Converter<?, RequestBody> requestBodyConverter(
            Type type,
            Annotation[] parameterAnnotations,
            Annotation[] methodAnnotations,
            Retrofit retrofit
    ) {
        if (true) {
            return super.requestBodyConverter(
                    type,
                    parameterAnnotations,
                    methodAnnotations,
                    retrofit
            );
        }
        Converter<ApiRequest<?>, RequestBody> delegate = retrofit.nextRequestBodyConverter(
                this,
                Types.newParameterizedType(ApiRequest.class, type),
                parameterAnnotations,
                methodAnnotations
        );
        return new Converter<Object, RequestBody>() {
            @Nullable
            @Override
            public RequestBody convert(Object value) throws IOException {
                return delegate.convert(new ApiRequest(value));
            }
        };
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public Converter<?, String> stringConverter(
            Type type,
            Annotation[] annotations,
            Retrofit retrofit
    ) {
        return super.stringConverter(type, annotations, retrofit);
    }
}
