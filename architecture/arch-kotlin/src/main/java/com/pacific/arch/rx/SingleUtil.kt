package com.pacific.arch.rx

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class SingleUtil private constructor() {
    companion object {
        fun <T> io() = SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        fun <T> computation() = SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        }

        fun <T> trampoline() = SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
        }

        fun <T> newThread() = SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }

        fun <T> single() = SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
        }

        fun <T> from(executor: Executor) = SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
        }
    }
}

