package com.pacific.example

import android.os.SystemClock
import android.util.Log
import com.pacific.arch.data.Source
import com.pacific.arch.example.App
import com.pacific.arch.rx.ObservableUtil
import com.pacific.arch.rx.verifyWorkThread
import io.reactivex.Observable
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {

    fun requestUser(): Observable<Source<String>> {
        return Observable
                .just("begin to get user from remote")
                .map {
                    verifyWorkThread()
                    Log.e("*******", it)
                    SystemClock.sleep(3000)
                    Log.e("*******", "done")
                    return@map Source.success(it)
                }
                .doOnDispose { Log.e("*******", "dispose") }
                .compose(ObservableUtil.io())

    }
}