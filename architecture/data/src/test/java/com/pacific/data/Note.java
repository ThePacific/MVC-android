package com.pacific.data;

import com.squareup.moshi.Json;

public class Note {

  @Json(name = "note_title")
  public String title;

  @Json(name = "note_size")
  public long size;

  public String content;

  @SuppressWarnings("unused") // Moshi uses this!
  public Note() {
  }
}