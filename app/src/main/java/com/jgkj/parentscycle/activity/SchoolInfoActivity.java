package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/7/18.
 */
public class SchoolInfoActivity extends BaseActivity implements NetListener{
    @Bind(R.id.shool_info_activity_job_rel)
    RelativeLayout jobRel;

    @Bind(R.id.shool_info_activity_permission_rel)
    RelativeLayout permissionRel;

    @Bind(R.id.shool_info_activity_add_school_rel)
    RelativeLayout addSchoolRel;

    @Bind(R.id.shool_info_activity_make_class_rel)
    RelativeLayout makeClassRel;

    @Bind(R.id.shool_info_activity_teacher_mangement_rel)
    RelativeLayout teacherManagementRel;

    @Bind(R.id.shool_info_activity_class_mangement_rel)
    RelativeLayout classMangementRel;

    @Bind(R.id.shool_info_activity_leave_school_rel)
    RelativeLayout leaveSchoolRel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_info_activity);
        ButterKnife.bind(this);
//        requestNet();
        requestModifyTeacherPermission();
    }

    //建立班级
    private void requestNet() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1");
        requestData.put("classname", "大二班");
        requestData.put("classadviser", "老韩");
        List<String> ids = new ArrayList<String>();
        ids.add("2");
        ids.add("3");
        ids.add("7");
        Gson gson = new Gson();
        requestData.put("teacheerid", gson.toJson(ids));
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.CREATE_CLASS, requestData, lp);
    }


    //修改教师接口
    private void requestModifyTeacherPermission() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("ostmpinfoid", UserInfo.loginInfo.getId());  //登录时ID
        requestData.put("permissions", "1");
        requestData.put("analysis", "teacher");
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.MODIFY_TEACHER_PERMISSION, requestData, lp);
    }

    //班级教师管理
    private void requestClassTeacherMangement() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", UserInfo.loginInfo.getId());
        requestData.put("classid", "1");
        List<String> ids = new ArrayList<String>();
        ids.add("2");
        ids.add("3");
        ids.add("7");
        Gson gson = new Gson();
        requestData.put("teacheerid", gson.toJson(ids));
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.CLASS_TEACHER_MANGEMENT, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {

    }
}
