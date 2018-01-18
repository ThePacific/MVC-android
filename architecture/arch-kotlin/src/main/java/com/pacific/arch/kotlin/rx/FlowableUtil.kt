package com.pacific.arch.kotlin.rx

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class FlowableUtil private constructor() {
    companion object {

        @JvmStatic
        fun <T> io() = FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> computation() = FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> trampoline() = FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> newThread() = FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> single() = FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun <T> from(executor: Executor) = FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
