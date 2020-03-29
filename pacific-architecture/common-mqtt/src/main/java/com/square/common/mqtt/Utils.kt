package com.square.common.mqtt

import com.square.guava.domain.CoreJdk

const val MQTT_CONNECT_TOKEN = -999
const val MQTT_DISCONNECT_TOKEN = -1000
const val MQTT_SUBSCRIBE_TOKEN = -1001
const val MQTT_UNSUBSCRIBE_TOKEN = -1002
const val MQTT_PUBLISH_TOKEN = -1003

const val QOS_AT_MOST_ONCE = 0
const val QOS_AT_LEAST_ONCE = 1
const val QOS_EXACTLY_ONCE = 2

const val TOPIC_IM_BROADCAST = "/im/broadcast/"

const val PLATFORM_ID = 1

fun createTopicFilters(): Array<String> {
    return arrayOf(
        "$TOPIC_IM_BROADCAST$PLATFORM_ID/${CoreJdk.token1}/${CoreJdk.deviceId}/"
            .replace("///", "/")
            .replace("//", "/")
    )
}

fun createQOS(): IntArray = intArrayOf(
    QOS_AT_LEAST_ONCE
)

fun createOkMessage(
    topic: String,
    qos: Int,
    id: Int,
    content: String
): OkMqttMessage {
    return OkMqttMessage.Builder()
        .id(id)
        .topic(topic, qos)
        .payload(content.toByteArray(Charsets.UTF_8))
        .build()
}