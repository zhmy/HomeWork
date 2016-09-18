package com.zmy.gradledemo.ala.data;

/**
 * Created by zmy on 16/9/17.
 */
public class ServerErrorData {
    private int errno;
    private String errmsg;
    private String usermsg;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getUsermsg() {
        return usermsg;
    }

    public void setUsermsg(String usermsg) {
        this.usermsg = usermsg;
    }
}
