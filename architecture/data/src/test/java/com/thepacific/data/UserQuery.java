package com.thepacific.data;

public class UserQuery {

  public final String loginName = "googleIo";
  public final String loginPassword = "20171001";

  private UserQuery() {
  }

  public static UserQuery create() {
    return new UserQuery();
  }
}
