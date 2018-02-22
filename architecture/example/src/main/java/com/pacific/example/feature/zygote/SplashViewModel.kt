package com.pacific.example.feature.zygote

import android.app.Application
import android.os.SystemClock
import com.pacific.arch.rx.CompletableUtil
import com.pacific.example.base.RxAwareViewModel
import io.reactivex.Completable
import javax.inject.Inject

class SplashViewModel @Inject constructor(app: Application) : RxAwareViewModel(app) {
    fun init(): Completable {
        return Completable
                .fromAction({
                    SystemClock.sleep(3000)
                })
                .compose(CompletableUtil.newThread())
    }
}