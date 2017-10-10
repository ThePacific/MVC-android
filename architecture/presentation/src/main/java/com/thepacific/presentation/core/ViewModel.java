package com.thepacific.presentation.core;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.util.Preconditions;
import android.text.TextUtils;
import com.uber.autodispose.LifecycleScopeProvider;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;

public abstract class ViewModel extends AndroidViewModel implements LifecycleScopeProvider<Event>,
    LifecycleObserver {

  private Event event = Event.ANY;

  private final EventMapper eventMapper = new EventMapper();
  private final PublishSubject<Event> lifecycle = PublishSubject.create();

  public ViewModel(Application application) {
    super(application);
  }

  @Override
  public final Observable<Event> lifecycle() {
    return lifecycle;
  }

  @Override
  public final Function<Event, Event> correspondingEvents() {
    return eventMapper;
  }

  @Override
  public final Event peekLifecycle() {
    return this.event;
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
  final void onEvent(LifecycleOwner owner, Lifecycle.Event event) {
    this.event = EventMapper.map(owner, event);
    lifecycle.onNext(this.event);
    if (event == Lifecycle.Event.ON_DESTROY) {
      this.event = Event.ANY;
    }
  }

  public enum Provider {
    ACTIVITY, PARENT_FRAGMENT, NONE
  }
}
