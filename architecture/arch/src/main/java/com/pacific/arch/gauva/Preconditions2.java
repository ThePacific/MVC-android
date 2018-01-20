package com.pacific.arch.gauva;

import android.annotation.SuppressLint;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Preconditions;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

@SuppressLint("RestrictedApi")
public class Preconditions2 {
    private Preconditions2() {
    }

    public static void checkArgument(boolean expression) {
        Preconditions.checkArgument(expression);
    }

    public static void checkArgument(boolean expression, final Object errorMessage) {
        Preconditions.checkArgument(expression, errorMessage);
    }

    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(final T string) {
        return Preconditions.checkStringNotEmpty(string);
    }

    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(final T string,
                                                   final Object errorMessage) {
        return Preconditions.checkStringNotEmpty(string, errorMessage);
    }

    public static @NonNull
    <T> T checkNotNull(final T reference) {
        return Preconditions.checkNotNull(reference);
    }

    public static @NonNull
    <T> T checkNotNull(final T reference, final Object errorMessage) {
        return Preconditions.checkNotNull(reference, errorMessage);
    }

    public static void checkState(final boolean expression, String message) {
        Preconditions.checkState(expression, message);
    }

    public static void checkState(final boolean expression) {
        checkState(expression, null);
    }

    public static int checkFlagsArgument(final int requestedFlags, final int allowedFlags) {
        return Preconditions.checkFlagsArgument(requestedFlags, allowedFlags);
    }

    public static @IntRange(from = 0)
    int checkArgumentNonnegative(final int value, final String errorMessage) {
        return Preconditions.checkArgumentNonnegative(value, errorMessage);
    }

    public static @IntRange(from = 0)
    int checkArgumentNonnegative(final int value) {
        return Preconditions.checkArgumentNonnegative(value);
    }

    public static long checkArgumentNonnegative(final long value) {
        return Preconditions.checkArgumentNonnegative(value);
    }

    public static long checkArgumentNonnegative(final long value, final String errorMessage) {
        return Preconditions.checkArgumentNonnegative(value, errorMessage);
    }

    public static int checkArgumentPositive(final int value, final String errorMessage) {
        return Preconditions.checkArgumentPositive(value, errorMessage);
    }

    public static float checkArgumentFinite(final float value, final String valueName) {
        return Preconditions.checkArgumentFinite(value, valueName);
    }

    public static float checkArgumentInRange(float value, float lower, float upper,
                                             String valueName) {
        return Preconditions.checkArgumentInRange(value, lower, upper, valueName);
    }

    public static int checkArgumentInRange(int value, int lower, int upper,
                                           String valueName) {
        return Preconditions.checkArgumentInRange(value, lower, upper, valueName);
    }

    public static long checkArgumentInRange(long value, long lower, long upper,
                                            String valueName) {
        return Preconditions.checkArgumentInRange(value, lower, upper, valueName);
    }

    public static <T> T[] checkArrayElementsNotNull(final T[] value, final String valueName) {
        return Preconditions.checkArrayElementsNotNull(value, valueName);
    }

    public static @NonNull
    <C extends Collection<T>, T> C checkCollectionElementsNotNull(final C value,
                                                                  final String valueName) {
        return Preconditions.checkCollectionElementsNotNull(value, valueName);
    }

    public static <T> Collection<T> checkCollectionNotEmpty(final Collection<T> value,
                                                            final String valueName) {
        return Preconditions.checkCollectionNotEmpty(value, valueName);
    }

    public static float[] checkArrayElementsInRange(float[] value, float lower, float upper,
                                                    String valueName) {
        return Preconditions.checkArrayElementsInRange(value, lower, upper, valueName);
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException("bad position");
        }
    }

    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, "index");
    }

    public static int checkPositionIndex(int index, int size, @Nullable String desc) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("bad position");
        }
        return index;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(Object object) {
        if (object == null) return true;
        try {
            return Array.getLength(object) == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }
}