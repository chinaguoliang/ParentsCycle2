package com.jgkj.parentscycle.bean;

import com.jgkj.parentscycle.net.NetBeanSuper;

import java.io.Serializable;

/**
 * Created by chen on 16/7/7.
 */
public class LoginInfo {
    LoginInfoObj info;
    LoginRoleObj role;

    public LoginInfoObj getInfo() {
        if (info == null) {
            return new LoginInfoObj();
        } else {
            return info;
        }
    }

    public void setInfo(LoginInfoObj info) {
        this.info = info;
    }

    public LoginRoleObj getRole() {
        if (role == null) {
            return new LoginRoleObj();
        } else {
            return role;
        }
    }

    public void setRole(LoginRoleObj role) {
        this.role = role;
    }
}
