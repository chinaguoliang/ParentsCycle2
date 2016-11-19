package com.jgkj.parentscycle.bean;

import java.io.Serializable;

/**
 * Created by chen on 16/11/17.
 */
public class ParentsCyclePostsListItem implements Serializable{
    String address = "";
    String createtime = "";
    String dznum = "";
    String id = "";
    String page = "";
    String plnum = "";
    String rows = "";
    String schoolid = "";
    String topic = "";
    String topicimg = "";
    String topictext = "";
    String topictype = "";
    String updatetime = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getDznum() {
        return dznum;
    }

    public void setDznum(String dznum) {
        this.dznum = dznum;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPlnum() {
        return plnum;
    }

    public void setPlnum(String plnum) {
        this.plnum = plnum;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicimg() {
        return topicimg;
    }

    public void setTopicimg(String topicimg) {
        this.topicimg = topicimg;
    }

    public String getTopictext() {
        return topictext;
    }

    public void setTopictext(String topictext) {
        this.topictext = topictext;
    }

    public String getTopictype() {
        return topictype;
    }

    public void setTopictype(String topictype) {
        this.topictype = topictype;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }
}
