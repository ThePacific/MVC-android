package com.pacific.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.pacific.guava.JdkPattern;
import com.pacific.guava.Joiner;
import com.pacific.guava.OkString;
import com.pacific.guava.Preconditions;
import com.squareup.moshi.Types;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestHttpUtil {

  @Test
  public void testIsEmpty() {
    String[] strings = new String[]{"we", "are", "the", "same"};
    String[] emptyArray = new String[]{};
    assertEquals(false, Preconditions.isEmpty(strings));
    assertEquals(true, Preconditions.isEmpty(emptyArray));
    assertEquals(true, Preconditions.isEmpty(""));

    List<String> list = new ArrayList<>();
    assertEquals(true, Preconditions.isEmpty(list));
    list.add("a");
    assertEquals(false, Preconditions.isEmpty(list));

    Map<String, String> map = new HashMap<>();
    assertEquals(true, Preconditions.isEmpty(map));
    map.put("a", "A");
    assertEquals(false, Preconditions.isEmpty(map));
  }

  @Test
  public void testToJson() throws IOException {
    MemoryCache.Entry entry = MemoryCache.Entry.create("Just test", 1000L);
    final String expected = "{\"data\":\"Just test\",\"ttl\":1000}";
    assertEquals(expected, HttpUtil.toJson(entry, null, MemoryCache.Entry.class));

    Note note = new Note();
    note.title = "for example";
    note.size = 512;
    final String target = "{\"note_size\":512,\"note_title\":\"for example\"}";
    assertEquals(target, HttpUtil.toJson(note, null, Object.class));
  }

  @Test
  public void testFromJson() throws IOException {
    final String json = "{\"data\":\"Just test\",\"ttl\":1000}";
    MemoryCache.Entry entry = HttpUtil.fromJson(json, null, MemoryCache.Entry.class);
    assertEquals("Just test", entry.data);
    assertEquals(1000L, entry.ttl);

    final String json1 = "{\"my_notes\":["
        + "{\"note_title\":\"first note\",\"note_size\":1024},"
        + "{\"note_title\":\"second note\",\"note_size\":2048}"
        + "]}";

    Page page = HttpUtil.fromJson(json1, null, Page.class);
    assertEquals(2, page.notes.size());
    assertEquals(1024, page.notes.get(0).size);
    assertEquals("first note", page.notes.get(0).title);
    assertEquals(2048, page.notes.get(1).size);
    assertEquals("second note", page.notes.get(1).title);

    final String json2 = "{\"my_notes\":["
        + "{\"note_title\":\"first note\",\"note_size\":1024},"
        + "{\"note_title\":\"second note\",\"note_size\":2048}"
        + "]}";

    PageT<Note> page2 = HttpUtil
        .fromJson(json2, null, Types.newParameterizedType(PageT.class, Note.class));
    assertEquals(2, page2.notes.size());
    assertEquals(1024, page2.notes.get(0).size);
    assertEquals("first note", page2.notes.get(0).title);
    assertEquals(2048, page2.notes.get(1).size);
    assertEquals("second note", page2.notes.get(1).title);

    final String listJson = "["
        + "{\"note_title\":\"first note\",\"note_size\":1024},"
        + "{\"note_title\":\"second note\",\"note_size\":2048}"
        + "]";

    List<Note> notes = HttpUtil
        .fromJson(listJson, null, Types.newParameterizedType(List.class, Note.class));
    assertEquals(2, notes.size());
    assertEquals(1024, notes.get(0).size);
    assertEquals("first note", notes.get(0).title);
    assertEquals(2048, notes.get(1).size);
    assertEquals("second note", notes.get(1).title);

    HttpUtil.toJson(page2, null, Types.newParameterizedType(PageT.class, Note.class));
  }

  @Test
  public void testToMap() throws IOException {
    Note note = new Note();
    note.title = "for example";
    note.size = 512;
    note.content = "Hello world";

    Map<String, String> map = HttpUtil.toMap(note, null);

    assertEquals(3, map.size());
    assertEquals(true, map.containsKey("note_title"));
    assertEquals(true, map.containsKey("note_size"));
    assertEquals(true, map.containsKey("content"));
    assertEquals("for example", map.get("note_title"));
    assertEquals(512L, map.get("note_size"));
    assertEquals("Hello world", map.get("content"));

    Map<String, String> map2 = HttpUtil.toMap(note, null);
    assertEquals(3, map2.size());
    assertEquals(true, map2.containsKey("note_title"));
    assertEquals(true, map2.containsKey("note_size"));
    assertEquals(true, map2.containsKey("content"));
    assertEquals("for example", map2.get("note_title"));
    // only different
    assertEquals(512L, map2.get("note_size"));
    assertEquals("Hello world", map2.get("content"));

    assertTrue(map.toString().equals(map2.toString()));
  }

  @Test
  public void testJdkPattern() {
    assertEquals(true, JdkPattern.and("123sample", 6, 16));
    assertEquals(false, JdkPattern.and("todoSample", 6, 16));
    assertEquals(true, JdkPattern.and("123456789012345a", 6, 16));
    assertEquals(false, JdkPattern.and("1234a", 6, 16));
    assertEquals(true, JdkPattern.or("123sample", 6, 16));
    assertEquals(true, JdkPattern.or("todoSample", 6, 16));
    assertEquals(true, JdkPattern.or("123456789012345a", 6, 16));
    assertEquals(false, JdkPattern.or("1234a", 6, 16));
    assertEquals(true, JdkPattern.or("123456", 6, 16));
    assertEquals(false, JdkPattern.or("12345", 6, 16));
  }

  @Test
  public void testOkString() {
    final String expected = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}";
    String actual = OkString
        .format("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{<min>,<max>}", "<", ">")
        .with("min", String.valueOf(6))
        .with("max", String.valueOf(16))
        .build();
    assertEquals(expected, actual);
  }

  @Test
  public void testJoiner() {
    Joiner joiner = Joiner.on("");
    assertEquals("ab", joiner.join("a", "b"));

    joiner = Joiner.on(",");
    assertEquals("a,b", joiner.join("a", "b"));
  }
}
