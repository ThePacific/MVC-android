package com.thepacific.data;

import static org.junit.Assert.assertEquals;

import com.thepacific.data.http.Source;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RepositoryTest {

  UserRepo userRepo = UserRepo.create();
  UserQuery userQuery = UserQuery.create();
  List<User> firstMemoryUser;
  List<User> secondMemoryUser;

  @Test
  public void testFetch() {
    userRepo.fetch(userQuery, true, true)
        .onErrorReturn(e -> Source.failure(e))
        .startWith(Source.inProgress())
        .subscribe(it -> {
          switch (it.status) {
            case IN_PROGRESS:
              System.out.println("Show Loading Dialog===============");
              break;
            case IRRELEVANT:
              System.out.println("Empty Data===============");
              break;
            case ERROR:
              System.out.println("Error Occur===============");
              break;
            case SUCCESS:
              System.out.println("Update UI===============");
              System.out.println(it.data.size());
              break;
            default:
              throw new UnsupportedOperationException();
          }
        });

    assertEquals(2, userRepo.memory().size());
  }

  @Test
  public void testGet() {
    userRepo.get(userQuery)
        .onErrorReturn(e -> Source.failure(e))
        .startWith(Source.inProgress())
        .subscribe(it -> {
          switch (it.status) {
            case IN_PROGRESS:
              System.out.println("Show Loading Dialog===============");
              break;
            case IRRELEVANT:
              System.out.println("Empty Data===============");
              break;
            case ERROR:
              System.out.println("Error Occur===============");
              break;
            case SUCCESS:
              System.out.println("Update UI===============");
              break;
            default:
              throw new UnsupportedOperationException();
          }
        });

    //it will return from cache
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new UnsupportedOperationException();
    }
    userRepo.get(userQuery).subscribe(it -> {
      firstMemoryUser = it.data;
      System.out.println(firstMemoryUser.get(1));
    });

    //it will return from cache
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      throw new UnsupportedOperationException();
    }

    userRepo.get(userQuery).subscribe(it -> {
      secondMemoryUser = it.data;
      //firstMemoryUser == secondMemoryUser
      assertEquals(firstMemoryUser, secondMemoryUser);
      System.out.println(secondMemoryUser.get(0));
    });
  }
}