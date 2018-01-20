package com.pacific.arch.gauva;

import android.support.annotation.Nullable;

import com.google.errorprone.annotations.CanIgnoreReturnValue;

import java.io.IOException;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.pacific.arch.gauva.Preconditions2.checkNotNull;

public class Joiner {

    private final String separator;

    private Joiner(String separator) {
        this.separator = checkNotNull(separator);
    }

    private Joiner(Joiner prototype) {
        this.separator = prototype.separator;
    }

    public static Joiner on(String separator) {
        return new Joiner(separator);
    }

    public static Joiner on(char separator) {
        return new Joiner(String.valueOf(separator));
    }

    private static Iterable<Object> iterable(final Object first, final Object second, final Object[] rest) {
        checkNotNull(rest);
        return new AbstractList<Object>() {
            @Override
            public int size() {
                return rest.length + 2;
            }

            @Override
            public Object get(int index) {
                switch (index) {
                    case 0:
                        return first;
                    case 1:
                        return second;
                    default:
                        return rest[index - 2];
                }
            }
        };
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A appendable, Iterable<?> parts) throws IOException {
        return appendTo(appendable, parts.iterator());
    }

    @CanIgnoreReturnValue
    public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
        checkNotNull(appendable);
        if (parts.hasNext()) {
            appendable.append(toString(parts.next()));
            while (parts.hasNext()) {
                appendable.append(separator);
                appendable.append(toString(parts.next()));
            }
        }
        return appendable;
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(A appendable, Object[] parts) throws IOException {
        return appendTo(appendable, Arrays.asList(parts));
    }

    @CanIgnoreReturnValue
    public final <A extends Appendable> A appendTo(
            A appendable, @Nullable Object first, @Nullable Object second, Object... rest)
            throws IOException {
        return appendTo(appendable, iterable(first, second, rest));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts) {
        return appendTo(builder, parts.iterator());
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts) {
        try {
            appendTo((Appendable) builder, parts);
        } catch (IOException impossible) {
            throw new AssertionError(impossible);
        }
        return builder;
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(StringBuilder builder, Object[] parts) {
        return appendTo(builder, Arrays.asList(parts));
    }

    @CanIgnoreReturnValue
    public final StringBuilder appendTo(
            StringBuilder builder, @Nullable Object first, @Nullable Object second, Object... rest) {
        return appendTo(builder, iterable(first, second, rest));
    }

    public final String join(Iterable<?> parts) {
        return join(parts.iterator());
    }

    public final String join(Iterator<?> parts) {
        return appendTo(new StringBuilder(), parts).toString();
    }

    public final String join(Object[] parts) {
        return join(Arrays.asList(parts));
    }

    public final String join(@Nullable Object first, @Nullable Object second, Object... rest) {
        return join(iterable(first, second, rest));
    }

    public Joiner useForNull(final String nullText) {
        checkNotNull(nullText);
        return new Joiner(this) {
            @Override
            CharSequence toString(@Nullable Object part) {
                return (part == null) ? nullText : Joiner.this.toString(part);
            }

            @Override
            public Joiner useForNull(String nullText) {
                throw new UnsupportedOperationException("already specified useForNull");
            }

            @Override
            public Joiner skipNulls() {
                throw new UnsupportedOperationException("already specified useForNull");
            }
        };
    }

    public Joiner skipNulls() {
        return new Joiner(this) {
            @Override
            public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts) throws IOException {
                checkNotNull(appendable, "appendable");
                checkNotNull(parts, "parts");
                while (parts.hasNext()) {
                    Object part = parts.next();
                    if (part != null) {
                        appendable.append(Joiner.this.toString(part));
                        break;
                    }
                }
                while (parts.hasNext()) {
                    Object part = parts.next();
                    if (part != null) {
                        appendable.append(separator);
                        appendable.append(Joiner.this.toString(part));
                    }
                }
                return appendable;
            }

            @Override
            public Joiner useForNull(String nullText) {
                throw new UnsupportedOperationException("already specified skipNulls");
            }

            @Override
            public MapJoiner withKeyValueSeparator(String kvs) {
                throw new UnsupportedOperationException("can't use .skipNulls() with maps");
            }
        };
    }

    public MapJoiner withKeyValueSeparator(char keyValueSeparator) {
        return withKeyValueSeparator(String.valueOf(keyValueSeparator));
    }

    public MapJoiner withKeyValueSeparator(String keyValueSeparator) {
        return new MapJoiner(this, keyValueSeparator);
    }

    CharSequence toString(Object part) {
        checkNotNull(part);
        return (part instanceof CharSequence) ? (CharSequence) part : part.toString();
    }

    public static final class MapJoiner {

        private final Joiner joiner;
        private final String keyValueSeparator;

        private MapJoiner(Joiner joiner, String keyValueSeparator) {
            this.joiner = joiner; // only "this" is ever passed, so don't checkNotNull
            this.keyValueSeparator = checkNotNull(keyValueSeparator);
        }

        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A appendable, Map<?, ?> map) throws IOException {
            return appendTo(appendable, map.entrySet());
        }

        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map) {
            return appendTo(builder, map.entrySet());
        }

        public String join(Map<?, ?> map) {
            return join(map.entrySet());
        }

        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A appendable, Iterable<? extends Entry<?, ?>> entries)
                throws IOException {
            return appendTo(appendable, entries.iterator());
        }

        @CanIgnoreReturnValue
        public <A extends Appendable> A appendTo(A appendable, Iterator<? extends Entry<?, ?>> parts)
                throws IOException {
            checkNotNull(appendable);
            if (parts.hasNext()) {
                Entry<?, ?> entry = parts.next();
                appendable.append(joiner.toString(entry.getKey()));
                appendable.append(keyValueSeparator);
                appendable.append(joiner.toString(entry.getValue()));
                while (parts.hasNext()) {
                    appendable.append(joiner.separator);
                    Entry<?, ?> e = parts.next();
                    appendable.append(joiner.toString(e.getKey()));
                    appendable.append(keyValueSeparator);
                    appendable.append(joiner.toString(e.getValue()));
                }
            }
            return appendable;
        }

        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder builder, Iterable<? extends Entry<?, ?>> entries) {
            return appendTo(builder, entries.iterator());
        }

        @CanIgnoreReturnValue
        public StringBuilder appendTo(StringBuilder builder, Iterator<? extends Entry<?, ?>> entries) {
            try {
                appendTo((Appendable) builder, entries);
            } catch (IOException impossible) {
                throw new AssertionError(impossible);
            }
            return builder;
        }

        public String join(Iterable<? extends Entry<?, ?>> entries) {
            return join(entries.iterator());
        }

        public String join(Iterator<? extends Entry<?, ?>> entries) {
            return appendTo(new StringBuilder(), entries).toString();
        }

        public MapJoiner useForNull(String nullText) {
            return new MapJoiner(joiner.useForNull(nullText), keyValueSeparator);
        }
    }
}
