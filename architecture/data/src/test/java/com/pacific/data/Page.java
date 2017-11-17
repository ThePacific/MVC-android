package com.pacific.data;

import com.squareup.moshi.Json;
import java.util.List;

public class Page {

  @Json(name = "my_notes")
  public List<Note> notes;
}
