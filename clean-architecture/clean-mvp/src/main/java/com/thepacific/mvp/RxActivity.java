package com.thepacific.mvp;

import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.v7.app.AppCompatActivity;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.BehaviorSubject;

public class RxActivity extends AppCompatActivity implements LifecycleProvider<DisposeEvent> {

    protected final BehaviorSubject<DisposeEvent> lifecycle = BehaviorSubject.create();

    @Override
    @NonNull
    @CheckResult
    public final Observable<DisposeEvent> lifecycle() {
        return lifecycle;
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {
        throw new AssertionError("Not supported");
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull DisposeEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycle, event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        AppLife.attachOnStart();
    }

    @Override
    @CallSuper
    protected void onPause() {
        lifecycle.onNext(DisposeEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    protected void onStop() {
        lifecycle.onNext(DisposeEvent.STOP);
        super.onStop();
        AppLife.detachOnStop();
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        lifecycle.onNext(DisposeEvent.DESTROY);
        super.onDestroy();
    }
}
