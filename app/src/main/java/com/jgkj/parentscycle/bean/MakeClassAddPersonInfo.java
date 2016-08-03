package com.jgkj.parentscycle.bean;

import java.io.Serializable;

/**
 * Created by chen on 16/8/3.
 */
public class MakeClassAddPersonInfo implements Serializable{
    String id = "";
    String name = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
