package com.thepacific.data;

import com.thepacific.data.http.Envelope;
import java.util.ArrayList;
import java.util.List;

public class EnvelopeImpl implements Envelope<List<User>> {

  @Override
  public List<User> data() {
    List<User> users = new ArrayList<>();
    users.add(new User("Jake",
        "Wharton",
        "1990.10.01",
        "US",
        10));
    users.add(new User("Barry",
        "Allen",
        "1992.10.01",
        "US",
        10));
    return users;
  }

  @Override
  public boolean isSuccess() {
    return true;
  }

  @Override
  public String message() {
    return null;
  }

  @Override
  public int code() {
    return 200;
  }

  public static EnvelopeImpl create() {
    return new EnvelopeImpl();
  }
}
