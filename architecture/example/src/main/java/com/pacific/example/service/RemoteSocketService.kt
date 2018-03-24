package com.pacific.example.service

import android.content.Intent
import android.content.res.Configuration
import android.os.*
import com.pacific.example.common.FLAG_LOCAL_MESSENGER
import com.pacific.example.common.FLAG_SOCKET_DATA
import dagger.android.DaggerService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.concurrent.TimeUnit

class RemoteSocketService : DaggerService() {
    private val disposables = CompositeDisposable()
    private val messenger = Messenger(IncomingHandler(this))
    private lateinit var remoteMessenger: Messenger

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
        disposables.add(Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.newThread())
                .doOnNext {
                    val bundle = Bundle()
                    bundle.putString("data", it.toString())
                    pushToRemote(bundle)
                }
                .subscribe())
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

    override fun onBind(intent: Intent?): IBinder = messenger.binder

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }

    private fun pushToRemote(data: Parcelable) {
        val msg = Message.obtain()
        msg.what = FLAG_SOCKET_DATA
        msg.obj = data
        msg.replyTo = messenger
        remoteMessenger.send(msg)
    }

    class IncomingHandler(private val service: RemoteSocketService) : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg!!.what) {
                FLAG_LOCAL_MESSENGER -> {
                    service.remoteMessenger = msg.replyTo
                }
                else -> {
                    super.handleMessage(msg)
                }
            }
        }
    }
}