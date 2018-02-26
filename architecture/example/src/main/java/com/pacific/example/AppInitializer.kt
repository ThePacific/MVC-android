package com.pacific.example

import android.arch.lifecycle.*
import com.pacific.arch.example.App
import com.pacific.arch.rx.verifyWorkThread
import com.pacific.arch.views.compact.attachDebug
import com.pacific.example.base.CrashReportingTree
import com.pacific.example.common.DEBUG_APP
import com.pacific.example.data.SystemRepo
import com.squareup.moshi.Moshi
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializer @Inject constructor(private val app: App,
                                         private val systemRepo: SystemRepo,
                                         private val moshi: Moshi) {
    val appLifecycle: PublishSubject<Lifecycle.Event> = PublishSubject.create()

    fun onAppCreate() {
        attachDebug(app, Runnable {
            verifyWorkThread()

            if (DEBUG_APP) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(CrashReportingTree())
            }

            ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
                fun onAppLifecycle(owner: LifecycleOwner, event: Lifecycle.Event) {
                    Timber.i("App current state -> %s ", owner.lifecycle.currentState)
                    if (event == Lifecycle.Event.ON_START || event == Lifecycle.Event.ON_STOP) {
                        appLifecycle.onNext(event)
                    }
                }
            })
        })
    }

    fun onAppTerminate() {
        appLifecycle.onComplete()
    }
}