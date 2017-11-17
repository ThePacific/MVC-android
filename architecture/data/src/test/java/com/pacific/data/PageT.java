package com.pacific.data;

import com.squareup.moshi.Json;
import java.util.List;

public class PageT<T> {

  @Json(name = "my_notes")
  public List<T> notes;
}
