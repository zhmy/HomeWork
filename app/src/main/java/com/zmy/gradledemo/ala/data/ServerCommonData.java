package com.zmy.gradledemo.ala.data;

import org.json.JSONObject;

/**
 * Created by zmy on 16/9/17.
 */
public abstract class ServerCommonData {
    private ServerErrorData error;
    private String server_time;
    private String time;
    private String ctime;
    private String logid;
    private String error_code;

    public abstract Object getData();

    public ServerErrorData getError() {
        return error;
    }

    public void setError(ServerErrorData error) {
        this.error = error;
    }

    public String getServer_time() {
        return server_time;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
}
