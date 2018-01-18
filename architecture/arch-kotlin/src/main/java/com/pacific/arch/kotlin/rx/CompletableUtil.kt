package com.pacific.arch.kotlin.rx

import io.reactivex.CompletableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class CompletableUtil private constructor() {
    companion object {

        @JvmStatic
        fun io() = CompletableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun computation() = CompletableTransformer {
            it.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun trampoline() = CompletableTransformer {
            it.subscribeOn(Schedulers.trampoline()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun newThread() = CompletableTransformer {
            it.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun single() = CompletableTransformer {
            it.subscribeOn(Schedulers.single()).observeOn(AndroidSchedulers.mainThread())
        }

        @JvmStatic
        fun from(executor: Executor) = CompletableTransformer {
            it.subscribeOn(Schedulers.from(executor)).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
