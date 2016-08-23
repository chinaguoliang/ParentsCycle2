package com.jgkj.parentscycle.user;

import android.text.TextUtils;

import com.jgkj.parentscycle.bean.LoginInfo;

/**
 * Created by chen on 16/7/24.
 */
public class UserInfo {
    //权限ID列表
    public static final String ADMIN_PERMISSION = "1";   //管理员
    public static final String USUAL_TEACHER_PERMISSION = "2"; //普通老师
    public static final String WEB_ADMIN_PERMISSION = "3"; //网站管理员
    public static final String LOOK_AFTER_TEACHER_PERMISSION = "4"; //保育员老师

    public static String getTitleByPermission(String permission) {
        permission = permission.trim();
        String result = "";
        if (TextUtils.equals(permission,ADMIN_PERMISSION)) {
            result = "管理员";
        } else if (TextUtils.equals(permission,USUAL_TEACHER_PERMISSION)) {
            result = "普通老师";
        } else if (TextUtils.equals(permission,WEB_ADMIN_PERMISSION)) {
            result = "网站管理员";
        } else if (TextUtils.equals(permission,LOOK_AFTER_TEACHER_PERMISSION)) {
            result = "保育员老师";
        }
        return result;
    }

    public static String getSexByServerData(String serverData) {
        if (TextUtils.equals(serverData,"1")) {
            return "男";
        } else if (TextUtils.equals(serverData,"0")) {
            return "女";
        }
        return "未知";
    }


    public static String phoneNumber;
    public static boolean isLogined;
    public static LoginInfo loginInfo = new LoginInfo();
    public static String Id;
}
