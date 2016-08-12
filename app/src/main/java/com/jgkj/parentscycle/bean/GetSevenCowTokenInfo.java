package com.jgkj.parentscycle.bean;

/**
 * Created by chen on 16/8/12.
 */
public class GetSevenCowTokenInfo {
    boolean success;
    String msg;
    String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
