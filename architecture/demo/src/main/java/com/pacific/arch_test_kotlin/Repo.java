package com.pacific.arch_test_kotlin;

public class Repo {

  public final String name;
  public final int star;
  public final int userId;

  private Repo(String name, int star, int userId) {
    this.name = name;
    this.star = star;
    this.userId = userId;
  }

  public static Repo create(String name, int star, int userId) {
    return new Repo(name, star, userId);
  }
}
