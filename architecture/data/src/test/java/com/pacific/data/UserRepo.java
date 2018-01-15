package com.pacific.data;

import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import io.reactivex.Observable;
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

public class UserRepo extends Repository<UserQuery, List<User>> {

  private UserRepo(Moshi moshi, DiskCache diskCache, MemoryCache memoryCache) {
    super(moshi, diskCache, memoryCache);
  }

  public static UserRepo create() {
    File cacheDir = new File("http");
    return new UserRepo(new Moshi.Builder().build(),
        new DiskCache(new Moshi.Builder().build(), cacheDir),
        new MemoryCache());
  }

  @Override
  protected boolean isIrrelevant(List<User> data) {
    return data == null;
  }

  @Override
  protected Observable<Envelope<List<User>>> dispatchNetwork(UserQuery query) {
    return Observable.just(EnvelopeImpl.create());
  }

  @Override
  protected String getKey(UserQuery query) {
    return "get-user";
  }

  @Override
  protected Type dataType() {
    return Types.newParameterizedType(List.class, User.class);
  }
}
