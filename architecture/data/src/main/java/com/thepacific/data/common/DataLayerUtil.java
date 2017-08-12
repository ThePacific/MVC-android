package com.thepacific.data.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import okio.ByteString;

public class DataLayerUtil {

  private DataLayerUtil() {
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

  public static long elapsedTimeMillis(int minutes) {
    return 60 * 1000 * minutes + System.currentTimeMillis();
  }
}
