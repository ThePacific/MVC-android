package com.thepacific.presentation.core;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import com.uber.autodispose.LifecycleEndedException;
import io.reactivex.functions.Function;

@RestrictTo(Scope.LIBRARY)
public final class EventMapper implements Function<Event, Event> {

  @Override
  public Event apply(Event event) throws Exception {
    switch (event) {
      case ACTIVITY_CREATE:
        return Event.ACTIVITY_DESTROY;
      case ACTIVITY_START:
        return Event.ACTIVITY_STOP;
      case ACTIVITY_RESUME:
        return Event.ACTIVITY_PAUSE;
      case ACTIVITY_PAUSE:
        return Event.ACTIVITY_STOP;
      case ACTIVITY_STOP:
        return Event.ACTIVITY_DESTROY;
      case ACTIVITY_DESTROY:
        throw new LifecycleEndedException();
      case FRAGMENT_CREATE:
        return Event.FRAGMENT_DESTROY;
      case FRAGMENT_START:
        return Event.FRAGMENT_STOP;
      case FRAGMENT_RESUME:
        return Event.FRAGMENT_PAUSE;
      case FRAGMENT_PAUSE:
        return Event.FRAGMENT_STOP;
      case FRAGMENT_STOP:
        return Event.FRAGMENT_DESTROY;
      case FRAGMENT_DESTROY:
        throw new LifecycleEndedException();
      case DIALOG_CREATE:
        return Event.DIALOG_DESTROY;
      case DIALOG_START:
        return Event.DIALOG_STOP;
      case DIALOG_RESUME:
        return Event.DIALOG_PAUSE;
      case DIALOG_PAUSE:
        return Event.DIALOG_STOP;
      case DIALOG_STOP:
        return Event.DIALOG_DESTROY;
      case DIALOG_DESTROY:
        throw new LifecycleEndedException();
      default:
        throw new UnsupportedOperationException();
    }
  }

  public static Event map(LifecycleOwner owner, Lifecycle.Event event) {
    if (owner instanceof Activity) {
      switch (event) {
        case ON_CREATE:
          return Event.ACTIVITY_CREATE;
        case ON_START:
          return Event.ACTIVITY_START;
        case ON_RESUME:
          return Event.ACTIVITY_RESUME;
        case ON_PAUSE:
          return Event.ACTIVITY_PAUSE;
        case ON_STOP:
          return Event.ACTIVITY_STOP;
        case ON_DESTROY:
          return Event.ACTIVITY_DESTROY;
        case ON_ANY:
          return Event.ANY;
        default:
          throw new UnsupportedOperationException();
      }
    }
    if (owner instanceof AppCompatDialogFragment || owner instanceof BottomSheetDialogFragment) {
      switch (event) {
        case ON_CREATE:
          return Event.DIALOG_CREATE;
        case ON_START:
          return Event.DIALOG_START;
        case ON_RESUME:
          return Event.DIALOG_RESUME;
        case ON_PAUSE:
          return Event.DIALOG_PAUSE;
        case ON_STOP:
          return Event.DIALOG_STOP;
        case ON_DESTROY:
          return Event.DIALOG_DESTROY;
        case ON_ANY:
          return Event.ANY;
        default:
          throw new UnsupportedOperationException();
      }
    }
    if (owner instanceof Fragment) {
      switch (event) {
        case ON_CREATE:
          return Event.FRAGMENT_CREATE;
        case ON_START:
          return Event.FRAGMENT_START;
        case ON_RESUME:
          return Event.FRAGMENT_RESUME;
        case ON_PAUSE:
          return Event.FRAGMENT_PAUSE;
        case ON_STOP:
          return Event.FRAGMENT_STOP;
        case ON_DESTROY:
          return Event.FRAGMENT_DESTROY;
        case ON_ANY:
          return Event.ANY;
        default:
          throw new UnsupportedOperationException();
      }
    }
    throw new UnsupportedOperationException("Owner must be com.boo.presentation.component.*");
  }
}
