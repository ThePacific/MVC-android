package com.pacific.data.platform;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.pacific.data.common.DataUtil;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class SystemIo {

  private SystemIo() {
    throw new UnsupportedOperationException();
  }

  @WorkerThread
  public static void readAsset(Context context, String name, boolean overwrite) {
    DataUtil.requireWorkThread();
    AssetManager assetManager = context.getAssets();
    try {
      final File file = new File(context.getFilesDir() + File.separator + name);
      if (FileSystem.SYSTEM.exists(file)) {
        if (overwrite) {
          FileSystem.SYSTEM.delete(file);
        } else {
          return;
        }
      }
      BufferedSource source = Okio.buffer(Okio.source(assetManager.open(name)));
      file.createNewFile();
      BufferedSink sink = Okio.buffer(FileSystem.SYSTEM.sink(file));
      byte[] bytes = new byte[1024];
      int nRead;
      while ((nRead = source.read(bytes)) != -1) {
        sink.write(bytes, 0, nRead);
      }
      Util.closeQuietly(source);
      Util.closeQuietly(sink);
    } catch (IOException e) {
      Log.e("readAsset()", e.toString());
    }
  }

  @WorkerThread
  public static void toGallery(Context context, Bitmap bmp, File directory, String img) {
    DataUtil.requireWorkThread();
    final String tag = "SystemIo.toGallery()";
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
      Log.e(tag, e.toString());
    } catch (IOException e) {
      Log.e(tag, e.toString());
    }
    try {
      MediaStore
          .Images
          .Media
          .insertImage(context.getContentResolver(), file.getAbsolutePath(), img, null);
      context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    } catch (FileNotFoundException e) {
      Log.e(tag, e.toString());
    }
  }
}

