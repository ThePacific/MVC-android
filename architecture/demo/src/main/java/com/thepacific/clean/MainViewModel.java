package com.thepacific.clean;

import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.thepacific.common.rx.ObservableUtil;
import com.thepacific.data.http.Source;
import io.reactivex.Observable;
import javax.inject.Inject;

public class MainViewModel extends ViewModel {

  @Inject
  public MainViewModel() {
  }

  public Observable<Source<Repo>> load(int userId) {
    return Observable.just(userId)
        .map(it -> {
          Thread.sleep(3000);
          return Source.success(Repo.create("Thepacific", 200, it));
        })
        .onErrorReturn(e -> Source.failure(e))
        .compose(ObservableUtil.io())
        .startWith(Source.inProgress())
        .doOnDispose(() -> Log.e("___________", "onDispose"));
  }
}
