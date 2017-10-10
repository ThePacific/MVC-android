package com.thepacific.guava;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class EmptyUtil {

  public static boolean isEmpty(Collection collection) {
    if (collection == null || collection.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(Map map) {
    if (map == null || map.isEmpty()) {
      return true;
    }
    return false;
  }

  public static boolean isEmpty(Object array) {
    if (array == null) {
      return true;
    }
    try {
      return Array.getLength(array) == 0;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean isEmpty(CharSequence string) {
    return string == null || string.length() == 0;
  }

  private EmptyUtil() {
  }
}
