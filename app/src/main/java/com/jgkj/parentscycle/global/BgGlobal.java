package com.jgkj.parentscycle.global;

/**
 * Created by chen on 16/7/5.
 */
public class BgGlobal {
    public static final String BASE_URL = "http://123.206.43.102:8080/support/";
    //public static final String BASE_URL = "http://www.mumuq.net:8080/support/";
    public static final String DEVICE_PARAMS = "";
    public static final String REGISTER_URL = "jgkj/regtuser";
    public static final String VERIFY_PHONE_NUMBER_URL = "jgkj/getCode";
    public static final String RESET_PASSWORD = "jgkj/jgeditPass"; //找回密码
    public static final String CHECK_RESET_PASSWORD = "jgkj/jgkjGetPassCheck";  //找回密码校验
    public static final String LOGIN_URL = "jgkj/loginUser";
    public static final String TEACHER_INFO_SAVE = "jgkj/teacherinfoSave";
    public static final String TEACHER_INFO_LIST = "teacherinfo/getTeacherinfo";  //教师个人中心

    public static final String CREATE_CLASS = "classinfo/save";  //建立班级

    public static final String MODIFY_TEACHER_PERMISSION = "schoolTeacher/schoolTeacherUpdate";  //修改教师权限
    public static final String CLASS_TEACHER_MANGEMENT = "classTeacher/updateClassinfoList"; //班级教师管理
    public static final String ANNOUNCEMENT_PUBLISH = "announcement/save"; //公告发布
    public static final String SEARCH_CLASS_LIST_BY_SCHOOL_ID = "classinfo/getClassList";
    public static final String ANNOUNCEMENT_LIST = "announcement/announcementList"; //公告列表
    public static final String ANNOUNCEMENT_COMMENT_SAVE = "announcementComment/save"; //公告评论保存
    public static final String MODIFY_PASS_BY_OLD_PASS = "jgkj/jgeditOldPass";  //根据旧密码修改新密码
    public static final String MODIFY_PHONE_BY_OLD_PHONE = "jgkj/jgeditOldPhone";//根据旧手机号修改手机号
}
