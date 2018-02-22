package com.pacific.example.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class RxAwareViewModel constructor(app: Application) : AndroidViewModel(app) {
    @JvmField
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}