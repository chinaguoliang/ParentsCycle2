package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
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
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;
import com.jgkj.parentscycle.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/3.
 */
public class TeacherInfoActivity extends BaseActivity implements View.OnClickListener,NetListener {
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.teacher_info_activity_lv)
    ListViewForScrollView contentLv;

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
                if (position == 11) {
                    //离开幼儿园
                    showLeaveSchoolDialog();
                } else if (position == 10) {
                    //班级管理
                    showModifyClassDialog();
                } else if (position == 9) {
                    showModifyPermissionDialog();
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        String teacherId = bundle.getString("teacher_id");

        requestTeacherInfo(teacherId);
    }

    private void requestTeacherInfo(String teacherId) {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("tmpinfoid", teacherId);
        requestData.put("schoolid", "1");  //暂时传1
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_LIST, requestData, lp);
    }

    private List<String> getContentData(TeacherInfoListInfo tii) {
        ArrayList<String> data = new ArrayList<String>();
        data.add("职务_" + tii.getAnalysis());
        data.add("权限_" + UserInfo.getTitleByPermission(tii.getPermissions()));
        data.add("昵称_" + tii.getNickname());
        data.add("姓名_" + tii.getTeachername());
        data.add("性别_" + UserInfo.getSexByServerData(tii.getTeachersex()));
        data.add("民族_" + tii.getNationality());
        data.add("出生日期_" + tii.getBirthdate());
        data.add("手机号_" + tii.getPhone());
        data.add("负责班级_" + tii.getClassid());
        data.add("更改职位与权限_");
        data.add("更改班级_");
        data.add("离开幼儿园_");
        return data;
    }

    private void initViews() {
        titleBg.setBackgroundColor(0x00000000);
        rightTv.setVisibility(View.GONE);

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

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof TeacherInfoListInfo) {
            if (nbs.isSuccess()) {
                TeacherInfoListInfo tii = (TeacherInfoListInfo)nbs.obj;
                titleTv.setText(tii.getTeachername());
                teacherInfoAdapter = new TeacherInfoAdapter(this, getContentData(tii));
                contentLv.setAdapter(teacherInfoAdapter);
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
