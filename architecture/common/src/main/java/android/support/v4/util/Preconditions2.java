/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.support.v4.util;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import javax.annotation.Nullable;

public class Preconditions2 {
    public static void checkArgument(boolean expression) {
        Preconditions.checkArgument(expression);
    }

    /**
     * Ensures that an expression checking an argument is true.
     *
     * @param expression   the expression to check
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, final Object errorMessage) {
        Preconditions.checkArgument(expression, errorMessage);
    }

    /**
     * Ensures that an string reference passed as a parameter to the calling
     * method is not empty.
     *
     * @param string an string reference
     * @return the string reference that was validated
     * @throws IllegalArgumentException if {@code string} is empty
     */
    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(final T string) {
        return Preconditions.checkStringNotEmpty(string);
    }

    /**
     * Ensures that an string reference passed as a parameter to the calling
     * method is not empty.
     *
     * @param string       an string reference
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the string reference that was validated
     * @throws IllegalArgumentException if {@code string} is empty
     */
    public static @NonNull
    <T extends CharSequence> T checkStringNotEmpty(final T string,
                                                   final Object errorMessage) {
        return Preconditions.checkStringNotEmpty(string, errorMessage);
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static @NonNull
    <T> T checkNotNull(final T reference) {
        return Preconditions.checkNotNull(reference);
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling
     * method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will
     *                     be converted to a string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static @NonNull
    <T> T checkNotNull(final T reference, final Object errorMessage) {
        return Preconditions.checkNotNull(reference, errorMessage);
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @param message    exception message
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(final boolean expression, String message) {
        Preconditions.checkState(expression, message);
    }

    /**
     * Ensures the truth of an expression involving the state of the calling
     * instance, but not involving any parameters to the calling method.
     *
     * @param expression a boolean expression
     * @throws IllegalStateException if {@code expression} is false
     */
    public static void checkState(final boolean expression) {
        checkState(expression, null);
    }

    /**
     * Check the requested flags, throwing if any requested flags are outside
     * the allowed set.
     *
     * @return the validated requested flags.
     */
    public static int checkFlagsArgument(final int requestedFlags, final int allowedFlags) {
        return Preconditions.checkFlagsArgument(requestedFlags, allowedFlags);
    }

    /**
     * Ensures that that the argument numeric value is non-negative.
     *
     * @param value        a numeric int value
     * @param errorMessage the exception message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static @IntRange(from = 0)
    int checkArgumentNonnegative(final int value, final String errorMessage) {
        return Preconditions.checkArgumentNonnegative(value, errorMessage);
    }

    /**
     * Ensures that that the argument numeric value is non-negative.
     *
     * @param value a numeric int value
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static @IntRange(from = 0)
    int checkArgumentNonnegative(final int value) {
        return Preconditions.checkArgumentNonnegative(value);
    }

    /**
     * Ensures that that the argument numeric value is non-negative.
     *
     * @param value a numeric long value
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static long checkArgumentNonnegative(final long value) {
        return Preconditions.checkArgumentNonnegative(value);
    }

    /**
     * Ensures that that the argument numeric value is non-negative.
     *
     * @param value        a numeric long value
     * @param errorMessage the exception message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was negative
     */
    public static long checkArgumentNonnegative(final long value, final String errorMessage) {
        return Preconditions.checkArgumentNonnegative(value, errorMessage);
    }

    /**
     * Ensures that that the argument numeric value is positive.
     *
     * @param value        a numeric int value
     * @param errorMessage the exception message to use if the check fails
     * @return the validated numeric value
     * @throws IllegalArgumentException if {@code value} was not positive
     */
    public static int checkArgumentPositive(final int value, final String errorMessage) {
        return Preconditions.checkArgumentPositive(value, errorMessage);
    }

    /**
     * Ensures that the argument floating point value is a finite number.
     * <p>
     * <p>A finite number is defined to be both representable (that is, not NaN) and
     * not infinite (that is neither positive or negative infinity).</p>
     *
     * @param value     a floating point value
     * @param valueName the name of the argument to use if the check fails
     * @return the validated floating point value
     * @throws IllegalArgumentException if {@code value} was not finite
     */
    public static float checkArgumentFinite(final float value, final String valueName) {
        return Preconditions.checkArgumentFinite(value, valueName);
    }

    /**
     * Ensures that the argument floating point value is within the inclusive range.
     * <p>
     * <p>While this can be used to range check against +/- infinity, note that all NaN numbers
     * will always be out of range.</p>
     *
     * @param value     a floating point value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated floating point value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static float checkArgumentInRange(float value, float lower, float upper,
                                             String valueName) {
        return Preconditions.checkArgumentInRange(value, lower, upper, valueName);
    }

    /**
     * Ensures that the argument int value is within the inclusive range.
     *
     * @param value     a int value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated int value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static int checkArgumentInRange(int value, int lower, int upper,
                                           String valueName) {
        return Preconditions.checkArgumentInRange(value, lower, upper, valueName);
    }

    /**
     * Ensures that the argument long value is within the inclusive range.
     *
     * @param value     a long value
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated long value
     * @throws IllegalArgumentException if {@code value} was not within the range
     */
    public static long checkArgumentInRange(long value, long lower, long upper,
                                            String valueName) {
        return Preconditions.checkArgumentInRange(value, lower, upper, valueName);
    }

    /**
     * Ensures that the array is not {@code null}, and none of its elements are {@code null}.
     *
     * @param value     an array of boxed objects
     * @param valueName the name of the argument to use if the check fails
     * @return the validated array
     * @throws NullPointerException if the {@code value} or any of its elements were {@code null}
     */
    public static <T> T[] checkArrayElementsNotNull(final T[] value, final String valueName) {
        return Preconditions.checkArrayElementsNotNull(value, valueName);
    }

    /**
     * Ensures that the {@link Collection} is not {@code null}, and none of its elements are
     * {@code null}.
     *
     * @param value     a {@link Collection} of boxed objects
     * @param valueName the name of the argument to use if the check fails
     * @return the validated {@link Collection}
     * @throws NullPointerException if the {@code value} or any of its elements were {@code null}
     */
    public static @NonNull
    <C extends Collection<T>, T> C checkCollectionElementsNotNull(
            final C value, final String valueName) {
        return Preconditions.checkCollectionElementsNotNull(value, valueName);
    }

    /**
     * Ensures that the {@link Collection} is not {@code null}, and contains at least one element.
     *
     * @param value     a {@link Collection} of boxed elements.
     * @param valueName the name of the argument to use if the check fails.
     * @return the validated {@link Collection}
     * @throws NullPointerException     if the {@code value} was {@code null}
     * @throws IllegalArgumentException if the {@code value} was empty
     */
    public static <T> Collection<T> checkCollectionNotEmpty(final Collection<T> value,
                                                            final String valueName) {
        return Preconditions.checkCollectionNotEmpty(value, valueName);
    }

    /**
     * Ensures that all elements in the argument floating point array are within the inclusive range
     * <p>
     * <p>While this can be used to range check against +/- infinity, note that all NaN numbers
     * will always be out of range.</p>
     *
     * @param value     a floating point array of values
     * @param lower     the lower endpoint of the inclusive range
     * @param upper     the upper endpoint of the inclusive range
     * @param valueName the name of the argument to use if the check fails
     * @return the validated floating point value
     * @throws IllegalArgumentException if any of the elements in {@code value} were out of range
     * @throws NullPointerException     if the {@code value} was {@code null}
     */
    public static float[] checkArrayElementsInRange(float[] value, float lower, float upper,
                                                    String valueName) {
        return Preconditions.checkArrayElementsInRange(value, lower, upper, valueName);
    }

    public static void checkPositionIndexes(int start, int end, int size) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException("bad position");
        }
    }

    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, "index");
    }

    public static int checkPositionIndex(int index, int size, @Nullable String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
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