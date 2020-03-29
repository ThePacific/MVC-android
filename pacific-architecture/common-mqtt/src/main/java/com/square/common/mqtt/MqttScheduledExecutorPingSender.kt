package com.square.common.mqtt

import org.eclipse.paho.client.mqttv3.ScheduledExecutorPingSender
import org.eclipse.paho.client.mqttv3.internal.ClientComms
import java.util.concurrent.ScheduledExecutorService

internal class MqttScheduledExecutorPingSender(
    executorService: ScheduledExecutorService
) : ScheduledExecutorPingSender(executorService) {

    lateinit var clientCommands: ClientComms
        private set

    fun isCommandsAttached(): Boolean = ::clientCommands.isInitialized

    override fun init(comms: ClientComms?) {
        super.init(comms)
        this.clientCommands = comms!!
    }
}