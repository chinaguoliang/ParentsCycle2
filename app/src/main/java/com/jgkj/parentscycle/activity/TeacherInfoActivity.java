package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ModifyClassDialogLvAdapter;
import com.jgkj.parentscycle.adapter.ModifyPermissionDialogLvAdapter;
import com.jgkj.parentscycle.adapter.TeacherInfoAdapter;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/3.
 */
public class TeacherInfoActivity extends BaseActivity implements View.OnClickListener {
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
    Dialog mLeaveSchoolDialog;
    Dialog mModifyClassDialog;
    Dialog mModifyPermissionDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_info_activity);
        ButterKnife.bind(this);
        initViews();
        contentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 12) {
                    //离开幼儿园
                    showLeaveSchoolDialog();
                } else if (position == 11) {
                    //班级管理
                    showModifyClassDialog();
                } else if (position == 1) {
                    showModifyPermissionDialog();
                }
            }
        });
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
        teacherInfoAdapter = new TeacherInfoAdapter(this, getContentData());
        contentLv.setAdapter(teacherInfoAdapter);
    }


    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }

    private View.OnClickListener changePhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.leave_school_dialog_confirm_leave_tv) {
                mLeaveSchoolDialog.dismiss();
                ToastUtil.showToast(TeacherInfoActivity.this,"已经离开学校", Toast.LENGTH_SHORT);
            } else if (v.getId() == R.id.leave_school_dialog_cancel_tv) {
                mLeaveSchoolDialog.dismiss();
            } else if (v.getId() == R.id.modify_class_dialog_confirm_btn) {
                mModifyClassDialog.dismiss();
            } else if (v.getId() == R.id.modify_permission_dialog_cancel_btn) {
                mModifyPermissionDialog.dismiss();
            }
        }
    };

    private void showLeaveSchoolDialog() {
        mLeaveSchoolDialog = new Dialog(this, R.style.DialogTheme);
        mLeaveSchoolDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.leave_school_dialog, null);
        TextView confirmLeave = (TextView) contentView.findViewById(R.id.leave_school_dialog_confirm_leave_tv);
        TextView cancelBtn = (TextView) contentView.findViewById(R.id.leave_school_dialog_cancel_tv);
        confirmLeave.setOnClickListener(changePhotoListener);
        cancelBtn.setOnClickListener(changePhotoListener);

        mLeaveSchoolDialog.setContentView(contentView);
        mLeaveSchoolDialog.setCanceledOnTouchOutside(true);
        mLeaveSchoolDialog.show();

        WindowManager.LayoutParams params = mLeaveSchoolDialog.getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mLeaveSchoolDialog.getWindow().setAttributes(params);
    }


    private void showModifyClassDialog() {
        mModifyClassDialog = new Dialog(this, R.style.DialogTheme);
        mModifyClassDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.modify_class_dialog, null);
        Button confirm = (Button) contentView.findViewById(R.id.modify_class_dialog_confirm_btn);
        ListView classLv = (ListView) contentView.findViewById(R.id.modify_class_dialog_lv);
        confirm.setOnClickListener(changePhotoListener);

        final ModifyClassDialogLvAdapter mcda = new ModifyClassDialogLvAdapter(this,getTestData());
        classLv.setAdapter(mcda);
        classLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mcda.setSelectPosition(position);
            }
        });

        mModifyClassDialog.setContentView(contentView);
        mModifyClassDialog.setCanceledOnTouchOutside(true);
        mModifyClassDialog.show();

        WindowManager.LayoutParams params = mModifyClassDialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mModifyClassDialog.getWindow().setAttributes(params);
    }

    private void showModifyPermissionDialog() {
        mModifyPermissionDialog = new Dialog(this, R.style.DialogTheme);
        mModifyPermissionDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.modify_permission_dialog, null);
        Button cancel = (Button) contentView.findViewById(R.id.modify_permission_dialog_cancel_btn);
        ListView classLv = (ListView) contentView.findViewById(R.id.modify_permission_dialog_lv);
        cancel.setOnClickListener(changePhotoListener);

        ArrayList <String> data = new ArrayList<String>();
        data.add("权限更改");
        data.add("管理员");
        data.add("普通老师");
        data.add("网站管理员");
        data.add("保育员老师");

        final ModifyPermissionDialogLvAdapter mcda = new ModifyPermissionDialogLvAdapter(this,data);
        classLv.setAdapter(mcda);
        classLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        mModifyPermissionDialog.setContentView(contentView);
        mModifyPermissionDialog.setCanceledOnTouchOutside(true);
        mModifyPermissionDialog.show();

        WindowManager.LayoutParams params = mModifyPermissionDialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mModifyPermissionDialog.getWindow().setAttributes(params);
    }

    private ArrayList<MakeClassAddPersonInfo> getTestData() {
        ArrayList<MakeClassAddPersonInfo> data = new ArrayList<MakeClassAddPersonInfo>();
        for (int i = 0 ; i < 3; i ++) {
            MakeClassAddPersonInfo ma = new MakeClassAddPersonInfo();
            if (i == 0) {
                ma.setName("柠檬班");
            }

            if (i == 1) {
                ma.setName("橘子班");
            }

            if (i == 2) {
                ma.setName("苹果班");
            }
            ma.setId(String.valueOf(i + 1));
            data.add(ma);
        }
        return data;
    }
}
