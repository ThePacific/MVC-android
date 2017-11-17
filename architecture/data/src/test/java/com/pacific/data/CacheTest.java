package com.pacific.data;

import static org.junit.Assert.assertEquals;

import com.pacific.data.cache.DiskCache;
import com.pacific.data.cache.MemoryCache;
import com.pacific.data.http.Source;
import com.squareup.moshi.Moshi;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.io.FileSystem;
import okio.ByteString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class CacheTest {

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

      DiskCache diskCache = new DiskCache(new Moshi.Builder().build(), cacheDir);
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
    final long now = System.currentTimeMillis();
    memoryCache.put("1", MemoryCache.Entry.create(Source.success("google"), 3000 + now));
    memoryCache.put("2", MemoryCache.Entry.create(Source.success("square"), 5000 + now));
    memoryCache.put("3", MemoryCache.Entry.create(Source.success("facebook"), 10000 + now));
    assertEquals(3, memoryCache.size());

    MemoryCache.Entry entry = memoryCache.get("4", false);
    assertEquals(null, entry);
    assertEquals(1, memoryCache.missCount());

    entry = memoryCache.get("1", false);
    Source<String> o = (Source<String>) entry.data;
    assertEquals("google", o.data);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new UnsupportedOperationException();
    }
    assertEquals(3, memoryCache.size());
    entry = memoryCache.get("2", true);
    assertThat(entry).isNull();

    memoryCache.evictExpired();
    assertEquals(1, memoryCache.size());

    entry = memoryCache.put("3", MemoryCache.Entry.create(Source.success("new facebook"),
        10000 + now));
    assertEquals("facebook", ((Source) entry.data).data);

    entry = memoryCache.get("3", false);
    assertEquals("new facebook", ((Source) entry.data).data);
  }

  @Test
  public void testTimeUnit() {
    final int ttl = 5;
    assertEquals(60 * 1000 * ttl, TimeUnit.MINUTES.toMillis(ttl));
  }
}