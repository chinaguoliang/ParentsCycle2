package com.jgkj.parentscycle.net;

import com.jgkj.parentscycle.bean.LoginInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/8.
 */
public class NetBeanSuper implements Serializable{
    int result = Integer.MAX_VALUE;
    String msg = "";
    String all_page = null;
    String page = null;
    boolean success;
    public Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAll_page() {
        return all_page;
    }

    public void setAll_page(String all_page) {
        this.all_page = all_page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
