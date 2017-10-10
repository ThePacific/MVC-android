package com.thepacific.guava;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import javax.annotation.Nullable;

public interface Predicate<T> {

  @CanIgnoreReturnValue
  boolean apply(@Nullable T input);

  @Override
  boolean equals(@Nullable Object object);
}
