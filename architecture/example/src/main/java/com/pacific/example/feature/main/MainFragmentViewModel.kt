package com.pacific.example

import android.os.SystemClock
import com.pacific.arch.data.Source
import com.pacific.arch.example.App
import com.pacific.arch.rx.applyIo
import com.pacific.arch.rx.verifyWorkThread
import com.pacific.example.base.RxAwareViewModel
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {

    fun requestUser(): Observable<Source<String>> {
        return Observable
                .just("begin to get user from remote")
                .map {
                    verifyWorkThread()
                    Timber.e(it)
                    SystemClock.sleep(3000)
                    return@map Source.success(it)
                }
                .doOnDispose { Timber.e("dispose") }
                .onErrorReturn { Source.failure(it) }
                .applyIo()
                .startWith(Source.inProgress())
    }
}