package com.pacific.arch.kotlin.rx

import io.reactivex.MaybeTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class MaybeUtil private constructor() {
    companion object {
        
        @JvmStatic
        fun <T> io() = MaybeTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> computation() = MaybeTransformer<T, T> {
            it.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> trampoline() = MaybeTransformer<T, T> {
            it.subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> newThread() = MaybeTransformer<T, T> {
            it.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> single() = MaybeTransformer<T, T> {
            it.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> from(executor: Executor) = MaybeTransformer<T, T> {
            it.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
