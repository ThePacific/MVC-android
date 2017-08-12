package com.thepacific.data.http;

import android.support.annotation.MainThread;

public interface OnAccessFailure {

  @MainThread
  void run(IoError error);
}