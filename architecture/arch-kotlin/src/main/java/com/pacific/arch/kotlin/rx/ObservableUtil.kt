package com.pacific.arch.kotlin.rx

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class ObservableUtil private constructor() {
    companion object {

        @JvmStatic
        fun <T> io() = ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> computation() = ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> trampoline() = ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> newThread() = ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> single() = ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> from(executor: Executor) = ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
