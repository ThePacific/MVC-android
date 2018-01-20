package com.pacific.arch.gauva;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.pacific.arch.gauva.Preconditions2.checkArgument;
import static com.pacific.arch.gauva.Preconditions2.checkNotNull;

public final class Splitter {

    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;

    private Splitter(Strategy strategy) {
        this(strategy, false, CharMatcher.none(), Integer.MAX_VALUE);
    }

    private Splitter(Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer, int limit) {
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
        this.limit = limit;
    }

    public static Splitter on(char separator) {
        return on(CharMatcher.is(separator));
    }

    public static Splitter on(final CharMatcher separatorMatcher) {
        checkNotNull(separatorMatcher);

        return new Splitter(
                new Strategy() {
                    @Override
                    public SplittingIterator iterator(Splitter splitter, final CharSequence toSplit) {
                        return new SplittingIterator(splitter, toSplit) {
                            @Override
                            int separatorStart(int start) {
                                return separatorMatcher.indexIn(toSplit, start);
                            }

                            @Override
                            int separatorEnd(int separatorPosition) {
                                return separatorPosition + 1;
                            }
                        };
                    }
                });
    }

    public static Splitter on(final String separator) {
        checkArgument(separator.length() != 0, "The separator may not be the empty string.");
        if (separator.length() == 1) {
            return Splitter.on(separator.charAt(0));
        }
        return new Splitter(
                new Strategy() {
                    @Override
                    public SplittingIterator iterator(Splitter splitter, CharSequence toSplit) {
                        return new SplittingIterator(splitter, toSplit) {
                            @Override
                            public int separatorStart(int start) {
                                int separatorLength = separator.length();

                                positions:
                                for (int p = start, last = toSplit.length() - separatorLength; p <= last; p++) {
                                    for (int i = 0; i < separatorLength; i++) {
                                        if (toSplit.charAt(i + p) != separator.charAt(i)) {
                                            continue positions;
                                        }
                                    }
                                    return p;
                                }
                                return -1;
                            }

                            @Override
                            public int separatorEnd(int separatorPosition) {
                                return separatorPosition + separator.length();
                            }
                        };
                    }
                });
    }

    public static Splitter on(Pattern separatorPattern) {
        return on(new JdkPattern(separatorPattern));
    }

    private static Splitter on(final JdkPattern jdkPattern) {
        checkArgument(!jdkPattern.matcher("").matches(), jdkPattern);
        return new Splitter(
                new Strategy() {
                    @Override
                    public SplittingIterator iterator(final Splitter splitter, CharSequence toSplit) {
                        final JdkPattern.JdkMatcher matcher = jdkPattern.matcher(toSplit);
                        return new SplittingIterator(splitter, toSplit) {
                            @Override
                            public int separatorStart(int start) {
                                return matcher.find(start) ? matcher.start() : -1;
                            }

                            @Override
                            public int separatorEnd(int separatorPosition) {
                                return matcher.end();
                            }
                        };
                    }
                });
    }

    public static Splitter onPattern(String separatorPattern) {
        return on(new JdkPattern(Pattern.compile(separatorPattern)));
    }

    public static Splitter fixedLength(final int length) {
        checkArgument(length > 0, "The length may not be less than 1");
        return new Splitter(
                new Strategy() {
                    @Override
                    public SplittingIterator iterator(final Splitter splitter, CharSequence toSplit) {
                        return new SplittingIterator(splitter, toSplit) {
                            @Override
                            public int separatorStart(int start) {
                                int nextChunkStart = start + length;
                                return (nextChunkStart < toSplit.length() ? nextChunkStart : -1);
                            }

                            @Override
                            public int separatorEnd(int separatorPosition) {
                                return separatorPosition;
                            }
                        };
                    }
                });
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(strategy, true, trimmer, limit);
    }

