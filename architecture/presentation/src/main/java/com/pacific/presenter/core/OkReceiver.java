package com.pacific.presenter.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.pacific.guava.Preconditions;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Inject;

public final class OkReceiver extends BroadcastReceiver {

  static final String FINISH_ACTION = "com.pacific.presentation.finish";
  private final ArrayMap<String, Consumer> consumers = new ArrayMap<>();

  @Inject
  public OkReceiver() {
    super();
  }

  public static void sendBroadcast(@Nonnull Context context, @Nonnull String action) {
    Preconditions.checkNotNull(context);
    Preconditions.checkState(!TextUtils.isEmpty(action));
    Intent intent = new Intent();
    intent.setAction(action);
    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
  }

  public static void sendFinishBroadcast(@Nonnull Context context) {
    sendBroadcast(context, FINISH_ACTION);
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    Preconditions.checkNotNull(intent);
    for (Map.Entry<String, Consumer> entry : consumers.entrySet()) {
      if (entry.getKey().equals(intent.getAction())) {
        entry.getValue().run(context, intent);
        break;
      }
    }
  }

  public void addConsumer(@Nonnull IntentFilter intentFilter, @Nonnull String action,
      @Nonnull Consumer consumer) {
    Preconditions.checkNotNull(intentFilter);
    Preconditions.checkState(!TextUtils.isEmpty(action));
    Preconditions.checkNotNull(consumer);
    intentFilter.addAction(action);
    consumers.put(action, consumer);
  }

  public void clearConsumer() {
    consumers.clear();
  }

  public void removeConsumer(@Nonnull String action) {
    Preconditions.checkState(!TextUtils.isEmpty(action));
    consumers.remove(action);
  }

  public interface Consumer {

    void run(Context context, Intent intent);
  }
}
