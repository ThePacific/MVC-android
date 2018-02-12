package com.pacific.arch.gauva;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class JdkPattern implements Serializable {

    private static final long serialVersionUID = 0;
    private final Pattern pattern;

    JdkPattern(Pattern pattern) {
        this.pattern = Preconditions2.checkNotNull(pattern);
    }

    public static boolean andLogic(String source, int min, int max) {
        Preconditions2.checkNotNull(source);
        Preconditions2.checkState(min > 0 && max > 0 && max > min);
        String regex = Phrase3.format("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{<min>,<max>}",
                "<",
                ">")
                .with("min", String.valueOf(min))
                .with("max", String.valueOf(max))
                .build();
        return source.matches(regex);
    }

    public static boolean orLogic(String source, int min, int max) {
        Preconditions2.checkNotNull(source);
        Preconditions2.checkState(min > 0 && max > 0 && max > min);
        String regex = Phrase3.format("[0-9A-Za-z]{<min>,<max>}",
                "<",
                ">")
                .with("min", String.valueOf(min))
                .with("max", String.valueOf(max))
                .build();
        return source.matches(regex);
    }

    JdkMatcher matcher(CharSequence t) {
        return new JdkMatcher(pattern.matcher(t));
    }

    String pattern() {
        return pattern.pattern();
    }

    int flags() {
        return pattern.flags();
    }

    @Override
    public String toString() {
        return pattern.toString();
    }

    @Override
    public int hashCode() {
        return pattern.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof JdkPattern)) {
            return false;
        }
        return pattern.equals(((JdkPattern) o).pattern);
    }

    static final class JdkMatcher {

        final Matcher matcher;

        JdkMatcher(Matcher matcher) {
            this.matcher = Preconditions2.checkNotNull(matcher);
        }

        boolean matches() {
            return matcher.matches();
        }

        boolean find() {
            return matcher.find();
        }

        boolean find(int index) {
            return matcher.find(index);
        }

        String replaceAll(String replacement) {
            return matcher.replaceAll(replacement);
        }

        int end() {
            return matcher.end();
        }

        int start() {
            return matcher.start();
        }
    }
}