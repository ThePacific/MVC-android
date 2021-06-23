package com.pacific.guava.jvm.executor

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * 预定义几种线程对象池
 */

val scheduledExecutorService: ScheduledExecutorService by lazy {
    Executors.newScheduledThreadPool(4)
}

val singleThreadExecutor: ExecutorService by lazy {
    Executors.newSingleThreadExecutor()
}

val cachedThreadPool: ExecutorService by lazy {
    Executors.newCachedThreadPool()
}

val fixedThreadPool: ExecutorService by lazy {
    Executors.newFixedThreadPool(4)
}