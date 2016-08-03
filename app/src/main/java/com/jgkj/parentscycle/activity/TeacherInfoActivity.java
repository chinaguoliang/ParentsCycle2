package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.TeacherInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/3.
 */
public class TeacherInfoActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.teacher_info_activity_lv)
    ListView contentLv;

    TeacherInfoAdapter teacherInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_info_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("职务");
        data.add("权限");
        data.add("昵称");
        data.add("姓名");
        data.add("性别");
        data.add("民族");
        data.add("出生日期");
        data.add("手机号");
        data.add("负责班级");
        data.add("建立班级");
        data.add("管理老师");
        data.add("班级管理");
        data.add("离开幼儿园");
        return data;
    }

    private void initViews() {
        titleBg.setBackgroundColor(0x00000000);
        rightTv.setVisibility(View.GONE);
        titleTv.setText("李老师");
        teacherInfoAdapter = new TeacherInfoAdapter(this,getContentData());
        contentLv.setAdapter(teacherInfoAdapter);
    }


    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }
}
