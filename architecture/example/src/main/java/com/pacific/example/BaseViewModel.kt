package com.pacific.example

import android.arch.lifecycle.AndroidViewModel
import com.pacific.arch.example.App
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel constructor(app: App) : AndroidViewModel(app) {
    @JvmField
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}