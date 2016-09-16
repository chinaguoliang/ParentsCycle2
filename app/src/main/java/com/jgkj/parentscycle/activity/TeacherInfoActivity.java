package com.jgkj.parentscycle.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ModifyClassDialogLvAdapter;
import com.jgkj.parentscycle.adapter.ModifyPermissionDialogLvAdapter;
import com.jgkj.parentscycle.adapter.TeacherInfoAdapter;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.bean.ModifyPermissionInfo;
import com.jgkj.parentscycle.bean.PerfectInfoInfo;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ClassedAndTeachersPaser;
import com.jgkj.parentscycle.json.ModifyPermissionPaser;
import com.jgkj.parentscycle.json.PerfectInfoPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.AsyncImageUtil;
import com.jgkj.parentscycle.utils.CircularImage;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;
import com.jgkj.parentscycle.widget.ListViewForScrollView;
import com.jgkj.parentscycle.widget.SexSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/3.
 */
public class TeacherInfoActivity extends BaseActivity implements View.OnClickListener,NetListener,DatePickerDialog.OnDateSetListener,SexSelectDialog.SexSlectDialogFinish {
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

    @Bind(R.id.teacher_info_activity_save_btn)
    Button saveBtn;

    @Bind(R.id.hall_mine_fragment_lv_header_user_icon_iv)
    CircularImage mIconIv;

    @Bind(R.id.teacher_info_activity_name_tv)
    TextView nameTv;

    @Bind(R.id.teacher_info_activity_phone_tv)
    TextView phoneTv;

    TeacherInfoAdapter teacherInfoAdapter;
    Dialog mLeaveSchoolDialog;
    Dialog mModifyClassDialog;
    Dialog mModifyPermissionDialog;
    TeacherInfoListInfo mTeacherInfoListInfo;

