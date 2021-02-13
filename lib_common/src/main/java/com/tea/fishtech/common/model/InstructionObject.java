package com.tea.fishtech.common.model;

public class InstructionObject {
    private String topic;// 设备关联电话

    private String deviceId;// 设备的探头ID

    private String action;  //设备开关

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
