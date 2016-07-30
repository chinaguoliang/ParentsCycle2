package com.jgkj.parentscycle.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

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
public class CheckAttendanceActivity extends BaseActivity implements View.OnClickListener{
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
    }

    private void initViews() {
        rightTv.setVisibility(View.GONE);
        titleTv.setText("2016.3");

        expCalendarView.markDate(new DateData(2016, 7, 30).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, GREEN)));
        expCalendarView.markDate(new DateData(2016, 7, 18).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, YELLOW)));
        expCalendarView.markDate(new DateData(2016, 7, 22).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, BLUE)));

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

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == mCheckAttendance) {

        }
    }
}
