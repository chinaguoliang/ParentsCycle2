package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.CheckAttendanceInfo;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.CheckAttendancePaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.TimeUtils;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sun.bob.mcalendarview.MarkStyle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_attendance_activity);
        ButterKnife.bind(this);
        initViews();
        requestCheckAttendance();
    }

    private void initViews() {
        rightTv.setVisibility(View.GONE);
        titleTv.setText(TimeUtils.getCurrentTime());

        expCalendarView.markDate(new DateData(2016, 9, 5).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, GREEN)));
        expCalendarView.markDate(new DateData(2016, 9, 1).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, YELLOW)));
        expCalendarView.markDate(new DateData(2016, 9, 2).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, BLUE)));

        expCalendarView.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
//                YearMonthTv.setText(String.format("%d年%d月", year, month));
            }

            @Override
            public void onMonthScroll(float positionOffset) {
//                Log.i("listener", "onMonthScroll:" + positionOffset);
            }
        });
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
        }
    }
}
