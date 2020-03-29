package com.square.common.mqtt

import androidx.annotation.WorkerThread
import com.square.core.isMqttConnected
import com.square.core.isNetworkConnected
import com.square.guava.android.log.getRandomUUID
import com.square.guava.android.verifyWorkThread
import com.square.guava.coroutines.ControlledRunner
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object MqttManager : MqttCallback, IMqttActionListener {

    private val controlledRunner = ControlledRunner<Unit>()

    private lateinit var loginName: String
    private lateinit var loginPassword: String
    private lateinit var urls: Array<String>
    private lateinit var scheduledExecutorService: ScheduledExecutorService
    private lateinit var connectOptions: MqttConnectOptions
    private lateinit var client: MqttAsyncClient
    private lateinit var pingSender: MqttScheduledExecutorPingSender
    private lateinit var qosArray: IntArray

    private var connectSuccessCount = 0L
    private var connectFailCount = 0L
    private var disconnectSuccessCount = 0L
    private var disconnectFailCount = 0L
    private var subscribeSuccessCount = 0L
    private var subscribeFailCount = 0L
    private var unsubscribeSuccessCount = 0L
    private var unsubscribeFailCount = 0L
    private var connectionLostCount = 0L
    private var preTopicFilters = emptyArray<String>()

    fun initialize(loginName: String, loginPassword: String, urls: Array<String>) {
        this.loginName = loginName
        this.loginPassword = loginPassword
        this.urls = urls
        this.qosArray = createQOS()

        this.scheduledExecutorService = Executors.newScheduledThreadPool(
            4,
            object : ThreadFactory {

                private val threadId = AtomicInteger(0)

                override fun newThread(r: Runnable): Thread {
                    return Thread(r).apply {
                        name = "MQTT-%${threadId.getAndIncrement()}"
                    }
                }
            }
        )
        this.connectOptions = MqttConnectOptions().apply {
            isAutomaticReconnect = false
            isCleanSession = true
            userName = MqttManager.loginName
            password = MqttManager.loginPassword.toCharArray()
            keepAliveInterval = 60 * 1
            connectionTimeout = 10
            maxReconnectDelay = 30000
            maxInflight = 5
            serverURIs = MqttManager.urls
            mqttVersion = MqttConnectOptions.MQTT_VERSION_3_1_1
        }
        this.pingSender = MqttScheduledExecutorPingSender(scheduledExecutorService)
        this.client = MqttAsyncClient(
            MqttManager.urls.first(),
            "Android" + getRandomUUID(),
            MemoryPersistence(),
            pingSender,
            scheduledExecutorService
        )
        this.client.setCallback(this)

        isNetworkConnected.observeForever {
            if (it == true) {
                reconnect(0)
            }
        }
    }

    fun post(message: OkMqttMessage) {
        GlobalScope.launch {
            controlledRunner.cancelPreviousThenRun {
                try {
                    publish(message)
                } catch (e: Exception) {
                    log(e)
                }
            }
        }
    }

    private fun reconnect(delayTimeMillis: Long) {
        GlobalScope.launch {
            controlledRunner.cancelPreviousThenRun {
                if (delayTimeMillis > 0) {
                    delay(delayTimeMillis)
                }
                try {
                    connect()
                } catch (e: Exception) {
                    log(e)
                }
            }
        }
    }

    private fun resubscribe() {
        GlobalScope.launch {
            controlledRunner.cancelPreviousThenRun {
                if (preTopicFilters.isNotEmpty()) {
                    unsubscribe()
                }
                subscribe()
            }
        }
    }

    @WorkerThread
    private fun connect() {
        verifyWorkThread()
        if (isNetworkConnected.value != true) {
            log("isNetworkConnected = false, skip to connect here ")
            return
        }
        if (pingSender.isCommandsAttached()) {
            if (pingSender.clientCommands.isConnecting) {
                log("Already connecting, skip to connect here")
                return
            }
            if (pingSender.clientCommands.isConnected) {
                log("Already connected, skip to connect here")
                return
            }
        }
        log("Connecting......")
        connectOptions.serverURIs = arrayOf(resolveFastestURL())
        client.connect(connectOptions, MQTT_CONNECT_TOKEN, this)
    }

    @WorkerThread
    private fun subscribe() {
        verifyWorkThread()
        val topicFilters = createTopicFilters()
        log("subscribe()->${topicFilters.joinToString(", ", "[", "]")}")
        client.subscribe(topicFilters, qosArray, MQTT_SUBSCRIBE_TOKEN, this)
    }

    @WorkerThread
    private fun unsubscribe() {
        verifyWorkThread()
        log("unsubscribe()->${preTopicFilters.joinToString(", ", "[", "]")}")
        client.unsubscribe(preTopicFilters, MQTT_UNSUBSCRIBE_TOKEN, this)
    }

    @WorkerThread
    private fun publish(message: OkMqttMessage) {
        verifyWorkThread()
        val tag = message.topics[0]
        log("publish()->$tag:${message.payload.size}")
        client.publish(message.topics[0], message, MQTT_PUBLISH_TOKEN, this)
    }

    override fun onSuccess(asyncActionToken: IMqttToken) {
        when (val flag = asyncActionToken.userContext as Int) {
            MQTT_CONNECT_TOKEN -> {
                log("Connect successful [${client.currentServerURI}]")
                connectSuccessCount++
                connectFailCount = 0
                // No isMqttConnected.postValue(true) here.
                // It's better to call after subscribe()
                resubscribe()
            }
            MQTT_DISCONNECT_TOKEN -> {
                log("Disconnect successful")
                disconnectSuccessCount++
                disconnectFailCount = 0
                isMqttConnected.postValue(false)
            }
            MQTT_SUBSCRIBE_TOKEN -> {
                log("Subscribe successful")
                subscribeSuccessCount++
                subscribeFailCount = 0
                preTopicFilters = asyncActionToken.topics
                isMqttConnected.postValue(true)
            }
            MQTT_UNSUBSCRIBE_TOKEN -> {
                log("Unsubscribe successful")
                unsubscribeSuccessCount++
                unsubscribeFailCount = 0
            }
            MQTT_PUBLISH_TOKEN -> {
                log("Publish successful")
            }
            else -> log("Unknown onSuccess $flag")
        }
    }

    override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
        when (val flag = asyncActionToken.userContext as Int) {
            MQTT_CONNECT_TOKEN -> {
                log("Connect failed")
                connectFailCount++
                connectSuccessCount = 0
                reconnect(connectFailCount * 2000L)
            }
            MQTT_DISCONNECT_TOKEN -> {
                log("Disconnect failed")
                disconnectFailCount++
                disconnectSuccessCount = 0
                isMqttConnected.postValue(false)
            }
            MQTT_SUBSCRIBE_TOKEN -> {
                log("Subscribe failed")
                subscribeFailCount++
                subscribeSuccessCount = 0
                isMqttConnected.postValue(true)
            }
            MQTT_UNSUBSCRIBE_TOKEN -> {
                log("Unsubscribe failed")
                unsubscribeFailCount++
                unsubscribeSuccessCount = 0
            }
            MQTT_PUBLISH_TOKEN -> {
                log("Publish failed")
            }
            else -> log("Unknown onFailure $flag")
        }

        log(exception)
    }

    override fun messageArrived(topic: String, message: MqttMessage) {
        verifyWorkThread()
        log("messageArrived()->$topic size->${message.payload.size}")
    }

    override fun connectionLost(cause: Throwable) {
        log("Connection lost")
        connectionLostCount++
        isMqttConnected.postValue(false)
        reconnect(5000)
    }

    override fun deliveryComplete(token: IMqttDeliveryToken) {
        log("Delivery complete")
    }

    private fun resolveFastestURL(): String {
        log("Resolving fastest URL......")
        return urls.first()
    }

    private fun log(message: String) {
        Timber.d("MQTT %s", message)
    }

    private fun log(t: Throwable) {
        Timber.d("MQTT %s", t.message ?: "Unknown Error")
    }
}