    String classIds = "";
    String headIconUrl = "";
    String sex = "";
    String permission = "";
    String selectedDate = "";
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
//                    showModifyClassDialog();
                    requestClassListBySchoolId();
                } else if (position == 9) {
                    showModifyPermissionDialog();
                } else if (position == 6) {
                    showDateDialog();
                } else if (position == 4) {
                    SexSelectDialog.showSexSelectDialog(TeacherInfoActivity.this,TeacherInfoActivity.this);
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
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);  //暂时传1
        TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_LIST, requestData, lp);
    }

    private void showDateDialog() {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        int year=d.get(Calendar.YEAR);
        int month=d.get(Calendar.MONTH);
        int day=d.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,this,year,month,day);
        dpd.getWindow().setBackgroundDrawable(new BitmapDrawable()); //设置为透明
        dpd.show();
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
        data.add("负责班级_" + tii.getClassname().toString());
        data.add("更改职位与权限_");
        data.add("更改班级_");
        data.add("离开幼儿园_");
        return data;
    }

    private void initViews() {
        titleBg.setBackgroundColor(0x00000000);
        rightTv.setVisibility(View.GONE);

    }


    @OnClick({R.id.baby_document_activity_back_iv,R.id.teacher_info_activity_save_btn,R.id.hall_mine_fragment_lv_header_user_icon_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == saveBtn) {
            requestSaveTeacherInfo();
        } else if (v == mIconIv) {
            showChangePhotoDialog();
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
        confirmLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTeacherInfoListInfo.setOnthejob("0");
                requestSaveTeacherInfo();
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeaveSchoolDialog.dismiss();
            }
        });

        mLeaveSchoolDialog.setContentView(contentView);
        mLeaveSchoolDialog.setCanceledOnTouchOutside(true);
        mLeaveSchoolDialog.show();

        WindowManager.LayoutParams params = mLeaveSchoolDialog.getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mLeaveSchoolDialog.getWindow().setAttributes(params);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
        selectedDate = date;
        String dateStr = "出生日期_" + date;
        teacherInfoAdapter.setPositionData(6,dateStr);
    }

    private void showModifyClassDialog(ArrayList< MakeClassAddPersonInfo > sourceData) {
        mModifyClassDialog = new Dialog(this, R.style.DialogTheme);
        mModifyClassDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.modify_class_dialog, null);
        Button confirm = (Button) contentView.findViewById(R.id.modify_class_dialog_confirm_btn);
        ListView classLv = (ListView) contentView.findViewById(R.id.modify_class_dialog_lv);
        confirm.setOnClickListener(changePhotoListener);

        final ModifyClassDialogLvAdapter mcda = new ModifyClassDialogLvAdapter(this,sourceData);
        classLv.setAdapter(mcda);
        classLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mcda.setSelectPosition(position);

            }
        });

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                classIds = mcda.getIdsData();
                mModifyClassDialog.dismiss();
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
                requestModifyTeacherPermission("" + position);
                mModifyPermissionDialog.dismiss();
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



    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {
        headIconUrl = BgGlobal.IMG_SERVER_PRE_URL + uploadedKey;
        AsyncImageUtil.asyncLoadImage(mIconIv,
                headIconUrl,
                R.mipmap.user_default_icon, true, false);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof TeacherInfoListInfo) {
            if (nbs.isSuccess()) {
                TeacherInfoListInfo tii = (TeacherInfoListInfo)nbs.obj;
                mTeacherInfoListInfo = tii;
                titleTv.setText(tii.getTeachername());
                nameTv.setText(tii.getTeachername());
                phoneTv.setText(tii.getPhone());
                AsyncImageUtil.asyncLoadImage(mIconIv,
                        tii.getHeadportrait(),
                        R.mipmap.user_default_icon, true, false);
                teacherInfoAdapter = new TeacherInfoAdapter(this, getContentData(tii));
                contentLv.setAdapter(teacherInfoAdapter);
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        } else if (nbs.obj instanceof ClassedAndTeachersListInfo) {
            if (nbs.isSuccess()) {
                ClassedAndTeachersListInfo tii = (ClassedAndTeachersListInfo)nbs.obj;
                initListView(tii.getDataList());
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        } else if (nbs.obj instanceof ModifyPermissionInfo) {
            if (nbs.isSuccess()) {
                finish();
            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        } else if (nbs.obj instanceof PerfectInfoInfo) {
            if (nbs.isSuccess()) {
                finish();
            }
            ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
        }
    }

    private void initListView(final List<ClassesAndTeachersListItemInfo> dataList) {
        int count = dataList.size();
        HashMap <String,String> nameIntMap = new HashMap <String,String>();
        ArrayList< MakeClassAddPersonInfo > sourceData = new ArrayList<MakeClassAddPersonInfo>();
        for (int i = 0 ; i < count ; i++) {
            ClassesAndTeachersListItemInfo catli = dataList.get(i);
            String className = catli.getClassname();
            String classId = catli.getClassid();
            if (nameIntMap.get(classId) != null) {
                continue;
            }

            nameIntMap.put(classId,classId);
            MakeClassAddPersonInfo mcpi = new MakeClassAddPersonInfo();
            mcpi.setId(classId);
            mcpi.setName(className);
            sourceData.add(mcpi);
        }
        showModifyClassDialog(sourceData);
    }

    //修改教师权限
    private void requestModifyTeacherPermission(String permission) {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("ostmpinfoid", mTeacherInfoListInfo.getTmpinfoid());  //登录时ID
        requestData.put("permissions", permission);
        requestData.put("analysis", mTeacherInfoListInfo.getAnalysis());
        requestData.put("teacherid", mTeacherInfoListInfo.getTeacherid());
        ModifyPermissionPaser lp = new ModifyPermissionPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.MODIFY_TEACHER_PERMISSION, requestData, lp);
    }

    public void requestSaveTeacherInfo() {
        showProgressDialog();
        HashMap<Integer,String> dataMap = teacherInfoAdapter.dataMap;

        HashMap<String, String> requestData = new HashMap<String, String>();
        String ayalysis = dataMap.get(0);
        if (TextUtils.isEmpty(ayalysis)) {
            requestData.put("analysis",mTeacherInfoListInfo.getAnalysis());
        } else {
            requestData.put("analysis",ayalysis);
        }

        if (TextUtils.isEmpty(selectedDate)) {
            requestData.put("birthdate",mTeacherInfoListInfo.getBirthdate());
        } else {
            requestData.put("birthdate",selectedDate);
        }


        if (TextUtils.isEmpty(classIds)) {
            requestData.put("classid",mTeacherInfoListInfo.getClassid());
        } else {
            requestData.put("classid",classIds);
        }

        if (TextUtils.isEmpty(headIconUrl)) {
            requestData.put("headportrait",mTeacherInfoListInfo.getHeadportrait());
        } else {
            requestData.put("headportrait",headIconUrl);
        }

        requestData.put("kbwx",mTeacherInfoListInfo.getKbwx()); //1: 是  0：否
        requestData.put("kbqq",mTeacherInfoListInfo.getKbqq());
        requestData.put("nationality",mTeacherInfoListInfo.getNationality());

        String nickName = dataMap.get(2);
        if (TextUtils.isEmpty(nickName)) {
            requestData.put("nickname",mTeacherInfoListInfo.getNickname());
        } else {
            requestData.put("nickname",nickName);
        }

        requestData.put("onthejob",mTeacherInfoListInfo.getOnthejob()); // 1:在职  0： 离职

        if (TextUtils.isEmpty(permission)) {
            requestData.put("permissions",mTeacherInfoListInfo.getPermissions());
        } else {
            requestData.put("permissions",permission);
        }

        String phone = dataMap.get(7);
        if (TextUtils.isEmpty(phone)) {
            requestData.put("phone",mTeacherInfoListInfo.getPhone());
        } else {
            requestData.put("phone",phone);
        }

        requestData.put("schoolname",mTeacherInfoListInfo.getSchoolname());
        requestData.put("teacherid",mTeacherInfoListInfo.getTeacherid());

        String teacherName = dataMap.get(3);
        if (TextUtils.isEmpty(teacherName)) {
            requestData.put("teachername",mTeacherInfoListInfo.getTeachername());
        } else {
            requestData.put("teachername",teacherName);
        }


        if (TextUtils.isEmpty(sex)) {
            requestData.put("teachersex",mTeacherInfoListInfo.getTeachersex());
        } else {
            requestData.put("teachersex",sex);
        }


        requestData.put("tmpinfoid", mTeacherInfoListInfo.getTmpinfoid());
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);  //暂时传1
        PerfectInfoPaser lp = new PerfectInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_SAVE, requestData, lp);
    }

    //按学校ID 查询班级列表 （发布选择班级展示）
    private void requestClassListBySchoolId() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        ClassedAndTeachersPaser lp = new ClassedAndTeachersPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.SEARCH_CLASS_LIST_BY_SCHOOL_ID, requestData, lp);
    }

    @Override
    public void finishSlecct(int index) {
        String sexResult = "";
        if (index == 1) {
            sex = "1";
            sexResult = "性别_男";
        } else if (index == 0) {
            sexResult = "性别_女";
            sex = "0";
        }
        teacherInfoAdapter.setPositionData(4,sexResult);
    }
}
