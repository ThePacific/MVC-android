package com.pacific.example

import android.arch.lifecycle.*
import com.pacific.arch.example.App
import com.pacific.arch.rx.verifyWorkThread
import com.pacific.arch.views.compact.attachDebug
import com.pacific.example.base.CrashReportingTree
import com.pacific.example.common.DEBUG
import com.pacific.example.data.AppRepo
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInitializer @Inject constructor(private val app: App,
                                         private val appRepo: AppRepo) {
    val appLifecycle: PublishSubject<Lifecycle.Event> = PublishSubject.create()

    fun onAppCreate() {
        attachDebug(app, Runnable {
            verifyWorkThread()

            if (DEBUG) {
                Timber.plant(Timber.DebugTree())
            } else {
                Timber.plant(CrashReportingTree())
            }

            ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
                fun onAppLifecycle(@SuppressWarnings("unused") owner: LifecycleOwner, event: Lifecycle.Event) {
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