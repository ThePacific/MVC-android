package com.pacific.example.service

import android.content.Intent
import android.content.res.Configuration
import android.os.Binder
import android.os.IBinder
import com.pacific.example.data.SOCKET_SERVICE
import dagger.android.DaggerService
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.concurrent.TimeUnit

class LocalSocketService : DaggerService() {

    private val binder: IBinder = InnerBinder(this)

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun dump(fd: FileDescriptor?, writer: PrintWriter?, args: Array<out String>?) {
        super.dump(fd, writer, args)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .doOnNext {
                    SOCKET_SERVICE.onNext(it.toString())
                }
                .subscribe()
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    class InnerBinder(val service: LocalSocketService) : Binder()
}