package com.thepacific.mvp;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.subjects.BehaviorSubject;

public interface MvpView {
    BehaviorSubject<DisposeEvent> lifecycle();
    AppCompatActivity context();
}
