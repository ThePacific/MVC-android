package com.pacific.arch.gauva;

import android.support.annotation.Nullable;

import static com.pacific.arch.gauva.Preconditions2.checkArgument;
import static com.pacific.arch.gauva.Preconditions2.checkNotNull;
import static com.pacific.arch.gauva.Preconditions2.isEmpty;

public final class Strings {

    private Strings() {
    }

    public static String nullToEmpty(@Nullable String string) {
        return (string == null) ? "" : string;
    }

    @Nullable
    public static String emptyToNull(@Nullable String string) {
        return isEmpty(string) ? null : string;
    }

    public static String padStart(String string, int minLength, char padChar) {
        checkNotNull(string);
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        sb.append(string);
        return sb.toString();
    }

    public static String padEnd(String string, int minLength, char padChar) {
        checkNotNull(string); // eager for GWT.
        if (string.length() >= minLength) {
            return string;
        }
        StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);
        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }
        return sb.toString();
    }

    public static String repeat(String string, int count) {
        checkNotNull(string);

        if (count <= 1) {
            checkArgument(count >= 0, count);
            return (count == 0) ? "" : string;
        }

        // IF YOU MODIFY THE CODE HERE, you must update StringsRepeatBenchmark
        final int len = string.length();
        final long longSize = (long) len * (long) count;
        final int size = (int) longSize;
        if (size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + longSize);
        }

        final char[] array = new char[size];
        string.getChars(0, len, array, 0);
        int n;
        for (n = len; n < size - n; n <<= 1) {
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }

    public static String commonPrefix(CharSequence a, CharSequence b) {
        checkNotNull(a);
        checkNotNull(b);

        int maxPrefixLength = Math.min(a.length(), b.length());
        int p = 0;
        while (p < maxPrefixLength && a.charAt(p) == b.charAt(p)) {
            p++;
        }
        if (validSurrogatePairAt(a, p - 1) || validSurrogatePairAt(b, p - 1)) {
            p--;
        }
        return a.subSequence(0, p).toString();
    }

    public static String commonSuffix(CharSequence a, CharSequence b) {
        checkNotNull(a);
        checkNotNull(b);

        int maxSuffixLength = Math.min(a.length(), b.length());
        int s = 0;
        while (s < maxSuffixLength && a.charAt(a.length() - s - 1) == b.charAt(b.length() - s - 1)) {
            s++;
        }
        if (validSurrogatePairAt(a, a.length() - s - 1) || validSurrogatePairAt(b, b.length() - s - 1)) {
            s--;
        }
        return a.subSequence(a.length() - s, a.length()).toString();
    }

    static boolean validSurrogatePairAt(CharSequence string, int index) {
        return index >= 0
                && index <= (string.length() - 2)
                && Character.isHighSurrogate(string.charAt(index))
                && Character.isLowSurrogate(string.charAt(index + 1));
    }
}
