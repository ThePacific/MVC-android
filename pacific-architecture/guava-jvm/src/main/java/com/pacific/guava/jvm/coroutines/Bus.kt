package com.pacific.guava.jvm.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

/**
 * kotlin版本的EventBus
 */
object Bus {

    @ExperimentalCoroutinesApi
    private val channel = BroadcastChannel<Pair<Int, Any>>(1)

    /**
     * 发送消息
     */
    @ExperimentalCoroutinesApi
    fun offer(id: Int, data: Any) = channel.trySend(Pair(id, data))

    /**
     * 发送消息
     */
    @ExperimentalCoroutinesApi
    fun offer(id: Int) = channel.trySend(Pair(id, Unit))

    /**
     * 订阅
     */
    @ExperimentalCoroutinesApi
    fun subscribe(): Flow<Pair<Int, Any>> = channel.openSubscription().consumeAsFlow()
}