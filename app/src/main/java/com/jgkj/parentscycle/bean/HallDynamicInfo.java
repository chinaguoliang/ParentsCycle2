package com.jgkj.parentscycle.bean;

/**
 * Created by chen on 16/7/23.
 */
public class HallDynamicInfo {
    String name;
    String time;
    String iconUrl;
    String bigIconUrl;
    String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getBigIconUrl() {
        return bigIconUrl;
    }

    public void setBigIconUrl(String bigIconUrl) {
        this.bigIconUrl = bigIconUrl;
    }
}
