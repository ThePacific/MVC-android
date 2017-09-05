package com.thepacific.clean;

import android.app.Application;
import android.util.Log;
import com.thepacific.data.http.Source;
import com.thepacific.presentation.core.Event;
import com.thepacific.presentation.core.ViewModel;
import com.thepacific.presentation.rx.ObservableUtil;
import com.uber.autodispose.ObservableSubscribeProxy;
import io.reactivex.Observable;
import javax.inject.Inject;

public class MainViewModel extends ViewModel {

  @Inject
  public MainViewModel(Application application) {
    super(application);
  }

  public ObservableSubscribeProxy<Source<Repo>> load(int userId) {
    return Observable.just(userId)
        .map(it -> {
          Thread.sleep(3000);
          return Source.success(Repo.create("Thepacific", 200, it));
        })
        .onErrorReturn(e -> Source.failure(e))
        .compose(ObservableUtil.io())
        .startWith(Source.inProgress())
        .doOnDispose(() -> Log.e("___________", "onDispose"))
        .to(ObservableUtil.bindUntil(lifecycle(), Event.ACTIVITY_STOP));
  }
}
