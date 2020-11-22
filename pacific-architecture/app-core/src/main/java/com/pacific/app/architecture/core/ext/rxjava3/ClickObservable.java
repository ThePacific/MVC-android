package com.pacific.app.architecture.core.ext.rxjava3;

import android.view.View;
import android.view.View.OnClickListener;

import io.reactivex.rxjava3.android.MainThreadDisposable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;

final class ClickObservable extends Observable<View> {

    private final View view;

    ClickObservable(View view) {
        this.view = view;
    }

    @Override
    protected void subscribeActual(Observer<? super View> observer) {
        MainThreadDisposable.verifyMainThread();
        Listener listener = new Listener(view, observer);
        observer.onSubscribe(listener);
        view.setOnClickListener(listener);
    }

    static final class Listener extends MainThreadDisposable implements OnClickListener {

        private final View view;
        private final Observer<? super View> observer;

        Listener(View view, Observer<? super View> observer) {
            this.view = view;
            this.observer = observer;
        }

        @Override
        public void onClick(View v) {
            if (!isDisposed()) {
                observer.onNext(view);
            }
        }

        @Override
        protected void onDispose() {
            view.setOnClickListener(null);
        }
    }
}
