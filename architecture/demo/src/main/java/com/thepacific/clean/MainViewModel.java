package com.thepacific.clean;

import android.app.Application;
import android.util.Log;
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

  public ObservableSubscribeProxy<Integer> load() {
    return Observable.just(0)
        .map(it -> {
          Thread.sleep(100000);
          return it;
        })
        .compose(ObservableUtil.<Integer>io())
        .doOnDispose(() -> Log.e("___________", "onDispose"))
        .to(ObservableUtil.<Integer>bindUntil(lifecycle(), Event.ACTIVITY_STOP));
  }
}
