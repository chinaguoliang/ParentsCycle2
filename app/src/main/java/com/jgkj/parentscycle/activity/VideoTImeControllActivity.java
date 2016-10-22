package com.jgkj.parentscycle.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/10/22.
 */
public class VideoTImeControllActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;


    @Bind(R.id.video_time_controll_activity_time_switch_cb)
    CheckBox timeSwitchCb;

    @Bind(R.id.video_time_controll_activity_start_time_ll)
    LinearLayout startTimeLl;

    @Bind(R.id.video_time_controll_activity_start_time_tv)
    TextView startTimetv;


    @Bind(R.id.video_time_controll_activity_end_time_ll)
    LinearLayout endTimeLl;


    @Bind(R.id.video_time_controll_activity_end_time_tv)
    TextView endTimeTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_time_controll_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rightTv.setVisibility(View.GONE);
        titleTv.setText("视频时段控制");
        timeSwitchCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    timeSwitchCb.setText("视频总开关处于开启状态");
                } else {
                    timeSwitchCb.setText("视频总开关处于关闭状态");
                }
            }
        });
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.video_time_controll_activity_start_time_ll,R.id.video_time_controll_activity_end_time_ll})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == startTimeLl) {
            String startTime = startTimetv.getText().toString();
            String startTimeArray[] = startTime.split(":");
            showDateDialog(Integer.parseInt(startTimeArray[0]),Integer.parseInt(startTimeArray[1]),startTimetv);
        } else if (v == endTimeLl) {
            String endTime = endTimeTv.getText().toString();
            String endTimeArray[] = endTime.split(":");
            showDateDialog(Integer.parseInt(endTimeArray[0]),Integer.parseInt(endTimeArray[1]),endTimeTv);
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }


    private void showDateDialog(int hour,int minute,final TextView dateTv) {
        if (!timeSwitchCb.isChecked()) {
            ToastUtil.showToast(this,"视频处于关闭状态", Toast.LENGTH_SHORT);
            return;
        }

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String result;
                if (minute < 10) {
                    result = hourOfDay + ":0" + minute + ":00";
                } else {
                    result = hourOfDay + ":" + minute + ":00";
                }

                dateTv.setText(result);
            }
        },hour,minute,true);
        timePickerDialog.getWindow().setBackgroundDrawable(new BitmapDrawable()); //设置为透明
        timePickerDialog.show();
    }

}
