package com.thepacific.data;

import com.thepacific.data.cache.DiskCache;
import com.thepacific.data.cache.MemoryCache;
import com.thepacific.data.http.Source;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import okhttp3.internal.io.FileSystem;
import okio.ByteString;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

  @Test
  public void testEntry() {
    String value = "Don't do that !!!";
    byte[] array = ByteString.encodeUtf8(value).toByteArray();
    assertEquals(value, ByteString.of(array, 0, array.length).utf8());
  }

  @Test
  public void testDiskCache() {
    File cacheDir = new File("http");
    try {
      if (FileSystem.SYSTEM.exists(cacheDir)) {
        if (!cacheDir.isDirectory()) {
          FileSystem.SYSTEM.delete(cacheDir);
          cacheDir.mkdir();
        }
      } else {
        cacheDir.mkdir();
      }

      DiskCache diskCache = new DiskCache(cacheDir);
      assertEquals(FileSystem.SYSTEM.exists(cacheDir), true);

      String value = "Don't do that !!!";
      String key = "xxx";
      byte[] array = ByteString.encodeUtf8(value).toByteArray();

      DiskCache.Entry in = DiskCache.Entry.create(array, 1000, 500);
      diskCache.put(key, in);
      assertEquals(diskCache.maxSize() > diskCache.size(), true);

      DiskCache.Entry out = diskCache.get(key);
      assertEquals(true, out != null);
      assertEquals(value, ByteString.of(out.data, 0, out.data.length).utf8());

    } catch (IOException e) {
      System.out.println(e.toString());
    }
  }

  @Test
  public void testMemoryCache() {
    MemoryCache memoryCache = new MemoryCache();
    memoryCache.put("1", MemoryCache.Entry.create(Source.success("google"), 3000));
    memoryCache.put("2", MemoryCache.Entry.create(Source.success("square"), 5000));
    memoryCache.put("3", MemoryCache.Entry.create(Source.success("facebook"), 10000));

    assertEquals(3, memoryCache.size());

    MemoryCache.Entry entry = memoryCache.get("4");
    assertEquals(null, entry);
    assertEquals(1, memoryCache.missCount());

    entry = memoryCache.get("1");
    assertEquals(1, memoryCache.hitCount());
    Source<String> source = (Source) entry.data;
    assertEquals("google", source.data);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new UnsupportedOperationException();
    }

    entry = memoryCache.get("2");
    assertEquals(entry, null);
    assertEquals(2, memoryCache.size());

    entry = memoryCache.get("1");
    assertEquals(entry, null);
    assertEquals(1, memoryCache.size());

    entry = memoryCache.put("3", MemoryCache.Entry.create(Source.success("new facebook"),
        10000));

    assertEquals("facebook", ((Source) entry.data).data);
    entry = memoryCache.get("3");

    assertEquals("new facebook", ((Source) entry.data).data);
  }
}