    public Splitter limit(int limit) {
        checkArgument(limit > 0, limit);
        return new Splitter(strategy, omitEmptyStrings, trimmer, limit);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.whitespace());
    }

    public Splitter trimResults(CharMatcher trimmer) {
        checkNotNull(trimmer);
        return new Splitter(strategy, omitEmptyStrings, trimmer, limit);
    }

    public Iterable<String> split(final CharSequence sequence) {
        checkNotNull(sequence);
        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return splittingIterator(sequence);
            }

            @Override
            public String toString() {
                return Joiner.on(", ")
                        .appendTo(new StringBuilder().append('['), this)
                        .append(']')
                        .toString();
            }
        };
    }

    private Iterator<String> splittingIterator(CharSequence sequence) {
        return strategy.iterator(this, sequence);
    }

    public List<String> splitToList(CharSequence sequence) {
        checkNotNull(sequence);

        Iterator<String> iterator = splittingIterator(sequence);
        List<String> result = new ArrayList<String>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return Collections.unmodifiableList(result);
    }

    public MapSplitter withKeyValueSeparator(String separator) {
        return withKeyValueSeparator(on(separator));
    }

    public MapSplitter withKeyValueSeparator(char separator) {
        return withKeyValueSeparator(on(separator));
    }

    public MapSplitter withKeyValueSeparator(Splitter keyValueSplitter) {
        return new MapSplitter(this, keyValueSplitter);
    }

    private interface Strategy {

        Iterator<String> iterator(Splitter splitter, CharSequence toSplit);
    }

    public static final class MapSplitter {

        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter outerSplitter;
        private final Splitter entrySplitter;

        private MapSplitter(Splitter outerSplitter, Splitter entrySplitter) {
            this.outerSplitter = outerSplitter; // only "this" is passed
            this.entrySplitter = checkNotNull(entrySplitter);
        }

        public Map<String, String> split(CharSequence sequence) {
            Map<String, String> map = new LinkedHashMap<String, String>();
            for (String entry : outerSplitter.split(sequence)) {
                Iterator<String> entryFields = entrySplitter.splittingIterator(entry);

                checkArgument(entryFields.hasNext(), entry);
                String key = entryFields.next();
                checkArgument(!map.containsKey(key), key);

                checkArgument(entryFields.hasNext(), entry);
                String value = entryFields.next();
                map.put(key, value);

                checkArgument(!entryFields.hasNext(), entry);
            }
            return Collections.unmodifiableMap(map);
        }
    }

    private abstract static class SplittingIterator extends AbstractIterator<String> {

        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyStrings;
        int offset = 0;
        int limit;

        protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyStrings = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }

        abstract int separatorStart(int start);

        abstract int separatorEnd(int separatorPosition);

        @Override
        protected String computeNext() {
            /*
             * The returned string will be from the end of the last match to the beginning of the next
             * one. nextStart is the start position of the returned substring, while offset is the place
             * to start looking for a separator.
             */
            int nextStart = offset;
            while (offset != -1) {
                int start = nextStart;
                int end;

                int separatorPosition = separatorStart(offset);
                if (separatorPosition == -1) {
                    end = toSplit.length();
                    offset = -1;
                } else {
                    end = separatorPosition;
                    offset = separatorEnd(separatorPosition);
                }
                if (offset == nextStart) {
                    /*
                     * This occurs when some pattern has an empty match, even if it doesn't match the empty
                     * string -- for example, if it requires lookahead or the like. The offset must be
                     * increased to look for separators beyond this point, without changing the start position
                     * of the next returned substring -- so nextStart stays the same.
                     */
                    offset++;
                    if (offset > toSplit.length()) {
                        offset = -1;
                    }
                    continue;
                }

                while (start < end && trimmer.matches(toSplit.charAt(start))) {
                    start++;
                }
                while (end > start && trimmer.matches(toSplit.charAt(end - 1))) {
                    end--;
                }

                if (omitEmptyStrings && start == end) {
                    // Don't include the (unused) separator in next split string.
                    nextStart = offset;
                    continue;
                }

                if (limit == 1) {
                    // The limit has been reached, return the rest of the string as the
                    // final item. This is tested after empty string removal so that
                    // empty strings do not count towards the limit.
                    end = toSplit.length();
                    offset = -1;
                    // Since we may have changed the end, we need to trim it again.
                    while (end > start && trimmer.matches(toSplit.charAt(end - 1))) {
                        end--;
                    }
                } else {
                    limit--;
                }

                return toSplit.subSequence(start, end).toString();
            }
            return endOfData();
        }
    }
}
