package com.pacific.arch.gauva;

import static java.lang.Character.MAX_SURROGATE;
import static java.lang.Character.MIN_SURROGATE;

public final class Utf8 {

    private Utf8() {
    }

    public static int encodedLength(CharSequence sequence) {
        // Warning to maintainers: this implementation is highly optimized.
        int utf16Length = sequence.length();
        int utf8Length = utf16Length;
        int i = 0;

        // This loop optimizes for pure ASCII.
        while (i < utf16Length && sequence.charAt(i) < 0x80) {
            i++;
        }

        // This loop optimizes for chars less than 0x800.
        for (; i < utf16Length; i++) {
            char c = sequence.charAt(i);
            if (c < 0x800) {
                utf8Length += ((0x7f - c) >>> 31); // branch free!
            } else {
                utf8Length += encodedLengthGeneral(sequence, i);
                break;
            }
        }

        if (utf8Length < utf16Length) {
            // Necessary and sufficient condition for overflow because of maximum 3x expansion
            throw new IllegalArgumentException(
                    "UTF-8 length does not fit in int: " + (utf8Length + (1L << 32)));
        }
        return utf8Length;
    }

    private static int encodedLengthGeneral(CharSequence sequence, int start) {
        int utf16Length = sequence.length();
        int utf8Length = 0;
        for (int i = start; i < utf16Length; i++) {
            char c = sequence.charAt(i);
            if (c < 0x800) {
                utf8Length += (0x7f - c) >>> 31; // branch free!
            } else {
                utf8Length += 2;
                // jdk7+: if (Character.isSurrogate(c)) {
                if (MIN_SURROGATE <= c && c <= MAX_SURROGATE) {
                    // Check that we have a well-formed surrogate pair.
                    if (Character.codePointAt(sequence, i) == c) {
                        throw new IllegalArgumentException(unpairedSurrogateMsg(i));
                    }
                    i++;
                }
            }
        }
        return utf8Length;
    }

    public static boolean isWellFormed(byte[] bytes) {
        return isWellFormed(bytes, 0, bytes.length);
    }

    public static boolean isWellFormed(byte[] bytes, int off, int len) {
        int end = off + len;
        Preconditions2.checkPositionIndexes(off, end, bytes.length);
        // Look for the first non-ASCII character.
        for (int i = off; i < end; i++) {
            if (bytes[i] < 0) {
                return isWellFormedSlowPath(bytes, i, end);
            }
        }
        return true;
    }

    private static boolean isWellFormedSlowPath(byte[] bytes, int off, int end) {
        int index = off;
        while (true) {
            int byte1;
            // Optimize for interior runs of ASCII bytes.
            do {
                if (index >= end) {
                    return true;
                }
            } while ((byte1 = bytes[index++]) >= 0);

            if (byte1 < (byte) 0xE0) {
                // Two-byte form.
                if (index == end) {
                    return false;
                }
                // Simultaneously check for illegal trailing-byte in leading position
                // and overlong 2-byte form.
                if (byte1 < (byte) 0xC2 || bytes[index++] > (byte) 0xBF) {
                    return false;
                }
            } else if (byte1 < (byte) 0xF0) {
                // Three-byte form.
                if (index + 1 >= end) {
                    return false;
                }
                int byte2 = bytes[index++];
                if (byte2 > (byte) 0xBF
                        // Overlong? 5 most significant bits must not all be zero.
                        || (byte1 == (byte) 0xE0 && byte2 < (byte) 0xA0)
                        // Check for illegal surrogate codepoints.
                        || (byte1 == (byte) 0xED && (byte) 0xA0 <= byte2)
                        // Third byte trailing-byte test.
                        || bytes[index++] > (byte) 0xBF) {
                    return false;
                }
            } else {
                // Four-byte form.
                if (index + 2 >= end) {
                    return false;
                }
                int byte2 = bytes[index++];
                if (byte2 > (byte) 0xBF
                        // Check that 1 <= plane <= 16. Tricky optimized form of:
                        // if (byte1 > (byte) 0xF4
                        //     || byte1 == (byte) 0xF0 && byte2 < (byte) 0x90
                        //     || byte1 == (byte) 0xF4 && byte2 > (byte) 0x8F)
                        || (((byte1 << 28) + (byte2 - (byte) 0x90)) >> 30) != 0
                        // Third byte trailing-byte test
                        || bytes[index++] > (byte) 0xBF
                        // Fourth byte trailing-byte test
                        || bytes[index++] > (byte) 0xBF) {
                    return false;
                }
            }
        }
    }

    private static String unpairedSurrogateMsg(int i) {
        return "Unpaired surrogate at index " + i;
    }
}
