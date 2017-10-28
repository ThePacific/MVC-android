package com.pacific.data.platform;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import com.pacific.data.common.DataUtil;
import java.util.Map;
import java.util.Set;
import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Immutable
public final class PrefsIo {

  private final SharedPreferences sharedPrefs;

  @Inject
  public PrefsIo(Application app) {
    this.sharedPrefs = app.getSharedPreferences("os_prefs", Context.MODE_PRIVATE);
  }

  public PrefsIo putString(String key, String value) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putString(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public PrefsIo putInt(String key, Integer value) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putInt(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public PrefsIo putBoolean(String key, boolean value) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putBoolean(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public PrefsIo putFloat(String key, Float value) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putFloat(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public PrefsIo putLong(String key, Long value) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putLong(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public PrefsIo putObject(String key, Object value) {
    putString(key, DataUtil.toJson(value, null));
    return this;
  }

  public PrefsIo putStringSet(String key, Set<String> value) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.putStringSet(key, value);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public String getString(String key, String defaultValue) {
    return sharedPrefs.getString(key, defaultValue);
  }

  public int getInt(String key, Integer defaultValue) {
    return sharedPrefs.getInt(key, defaultValue);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    return sharedPrefs.getBoolean(key, defaultValue);
  }

  public float getFloat(String key, Float defaultValue) {
    return sharedPrefs.getFloat(key, defaultValue);
  }

  public long getLong(String key, Long defaultValue) {
    return sharedPrefs.getLong(key, defaultValue);
  }

  public <T> T getObject(String key, Class<T> tClass) {
    if (sharedPrefs.contains(key)) {
      return DataUtil.fromJson(sharedPrefs.getString(key, ""), null, tClass);
    } else {
      return null;
    }
  }

  public Set<String> getStringSet(String key, Set<String> defaultValue) {
    return sharedPrefs.getStringSet(key, defaultValue);
  }

  public PrefsIo remove(String key) {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.remove(key);
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public PrefsIo clear() {
    SharedPreferences.Editor editor = sharedPrefs.edit();
    editor.clear();
    SharedPreferencesCompat.EditorCompat.getInstance().apply(editor);
    return this;
  }

  public boolean contains(String key) {
    return sharedPrefs.contains(key);
  }

  public Map<String, ?> getAll() {
    return sharedPrefs.getAll();
  }
}
