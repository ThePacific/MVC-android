package com.pacific.arch.rx

import android.annotation.SuppressLint
import com.pacific.arch.BuildConfig
import com.uber.autodispose.android.internal.AutoDisposeAndroidUtil
import io.reactivex.*
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

@SuppressLint("RestrictedApi")
fun isMainThread(): Boolean {
    return try {
        return AutoDisposeAndroidUtil.isMainThread()
    } catch (e: Exception) {
        true// Cover for tests
    }
}

fun verifyMainThread() {
    if (BuildConfig.DEBUG) {
        return// Cover for tests
    }
    MainThreadDisposable.verifyMainThread()
}

fun verifyWorkThread() {
    if (BuildConfig.DEBUG) {
        return // Cover for tests
    }
    if (isMainThread()) {
        throw UnsupportedOperationException("Can't run in MainThread")
    }
}

fun postToMainThread(runnable: Runnable) {
    AndroidSchedulers.mainThread().scheduleDirect(runnable)
}

fun <T> Single<T>.applyIo(): Single<T> {
    return this.compose({
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Single<T>.applyComputation(): Single<T> {
    return this.compose({
        this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Single<T>.applyNewThread(): Single<T> {
    return this.compose({
        this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Single<T>.applySingle(): Single<T> {
    return this.compose({
        this.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Single<T>.applyFrom(executor: Executor): Single<T> {
    return this.compose({
        this.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Observable<T>.applyIo(): Observable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Observable<T>.applyComputation(): Observable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Observable<T>.applyNewThread(): Observable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Observable<T>.applySingle(): Observable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Observable<T>.applyFrom(executor: Executor): Observable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Maybe<T>.applyIo(): Maybe<T> {
    return this.compose({
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Maybe<T>.applyComputation(): Maybe<T> {
    return this.compose({
        this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Maybe<T>.applyNewThread(): Maybe<T> {
    return this.compose({
        this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Maybe<T>.applySingle(): Maybe<T> {
    return this.compose({
        this.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Maybe<T>.applyFrom(executor: Executor): Maybe<T> {
    return this.compose({
        this.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Flowable<T>.applyIo(): Flowable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Flowable<T>.applyComputation(): Flowable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Flowable<T>.applyNewThread(): Flowable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Flowable<T>.applySingle(): Flowable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun <T> Flowable<T>.applyFrom(executor: Executor): Flowable<T> {
    return this.compose({
        this.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
    })
}

fun Completable.applyIo(): Completable {
    return this.compose({
        this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun Completable.applyComputation(): Completable {
    return this.compose({
        this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun Completable.applyNewThread(): Completable {
    return this.compose({
        this.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun Completable.applySingle(): Completable {
    return this.compose({
        this.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
    })
}

fun Completable.applyFrom(executor: Executor): Completable {
    return this.compose({
        this.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
    })
}