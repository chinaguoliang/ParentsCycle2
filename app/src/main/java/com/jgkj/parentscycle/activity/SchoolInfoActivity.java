package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import butterknife.OnClick;

/**
 * Created by chen on 16/7/18.
 */
public class SchoolInfoActivity extends BaseActivity implements NetListener,View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

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
        initViews();


        //requestCreateClass();
        //requestModifyTeacherPermission();
        requestClassTeacherMangement();
    }

    private void initViews() {
        titleTv.setText("学校信息");
        rightTv.setVisibility(View.GONE);
    }

    //建立班级
    private void requestCreateClass() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1"); //暂时传1
        requestData.put("classname", "大二班");
        requestData.put("classadviser", "老韩");
        String teacheerid[]=new String[2];
        teacheerid[0]="1";
        teacheerid[1]="2";
        Gson gson = new Gson();
        requestData.put("teacheerid", gson.toJson(teacheerid));
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.CREATE_CLASS, requestData, lp);
    }



    //修改教师权限
    private void requestModifyTeacherPermission() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("ostmpinfoid", UserInfo.loginInfo.getId());  //登录时ID
        requestData.put("permissions", UserInfo.LOOK_AFTER_TEACHER_PERMISSION);
        requestData.put("analysis", "laosh11i");
        requestData.put("teacherid", UserInfo.loginInfo.getId());
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.MODIFY_TEACHER_PERMISSION, requestData, lp);
    }

    //班级教师管理
    private void requestClassTeacherMangement() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1");
        requestData.put("classid", "1");
        String teacheerid[]=new String[2];
        teacheerid[0]="1";
        teacheerid[1]="2";
        Gson gson = new Gson();
        requestData.put("teacheerid", gson.toJson(teacheerid));
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.CLASS_TEACHER_MANGEMENT, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {

    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.shool_info_activity_make_class_rel})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == makeClassRel) {
            startActivity(new Intent(SchoolInfoActivity.this,MakeClassActivity.class));
        }
    }
}
