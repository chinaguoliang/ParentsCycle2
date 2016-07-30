package com.jgkj.parentscycle.bean;

import java.io.Serializable;

/**
 * Created by chen on 16/7/24.
 */
public class ParentsCycleInfo implements Serializable {
    String name;
    String time;
    int imgRes;
    String Content;

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

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
