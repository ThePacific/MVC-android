package com.pacific.data;

import android.os.Looper;
import android.text.TextUtils;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import io.reactivex.android.MainThreadDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okio.ByteString;

public class DataUtil {

  private DataUtil() {
    throw new UnsupportedOperationException();
  }

  public static String md5(String source) {
    if (TextUtils.isEmpty(source)) {
      return source;
    }
    return ByteString.encodeUtf8(source).md5().hex();
  }

  @SuppressWarnings("unchecked")
  public static Map<String, String> toMap(Object obj, Moshi moshi) {
    if (moshi == null) {
      moshi = new Moshi.Builder().build();
    }
    JsonAdapter<Object> adapter = moshi.adapter(Object.class);
    Object jsonStructure = adapter.toJsonValue(obj);
    return (Map<String, String>) jsonStructure;
  }

  public static String toJson(Object obj, Moshi moshi, Type type) {
    if (moshi == null) {
      moshi = new Moshi.Builder().build();
    }
    return moshi.adapter(type).lenient().toJson(obj);
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromJson(String json, Moshi moshi, Type type) throws IOException {
    if (moshi == null) {
      moshi = new Moshi.Builder().build();
    }
    return (T) moshi.adapter(type).lenient().fromJson(json);
  }

  public static byte[] toJsonByteArray(Object obj, Moshi moshi, Type type) {
    return string2ByteArray(toJson(obj, moshi, type));
  }

  @SuppressWarnings("unchecked")
  public static <T> T fromJsonByteArray(byte[] jsonByteArray, Moshi moshi, Type type)
      throws IOException {
    return fromJson(byteArray2String(jsonByteArray), moshi, type);
  }

  public static byte[] string2ByteArray(String value) {
    return ByteString.encodeUtf8(value).toByteArray();
  }

  public static String byteArray2String(byte[] bytes) {
    return ByteString.of(bytes, 0, bytes.length).utf8();
  }

  public static boolean isAccessFailure(IOError ioError) {
    return ioError.code == 403 || ioError.code == 405;
  }

  public static boolean isMainThread() {
    try {
      return Looper.myLooper() == Looper.getMainLooper();
    } catch (Exception e) {
      return true;// Cover for tests
    }
  }

  public static void verifyMainThread() {
    if (BuildConfig.DEBUG) {
      return;// Cover for tests
    }
    MainThreadDisposable.verifyMainThread();
  }

  public static void verifyWorkThread() {
    if (BuildConfig.DEBUG) {
      return;// Cover for tests
    }
    if (isMainThread()) {
      throw new UnsupportedOperationException("Can't run in Main thread");
    }
  }

  public static void postToMainThread(Runnable runnable) {
    AndroidSchedulers.mainThread().scheduleDirect(runnable);
  }

  public void cancelRequest(OkHttpClient client, Object tag) {
    for (Call call : client.dispatcher().queuedCalls()) {
      if (tag.equals(call.request().tag())) {
        call.cancel();
      }
    }
    for (Call call : client.dispatcher().runningCalls()) {
      if (tag.equals(call.request().tag())) {
        call.cancel();
      }
    }
  }
}
