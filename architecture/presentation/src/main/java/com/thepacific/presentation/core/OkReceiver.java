package com.thepacific.presentation.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.util.ArrayMap;
import com.thepacific.common.Preconditions;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.inject.Inject;

public final class OkReceiver extends BroadcastReceiver {

  private final ArrayMap<String, Consumer> consumers = new ArrayMap<>();

  @Inject
  public OkReceiver() {
    super();
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent != null) {
      for (Map.Entry<String, Consumer> entry : consumers.entrySet()) {
        if (entry.getKey().equals(intent.getAction())) {
          entry.getValue().run(context, intent);
          break;
        }
      }
    } else {
      throw new UnsupportedOperationException();
    }
  }

  public void addConsumer(@Nonnull String action, @Nonnull Consumer consumer) {
    Preconditions.checkNotNull(action);
    Preconditions.checkNotNull(consumer);
    consumers.put(action, consumer);
  }

  public void clearConsumer() {
    consumers.clear();
  }

  public static void sendBroadcast(@Nonnull Context context, @Nonnull String action) {
    Preconditions.checkNotNull(context);
    Preconditions.checkNotNull(action);
    Intent intent = new Intent();
    intent.setAction(action);
    context.sendBroadcast(intent);
  }

  public interface Consumer {

    void run(Context context, Intent intent);
  }
}
