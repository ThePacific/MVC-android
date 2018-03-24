package com.pacific.example.base

import android.arch.lifecycle.AndroidViewModel
import com.pacific.example.App
import io.reactivex.disposables.CompositeDisposable

abstract class RxAwareViewModel constructor(app: App) : AndroidViewModel(app) {
    @JvmField
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}