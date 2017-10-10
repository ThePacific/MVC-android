package com.thepacific.data.common;

import android.os.Looper;
import com.thepacific.data.http.IoError;
import com.thepacific.guava.MapString;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import okio.ByteString;

public class DataUtil {

  private DataUtil() {
    throw new UnsupportedOperationException();
  }

  public static String toJson(Object value, Gson gson) {
    if (gson == null) {
      gson = new Gson();
    }
    return gson.toJson(value);
  }

  public static <T> T fromJson(String json, Gson gson, Class<T> tClass) {
    if (gson == null) {
      gson = new Gson();
    }
    return gson.fromJson(json, tClass);
  }

  public static <T> List<T> fromJsonToList(String json, Gson gson) {
    if (gson == null) {
      gson = new Gson();
    }
    Type typeOfT = new TypeToken<List<T>>() {
    }.getType();
    return gson.fromJson(json, typeOfT);
  }

  public static <T> List<T> fromJsonByteArrayToList(byte[] jsonByteArray, Gson gson) {
    return fromJsonToList(byteArray2String(jsonByteArray), gson);
  }

  public static byte[] toJsonByteArray(Object value, Gson gson) {
    return string2ByteArray(toJson(value, gson));
  }

  public static <T> T fromJsonByteArray(byte[] jsonByteArray, Gson gson, Class<T> tClass) {
    return fromJson(byteArray2String(jsonByteArray), gson, tClass);
  }

  public static byte[] string2ByteArray(String value) {
    return ByteString.encodeUtf8(value).toByteArray();
  }

  public static String byteArray2String(byte[] bytes) {
    return ByteString.of(bytes, 0, bytes.length).utf8();
  }

  public static Map<String, String> toMap(Object obj, Gson gson) {
    if (gson == null) {
      gson = new Gson();
    }
    String json = gson.toJson(obj);
    Map<String, String> map = gson.fromJson(json, new TypeToken<Map<String, String>>() {
    }.getType());
    return map;
  }

  public static boolean isAccessFailure(IoError ioError) {
    return ioError.code == 403 || ioError.code == 405;
  }

  public static boolean isMainThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  public static void requireMainThread() {
    MainThreadDisposable.verifyMainThread();
  }

  public static void requireWorkThread() {
    if (isMainThread()) {
      throw new UnsupportedOperationException("Can't run in Main thread");
    }
  }

  public static void postToMainThread(Runnable runnable) {
    AndroidSchedulers.mainThread().scheduleDirect(runnable);
  }



  public static boolean andPattern(String source, int minLength, int maxLength) {
    Preconditions.checkNotNull(source);
    Preconditions.checkState(minLength > 0 && maxLength > 0 && maxLength > minLength);
    String regex = MapString
        .format("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{<minLength>,<maxLength>}",
            "<",
            ">")
        .with("minLength", String.valueOf(minLength))
        .with("maxLength", String.valueOf(maxLength))
        .build();
    return source.matches(regex);
  }

  public static boolean orPattern(String source, int minLength, int maxLength) {
    Preconditions.checkNotNull(source);
    Preconditions.checkState(minLength > 0 && maxLength > 0 && maxLength > minLength);
    String regex = MapString
        .format("[0-9A-Za-z]{<minLength>,<maxLength>}",
            "<",
            ">")
        .with("minLength", String.valueOf(minLength))
        .with("maxLength", String.valueOf(maxLength))
        .build();
    return source.matches(regex);
  }

  public static String md5(String source) {
    Preconditions.checkNotNull(source);
    return ByteString.encodeUtf8(source).md5().hex();
  }
}
