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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ModifyAttendanceTeacherInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.ModifyAttendanceTeacherPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/29.
 */
public class ModifyAttendanceActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener,View.OnClickListener,NetListener {
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.modify_attendance_activity_save_btn)
    Button saveBtn;

    @Bind(R.id.modify_attendance_activity_date_rel)
    RelativeLayout dateRel;

    @Bind(R.id.modify_attendance_activity_type_rel)
    RelativeLayout typeRel;

    @Bind(R.id.modify_attendance_activity_type_tv)
    TextView typeTv;

    @Bind(R.id.modify_attendance_activity_date_tv)
    TextView dateTv;

    @Bind(R.id.modify_attendance_activity_content_text_et)
    EditText contentEt;

    int attendanceType = 0;
    Dialog mModifyAttendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_attendance_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("修改考勤");
        titleTv.setVisibility(View.GONE);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    private void showDateDialog() {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        int year = d.get(Calendar.YEAR);
        int month = d.get(Calendar.MONTH);
        int day = d.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog, this, year, month, day);
        dpd.getWindow().setBackgroundDrawable(new BitmapDrawable()); //设置为透明
        dpd.show();
    }

    @OnClick({R.id.baby_document_activity_back_iv, R.id.modify_attendance_activity_save_btn,R.id.modify_attendance_activity_date_rel,R.id.modify_attendance_activity_type_rel})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == saveBtn) {
            String contentStr = contentEt.getText().toString();
            if (TextUtils.isEmpty(contentStr)) {
                ToastUtil.showToast(this,"请填写内容",Toast.LENGTH_SHORT);
                return;
            }
            requestBabyAskForLeave();
        } else if (v == dateRel) {
            showDateDialog();
        } else if (v == typeRel) {
            showAttendanceTypeDialog();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
        dateTv.setText(date);
    }

    private void showAttendanceTypeDialog() {
        mModifyAttendance = new Dialog(this, R.style.DialogTheme);
        mModifyAttendance.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.modify_attendance_type_dialog, null);

        final TextView hadGoSchoolTv = (TextView) contentView.findViewById(R.id.modify_attendance_dialog_had_go_school_tv);
        hadGoSchoolTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                attendanceType = 0;
                mModifyAttendance.dismiss();
                typeTv.setText(hadGoSchoolTv.getText());
            }
        });

        final TextView askForLeaveTv = (TextView) contentView.findViewById(R.id.modify_attendance_dialog_ask_for_leave_tv);
        askForLeaveTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                attendanceType = 1;
                mModifyAttendance.dismiss();
                typeTv.setText(askForLeaveTv.getText());
            }
        });

        final TextView notToSchoolTv = (TextView) contentView.findViewById(R.id.modify_attendance_dialog_not_to_school_tv);
        notToSchoolTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                attendanceType = 2;
                mModifyAttendance.dismiss();
                typeTv.setText(notToSchoolTv.getText());
            }
        });


        TextView cancelBtn = (TextView) contentView.findViewById(R.id.modify_attendance_cancel_tv);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mModifyAttendance.dismiss();
            }
        });

        mModifyAttendance.setContentView(contentView);
        mModifyAttendance.setCanceledOnTouchOutside(true);
        mModifyAttendance.show();

        WindowManager.LayoutParams params = mModifyAttendance.getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mModifyAttendance.getWindow().setAttributes(params);
    }

    // 家长版-宝宝请假添加     (此接口教师也可以使用，看具体需求，如果是签到直接改变类型，内容可不传)
    public void requestBabyAskForLeave() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("babyid", UserInfo.loginInfo.getInfo().getTmpinfoid());
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("techerstarttime",dateTv.getText().toString() + " 00:00:00");
        requestData.put("asktype",attendanceType + "");
        requestData.put("askday","1");
        requestData.put("asktext",contentEt.getText().toString());
        ModifyAttendanceTeacherPaser lp = new ModifyAttendanceTeacherPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.BABY_ASK_LEAVE_ADD, requestData, lp);
    }


//    // 教师版请假修改
//    public void requestAskForLeaveForTeacherVersion() {
//        showProgressDialog();
//        HashMap<String, String> requestData = new HashMap<String, String>();
//        requestData.put("askday", "1");
//        requestData.put("asktext","20160601");
//        requestData.put("asktype","22");
//        requestData.put("babyid","12312");
//        requestData.put("schoolid","555");
//        requestData.put("classid","999");
//        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
//        String date = sdf.format(System.currentTimeMillis());
//        requestData.put("dateday",date);
//        ModifyAttendanceTeacherPaser lp = new ModifyAttendanceTeacherPaser();
//        NetRequest.getInstance().request(mQueue, this,
//                BgGlobal.ASK_FOR_LEAVE_MODIFY_FOR_TEACHER_VERSION, requestData, lp);
//    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ModifyAttendanceTeacherInfo) {
            if (nbs.isSuccess()) {
                finish();
            } else {

            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
