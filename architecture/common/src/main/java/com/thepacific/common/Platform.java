/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.thepacific.common;

import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 * Methods factored out so that they can be emulated differently in GWT.
 *
 * @author Jesse Wilson
 */
final class Platform {
    private static final PatternCompiler patternCompiler = loadPatternCompiler();

    private Platform() {
    }

    /**
     * Calls {@link System#nanoTime()}.
     */
    static long systemNanoTime() {
        return System.nanoTime();
    }

    static String formatCompact4Digits(double value) {
        return String.format(Locale.ROOT, "%.4g", value);
    }

    static boolean stringIsNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    static CommonPattern compilePattern(String pattern) {
        Preconditions.checkNotNull(pattern);
        return patternCompiler.compile(pattern);
    }

    private static PatternCompiler loadPatternCompiler() {
    /*
     * We'd normally use ServiceLoader here, but it hurts Android startup performance. To avoid
     * that, we hardcode the JDK Pattern compiler on Android (and, inadvertently, on App Engine and
     * in Guava, at least for now).
     */
        return new JdkPatternCompiler();
    }

    private static final class JdkPatternCompiler implements PatternCompiler {
        @Override
        public CommonPattern compile(String pattern) {
            return new JdkPattern(Pattern.compile(pattern));
        }
    }
}
