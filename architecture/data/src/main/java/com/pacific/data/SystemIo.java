package com.pacific.data;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class SystemIo {

  private final static String TAG = "SystemIo";

  private SystemIo() {
    throw new UnsupportedOperationException();
  }

  @WorkerThread
  public static void readAsset(Context context, String name, String path, boolean overwrite) {
    DataUtil.verifyWorkThread();
    path = suffixDir(path);
    mkdirs(path);
    AssetManager assetManager = context.getAssets();
    try {
      final File file = new File(path + name);
      if (FileSystem.SYSTEM.exists(file)) {
        if (overwrite) {
          FileSystem.SYSTEM.delete(file);
        } else {
          return;
        }
      }
      file.createNewFile();
      BufferedSource source = Okio.buffer(Okio.source(assetManager.open(name)));
      BufferedSink sink = Okio.buffer(FileSystem.SYSTEM.sink(file));
      byte[] bytes = new byte[1024];
      int nRead;
      while ((nRead = source.read(bytes)) != -1) {
        sink.write(bytes, 0, nRead);
      }
      Util.closeQuietly(source);
      Util.closeQuietly(sink);
    } catch (IOException e) {
      Log.e(TAG, e.toString());
    }
  }

  @WorkerThread
  public static void toGallery(Context context, Bitmap bmp, File directory, String img) {
    DataUtil.verifyWorkThread();
    final String ext = ".jpg";
    if (!FileSystem.SYSTEM.exists(directory) || !directory.isDirectory()) {
      directory.mkdir();
    }
    if (TextUtils.isEmpty(img)) {
      img = System.currentTimeMillis() + ext;
    }
    if (!img.endsWith(ext)) {
      img = img + ext;
    }
    File file = new File(directory, img);
    try {
      if (FileSystem.SYSTEM.exists(file)) {
        FileSystem.SYSTEM.delete(file);
      }
      file.createNewFile();
      FileOutputStream fos = new FileOutputStream(file);
      bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
      fos.flush();
      Util.closeQuietly(fos);
    } catch (FileNotFoundException e) {
      Log.e(TAG, e.toString());
    } catch (IOException e) {
      Log.e(TAG, e.toString());
    }
    try {
      MediaStore
          .Images
          .Media
          .insertImage(context.getContentResolver(), file.getAbsolutePath(), img, null);
      context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    } catch (FileNotFoundException e) {
      Log.e(TAG, e.toString());
    }
  }

  @WorkerThread
  public static void unzip(File zipFile, String directory) {
    DataUtil.verifyWorkThread();
    try {
      ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
      BufferedSource source = Okio.buffer(Okio.source(zipInputStream));
      directory = suffixDir(directory);
      ZipEntry zipEntry;
      BufferedSink sink;
      while ((zipEntry = zipInputStream.getNextEntry()) != null) {
        if (zipEntry.isDirectory()) {
          mkdirs(directory + File.separator + zipEntry.getName());
        } else {
          sink = Okio.buffer(Okio.sink(new File(directory, zipEntry.getName())));
          byte[] bytes = new byte[1024];
          int nRead;
          while ((nRead = source.read(bytes)) != -1) {
            sink.write(bytes, 0, nRead);
          }
          Util.closeQuietly(sink);
          zipInputStream.closeEntry();
        }
      }
      Util.closeQuietly(source);
      Util.closeQuietly(zipInputStream);
    } catch (IOException e) {
      Log.e(TAG, e.toString());
    }
  }

  public static String getSDCard() {
    return suffixDir(Environment.getExternalStorageDirectory().getAbsolutePath());
  }

  @WorkerThread
  public static File mkdirs(String dir) {
    DataUtil.verifyWorkThread();
    File file = new File(dir);
    if (!file.exists() || !file.isDirectory()) {
      file.mkdirs();
    }
    return file;
  }

  public static String suffixDir(String dir) {
    if (dir.endsWith(File.separator)) {
      return dir;
    }
    return dir + File.separator;
  }
}

