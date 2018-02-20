package com.pacific.example

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable

open class RxAwareViewModel constructor(app: Application) : AndroidViewModel(app) {
    @JvmField
    val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}