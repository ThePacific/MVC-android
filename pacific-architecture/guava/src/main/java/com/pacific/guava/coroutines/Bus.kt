package com.pacific.guava.coroutines

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

object Bus {

    private val channel = BroadcastChannel<Pair<Int, Any>>(1)

    fun offer(id: Int, data: Any) = channel.offer(Pair(id, data))

    fun offer(id: Int) = channel.offer(Pair(id, Unit))

    fun subscribe(): Flow<Pair<Int, Any>> = channel.openSubscription().consumeAsFlow()
}