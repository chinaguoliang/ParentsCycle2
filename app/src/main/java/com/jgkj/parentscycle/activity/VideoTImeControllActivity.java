package com.jgkj.parentscycle.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.GetVideoControlTimeInfo;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.bean.SaveVideoControlTimeInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ClassedAndTeachersPaser;
import com.jgkj.parentscycle.json.GetVideoControlTimeInfoPaser;
import com.jgkj.parentscycle.json.SaveVideoControlTimeInfoPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/10/22.
 */
public class VideoTImeControllActivity extends BaseActivity implements View.OnClickListener,NetListener {
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

    @Bind(R.id.video_time_controll_activity_confirm_btn)
    Button confirmBtn;

    @Bind(R.id.video_time_controll_activity_serial_number_et)
    EditText serialNumberEt;

    MakeClassAddPersonInfo mMakeClassAddPersonInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_time_controll_activity);
        ButterKnife.bind(this);
        initView();
        requestVideoControllTime();
    }

    private void initView() {
        rightTv.setVisibility(View.GONE);

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

        mMakeClassAddPersonInfo = (MakeClassAddPersonInfo)this.getIntent().getExtras().getSerializable("class_data");
        titleTv.setText(mMakeClassAddPersonInfo.getName());
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.video_time_controll_activity_start_time_ll,R.id.video_time_controll_activity_end_time_ll,R.id.video_time_controll_activity_confirm_btn})
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
        } else if (v == confirmBtn) {
            requestSaveVideoControllTime();
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

    private void requestVideoControllTime() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        requestData.put("classid", mMakeClassAddPersonInfo.getId());

        GetVideoControlTimeInfoPaser lp = new GetVideoControlTimeInfoPaser();
        NetRequest.getInstance().requestTest(mQueue, this, BgGlobal.GET_VIDEO_CONTROL_DATA, requestData, lp);
    }


    private void requestSaveVideoControllTime() {
        String serialNumber = serialNumberEt.getText().toString();
        if (TextUtils.isEmpty(serialNumber)) {
            ToastUtil.showToast(this,"请输入摄像头序列号",Toast.LENGTH_SHORT);
            return;
        }

        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        requestData.put("classid", mMakeClassAddPersonInfo.getId());
        requestData.put("start_time", startTimetv.getText().toString().trim());
        requestData.put("end_time", endTimeTv.getText().toString().trim());
        requestData.put("serial_number", serialNumber.trim());
        if (timeSwitchCb.isChecked()) {
            requestData.put("is_allow_play", "0");
        } else {
            requestData.put("is_allow_play", "1");
        }

        SaveVideoControlTimeInfoPaser lp = new SaveVideoControlTimeInfoPaser();
        NetRequest.getInstance().requestTest(mQueue, this, BgGlobal.SAVE_VIDEO_CONTROL_DATA, requestData, lp);
    }


    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof GetVideoControlTimeInfo) {
            if (nbs.isSuccess()) {
                GetVideoControlTimeInfo tii = (GetVideoControlTimeInfo)nbs.obj;
                SimpleDateFormat reFormat = new SimpleDateFormat("HH:mm:ss");
                startTimetv.setText(tii.getStart_time());
                endTimeTv.setText(tii.getEnd_time());
                if (TextUtils.equals(tii.getIs_allow_play(),"0")) {
                    timeSwitchCb.setChecked(true);
                } else {
                    timeSwitchCb.setChecked(false);
                }

                if (!TextUtils.equals(tii.getSerial_number(),"0"))
                    serialNumberEt.setText(tii.getSerial_number());
            } else {

            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        } else if (nbs.obj instanceof SaveVideoControlTimeInfo) {
            if (nbs.isSuccess()) {
                finish();
            }

            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
