package com.pacific.data.platform;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import okhttp3.internal.Util;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

public class Decompress {

  private File _zipFile;
  private static final String ROOT_LOCATION = "sdcard";

  public Decompress(File zipFile) {
    _zipFile = zipFile;
  }

  public void unzip() throws IOException, DataFormatException {
    System.out.println("Starting to unzip");
    InputStream fin = new FileInputStream(_zipFile);
    ZipInputStream zin = new ZipInputStream(fin);
    BufferedSource source = Okio.buffer(Okio.source(zin));

    ZipEntry ze;
    BufferedSink sink;
    while ((ze = zin.getNextEntry()) != null) {
      if (ze.isDirectory()) {
        String dir = ROOT_LOCATION + "/" + ze.getName();
        File f = new File(dir);
        if (dir.length() >= 0 && !f.isDirectory()) {
          f.mkdirs();
        }
      } else {
        sink = Okio.buffer(Okio.sink(new File(ROOT_LOCATION, ze.getName())));
        byte[] bytes = new byte[1024];
        int nRead;
        while ((nRead = source.read(bytes)) != -1) {
          sink.write(bytes, 0, nRead);
        }
        Util.closeQuietly(sink);
        zin.closeEntry();
      }
    }
    Util.closeQuietly(source);
    Util.closeQuietly(zin);
    System.out.println("Finished unzip");
  }

}
