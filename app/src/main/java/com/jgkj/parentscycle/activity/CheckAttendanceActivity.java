package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1宝宝档案-教师版-修改考勤
 * Created by chen on 16/7/30.
 */
public class CheckAttendanceActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.check_attendance_activity_modify_attendance_btn)
    Button mCheckAttendance;

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
