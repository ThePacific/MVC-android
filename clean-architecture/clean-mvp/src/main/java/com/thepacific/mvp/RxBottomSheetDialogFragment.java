package com.thepacific.mvp;

import android.os.Bundle;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.RxLifecycle;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class RxBottomSheetDialogFragment extends BottomSheetDialogFragment implements LifecycleProvider<DisposeEvent> {

    private final BehaviorSubject<DisposeEvent> lifecycle = BehaviorSubject.create();

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!view.isClickable()) view.setClickable(true);
    }

    @Override
    public void onPause() {
        lifecycle.onNext(DisposeEvent.FRAGMENT_PAUSE);
        super.onPause();
    }

    @Override
    public void onStop() {
        lifecycle.onNext(DisposeEvent.FRAGMENT_STOP);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        lifecycle.onNext(DisposeEvent.FRAGMENT_DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        lifecycle.onNext(DisposeEvent.FRAGMENT_DESTROY);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        lifecycle.onNext(DisposeEvent.FRAGMENT_DETACH);
        super.onDetach();
    }
}
