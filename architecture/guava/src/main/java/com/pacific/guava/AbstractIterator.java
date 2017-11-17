package com.pacific.guava;

import static com.pacific.guava.Preconditions.checkState;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

abstract class AbstractIterator<T> implements Iterator<T> {

  private State state = State.NOT_READY;

  protected AbstractIterator() {
  }

  private enum State {
    READY,
    NOT_READY,
    DONE,
    FAILED,
  }

  private T next;

  protected abstract T computeNext();

  @Nullable
  @CanIgnoreReturnValue
  protected final T endOfData() {
    state = State.DONE;
    return null;
  }

  @Override
  public final boolean hasNext() {
    checkState(state != State.FAILED);
    switch (state) {
      case READY:
        return true;
      case DONE:
        return false;
      default:
    }
    return tryToComputeNext();
  }

  private boolean tryToComputeNext() {
    state = State.FAILED; // temporary pessimism
    next = computeNext();
    if (state != State.DONE) {
      state = State.READY;
      return true;
    }
    return false;
  }

  @Override
  public final T next() {
    if (!hasNext()) {
      throw new NoSuchElementException();
    }
    state = State.NOT_READY;
    T result = next;
    next = null;
    return result;
  }

  @Override
  public final void remove() {
    throw new UnsupportedOperationException();
  }
}
