package com.jgkj.parentscycle.bean;

import com.jgkj.parentscycle.net.NetBeanSuper;

import java.io.Serializable;

/**
 * Created by chen on 16/7/12.
 */
public class RegisterInfo extends NetBeanSuper implements Serializable {
    String msg;
    boolean success;

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
