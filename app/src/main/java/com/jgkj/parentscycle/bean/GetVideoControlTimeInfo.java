package com.jgkj.parentscycle.bean;

/**
 * Created by chen on 16/10/23.
 */
public class GetVideoControlTimeInfo {
    String id = "";
    String school_id = "";
    String class_id = "";
    String start_time = "";
    String end_time = "";
    String is_allow_play = "";

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIs_allow_play() {
        return is_allow_play;
    }

    public void setIs_allow_play(String is_allow_play) {
        this.is_allow_play = is_allow_play;
    }
}
