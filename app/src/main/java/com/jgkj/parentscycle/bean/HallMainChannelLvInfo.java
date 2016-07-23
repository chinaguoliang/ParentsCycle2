package com.jgkj.parentscycle.bean;

import java.io.Serializable;

/**
 * Created by chen on 16/7/23.
 */
public class HallMainChannelLvInfo implements Serializable{
    String className;
    String onLineStatus;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOnLineStatus() {
        return onLineStatus;
    }

    public void setOnLineStatus(String onLineStatus) {
        this.onLineStatus = onLineStatus;
    }
}
