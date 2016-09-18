package com.zmy.gradledemo.ala.data;

/**
 * Created by zmy on 16/9/17.
 */
public class SessionInfoData {
    private String sessionId;
    private String rtmpUrl;
    private String hlsUrl;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }
}
