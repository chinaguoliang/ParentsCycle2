package com.jgkj.parentscycle.user;

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


    public static String phoneNumber;
    public static boolean isLogined;
    public static LoginInfo loginInfo = new LoginInfo();
    public static String Id;
}
