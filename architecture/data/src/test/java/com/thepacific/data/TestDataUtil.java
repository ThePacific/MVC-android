package com.thepacific.data;

import static org.junit.Assert.assertEquals;

import com.thepacific.data.cache.MemoryCache;
import com.thepacific.data.common.DataUtil;
import com.thepacific.guava.MapString;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Created by Android Open Source Project.
 */

@RunWith(MockitoJUnitRunner.class)
public class TestDataUtil {

  @Test
  public void testIsEmpty() {
    String[] strings = new String[]{"we", "are", "the", "same"};
    String[] emptyArray = new String[]{};
    assertEquals(false, DataUtil.isEmptyArray(strings));
    assertEquals(true, DataUtil.isEmptyArray(emptyArray));
    assertEquals(true, DataUtil.isEmptyArray(null));

    List<String> list = new ArrayList<>();
    assertEquals(true, DataUtil.isEmpty(list));
    list.add("a");
    assertEquals(false, DataUtil.isEmpty(list));

    Map<String, String> map = new HashMap<>();
    assertEquals(true, DataUtil.isEmpty(map));
    map.put("a", "A");
    assertEquals(false, DataUtil.isEmpty(map));

  }

  @Test
  public void testToJson() {
    MemoryCache.Entry entry = MemoryCache.Entry.create("Just test", 1000L);
    final String expected = "{\"data\":\"Just test\",\"ttl\":1000}";
    assertEquals(expected, DataUtil.toJson(entry, null));
  }

  @Test
  public void testFromJson() {
    final String json = "{\"data\":\"Just test\",\"ttl\":1000}";
    MemoryCache.Entry entry = DataUtil.fromJson(json, null, MemoryCache.Entry.class);
    assertEquals("Just test", entry.data);
    assertEquals(1000L, entry.ttl);
  }

  @Test
  public void testCheckPassword() {
    String regexExpected = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}";

    String regex = MapString
        .format("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{<minLength>,<maxLength>}",
            "<",
            ">")
        .with("minLength", String.valueOf(6))
        .with("maxLength", String.valueOf(16))
        .build();
    assertEquals(regexExpected, regex);

    assertEquals(true, DataUtil.andPattern("123sample", 6, 16));
    assertEquals(false, DataUtil.andPattern("todoSample", 6, 16));
    assertEquals(true, DataUtil.andPattern("123456789012345a", 6, 16));
    assertEquals(false, DataUtil.andPattern("1234a", 6, 16));

    assertEquals(true, DataUtil.orPattern("123sample", 6, 16));
    assertEquals(true, DataUtil.orPattern("todoSample", 6, 16));
    assertEquals(true, DataUtil.orPattern("123456789012345a", 6, 16));
    assertEquals(false, DataUtil.orPattern("1234a", 6, 16));
    assertEquals(true, DataUtil.orPattern("123456", 6, 16));
    assertEquals(false, DataUtil.orPattern("12345", 6, 16));
  }
}
