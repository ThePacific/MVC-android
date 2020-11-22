package com.pacific.app.architecture.core.ext.rxjava3;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

public abstract class InitialValueObservable<T> extends Observable<T> {

    @Override
    protected final void subscribeActual(Observer<? super T> observer) {
        subscribeListener(observer);
        observer.onNext(getInitialValue());
    }

    protected abstract void subscribeListener(Observer<? super T> observer);

    protected abstract T getInitialValue();

    public final Observable<T> skipInitialValue() {
        return new Skipped();
    }

    private final class Skipped extends Observable<T> {

        Skipped() {
        }

        @Override
        protected void subscribeActual(Observer<? super T> observer) {
            subscribeListener(observer);
        }
    }
}
