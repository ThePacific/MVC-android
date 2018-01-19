package com.pacific.arch.gauva;

import android.support.annotation.Nullable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

public interface Predicate<T> {

    @CanIgnoreReturnValue
    boolean apply(@Nullable T input);

    @Override
    boolean equals(@Nullable Object object);
}