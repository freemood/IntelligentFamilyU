package com.Intelligent.FamilyU.model.message.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MessageNotionBean implements Serializable {
    @SerializedName("gateWayId")
    private String gateWayId;
    @SerializedName("gatewaySN")
    private String gatewaySN;
    @SerializedName("pluginId")
    private String pluginId;
    @SerializedName("after")
    private After after;
    @SerializedName("before")
    private Before before;
    private String title;
    public class After{
        private int index;
        private String text;
        private String value;
        private int mask;

        public int getIndex() {
            return index;
        }

        public After setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public After setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public After setValue(String value) {
            this.value = value;
            return this;
        }

        public int getMask() {
            return mask;
        }

        public After setMask(int mask) {
            this.mask = mask;
            return this;
        }
    }

    public class Before{
        private int index;
        private String text;
        private String value;
        private int mask;

        public int getIndex() {
            return index;
        }

        public Before setIndex(int index) {
            this.index = index;
            return this;
        }

        public String getText() {
            return text;
        }

        public Before setText(String text) {
            this.text = text;
            return this;
        }

        public String getValue() {
            return value;
        }

        public Before setValue(String value) {
            this.value = value;
            return this;
        }

        public int getMask() {
            return mask;
        }

        public Before setMask(int mask) {
            this.mask = mask;
            return this;
        }
    }

    public String getGateWayId() {
        return gateWayId;
    }

    public MessageNotionBean setGateWayId(String gateWayId) {
        this.gateWayId = gateWayId;
        return this;
    }

    public String getGatewaySN() {
        return gatewaySN;
    }

    public MessageNotionBean setGatewaySN(String gatewaySN) {
        this.gatewaySN = gatewaySN;
        return this;
    }

    public String getPluginId() {
        return pluginId;
    }

    public MessageNotionBean setPluginId(String pluginId) {
        this.pluginId = pluginId;
        return this;
    }

    public After getAfter() {
        return after;
    }

    public MessageNotionBean setAfter(After after) {
        this.after = after;
        return this;
    }

    public Before getBefore() {
        return before;
    }

    public MessageNotionBean setBefore(Before before) {
        this.before = before;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MessageNotionBean setTitle(String title) {
        this.title = title;
        return this;
    }
}
