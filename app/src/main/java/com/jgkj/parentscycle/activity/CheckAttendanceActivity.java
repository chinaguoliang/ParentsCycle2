package com.jgkj.parentscycle.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.CheckAttendanceInfo;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.bean.ModifyAttendanceTeacherInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.CheckAttendancePaser;
import com.jgkj.parentscycle.json.ModifyAttendanceTeacherPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.TimeUtils;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.listeners.OnExpDateClickListener;
import sun.bob.mcalendarview.listeners.OnMonthScrollListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

/**
 * 1宝宝档案-教师版-修改考勤
 * Created by chen on 16/7/30.
 */
public class CheckAttendanceActivity extends BaseActivity implements View.OnClickListener,NetListener{
    public static final int YELLOW = 0xFFF3BB48;
    public static final int BLUE = 0xFF528DE6;
    public static final int GREEN = 0xFFC2E891;


    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.check_attendance_activity_modify_attendance_btn)
    Button mCheckAttendance;

    @Bind(R.id.check_attendance_activity_calendar_view)
    ExpCalendarView expCalendarView;

    Dialog currDialog;
    int attendanceType = 0;
    Dialog mModifyAttendance;
    private String directionStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance_activity);
        ButterKnife.bind(this);
        directionStr = this.getIntent().getStringExtra("direction");
        initViews();
        requestCheckAttendance();
    }

    private void initViews() {
        rightTv.setVisibility(View.GONE);
        titleTv.setText(TimeUtils.getCurrentTime());

        expCalendarView.markDate(new DateData(2016, 9, 5).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, GREEN)));
        expCalendarView.markDate(new DateData(2016, 9, 1).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, YELLOW)));
        expCalendarView.markDate(new DateData(2016, 9, 2).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, BLUE)));

//        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
//            @Override
//            public void onMonthChange(int year, int month) {
////                YearMonthTv.setText(String.format("%d年%d月", year, month));
//            }
//
//            @Override
//            public void onMonthScroll(float positionOffset) {
////                Log.i("listener", "onMonthScroll:" + positionOffset);
//            }
//
//        });



        if (TextUtils.equals("hall",directionStr)) {
            mCheckAttendance.setVisibility(View.GONE);

        } else {
            expCalendarView.setOnDateClickListener(new OnDateClickListener() {
                @Override
                public void onDateClick(View view, DateData dateData) {
                    int year = dateData.getYear();
                    int month = dateData.getMonth();
                    int day = dateData.getDay();
                    LogUtil.d("result","the time:" + year + "-" + month + "-" + day);

                    if (currDialog != null && currDialog.isShowing()) {
                        return;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(CheckAttendanceActivity.this);
                    builder.setMessage(year + "-" + month + "-" + day);
                    builder.setTitle("确认要签到吗?");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            requestBabyAskForLeave();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    currDialog = builder.create();
                    currDialog.show();

                }
            });
        }
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.check_attendance_activity_modify_attendance_btn})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == mCheckAttendance) {
            startActivity(new Intent(v.getContext(),ModifyAttendanceActivity.class));
        }
    }
    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    //考勤日历 颜色列表  根据类型显示 各种颜色
    public void requestCheckAttendance() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("page", "1");
        requestData.put("rows","10");
        requestData.put("createdatetimeStart","2015");
        requestData.put("createdatetimeEnd","2016");
        requestData.put("classid", UserInfo.loginInfo.getInfo().getClassid());
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(System.currentTimeMillis());
        //requestData.put("starttime",date);
        CheckAttendancePaser lp = new CheckAttendancePaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.CHECK_ATTENDANCE, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof CheckAttendanceInfo) {
            if (nbs.isSuccess()) {

            } else {
            }
            ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
        }else if (nbs.obj instanceof ModifyAttendanceTeacherInfo) {
            if (nbs.isSuccess()) {
                finish();
                ToastUtil.showToast(this,"签到成功", Toast.LENGTH_SHORT);
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }

        }
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
                requestBabyAskForLeave();
            }
        });

        final TextView askForLeaveTv = (TextView) contentView.findViewById(R.id.modify_attendance_dialog_ask_for_leave_tv);
        askForLeaveTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                attendanceType = 1;
                mModifyAttendance.dismiss();
                requestBabyAskForLeave();
            }
        });

        final TextView notToSchoolTv = (TextView) contentView.findViewById(R.id.modify_attendance_dialog_not_to_school_tv);
        notToSchoolTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                attendanceType = 2;
                mModifyAttendance.dismiss();
                requestBabyAskForLeave();
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
        requestData.put("techerstarttime",date);
        requestData.put("asktype","0"); // 0 是到园
        requestData.put("askday","1");
        requestData.put("asktext","parents add");
        ModifyAttendanceTeacherPaser lp = new ModifyAttendanceTeacherPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.BABY_ASK_LEAVE_ADD, requestData, lp);
    }
}
