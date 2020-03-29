package com.square.common.mqtt;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.jetbrains.annotations.NotNull;

public class OkMqttMessage extends MqttMessage {
    public final String[] topics;
    public final int[] grantedQos;

    private OkMqttMessage(Builder builder) {
        super.setPayload(builder.payload);
        super.setRetained(builder.isRetained);
        super.setQos(builder.qos);
        super.setMutable(builder.isMutable);
        super.setDuplicate(builder.isDuplicate);
        super.setId(builder.id);
        this.topics = builder.topics;
        this.grantedQos = builder.grantedQos;
        if (this.topics == null || this.topics.length == 0) {
            throw new NullPointerException();
        }
        if (this.grantedQos == null || this.grantedQos.length == 0) {
            throw new NullPointerException();
        }
        if (this.topics.length != this.grantedQos.length) {
            throw new UnsupportedOperationException();
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        boolean isMutable = true;
        byte[] payload = new byte[0];
        int qos = 1;
        boolean isRetained = false;
        boolean isDuplicate = false;
        int id;

        String[] topics;
        int[] grantedQos;

        public Builder() {
        }

        public Builder(OkMqttMessage msg) {
            payload = msg.getPayload();
            qos = msg.getQos();
            isRetained = msg.isRetained();
            isDuplicate = msg.isDuplicate();
            id = msg.getId();
            isMutable = true;

            topics = msg.topics;
            grantedQos = msg.grantedQos;
        }

        public Builder payload(@NotNull byte[] payload) {
            this.payload = payload;
            return this;
        }

        public Builder isMutable(boolean isMutable) {
            this.isMutable = isMutable;
            return this;
        }

        public Builder qos(int qos) {
            this.qos = qos;
            return this;
        }

        public Builder isRetained(boolean isRetained) {
            this.isRetained = isRetained;
            return this;
        }

        public Builder isDuplicate(boolean isDuplicate) {
            this.isDuplicate = isDuplicate;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder topics(String[] topics, int[] grantedQos) {
            if (this.topics != null && this.topics.length > 0) {
                throw new AssertionError();
            }
            if (this.grantedQos != null && this.grantedQos.length > 0) {
                throw new AssertionError();
            }
            this.topics = topics;
            this.grantedQos = grantedQos;
            return this;
        }

        public Builder topics(String[] topics) {
            int[] ints = new int[topics.length];
            for (int i = 0; i < ints.length; i++) {
                ints[i] = -1;
            }
            return topics(topics, ints);
        }

        public Builder topic(String topic, int grantedQos) {
            return topics(new String[]{topic}, new int[]{grantedQos});
        }

        public OkMqttMessage build() {
            return new OkMqttMessage(this);
        }
    }
}
