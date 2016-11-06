package com.videogo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.videogo.bean.GetVideoControlTimeInfo;
import com.videogo.bean.GetVideoControlTimeInfoPaser;
import com.videogo.bean.SaveVideoControlTimeInfo;
import com.videogo.bean.SaveVideoControlTimeInfoPaser;

import net.NetBeanSuper;
import net.NetListener;
import net.NetRequest;
import net.NetUrls;

import java.text.SimpleDateFormat;
import java.util.HashMap;


/**
 * Created by chen on 16/10/22.
 */
public class VideoTImeControllActivity extends Activity implements View.OnClickListener, NetListener {
    View titleBg;
    ImageView backIv;
    TextView titleTv;
    TextView rightTv;
    CheckBox timeSwitchCb;
    LinearLayout startTimeLl;
    LinearLayout endTimeLl;
    TextView startTimetv;
    TextView endTimeTv;
    Button confirmBtn;
    EditText serialNumberEt;
    Dialog mProgressDialog;
    RequestQueue mQueue;
    String servialNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.videogo.open.R.layout.video_time_controll_activity);
        mQueue = Volley.newRequestQueue(this);
        servialNumber = this.getIntent().getStringExtra("serial_num");
        findViews();
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

//        mMakeClassAddPersonInfo = (MakeClassAddPersonInfo)this.getIntent().getExtras().getSerializable("class_data");
        titleTv.setText("视频控制");
    }

    private void findViews() {

        titleBg = this.findViewById(com.videogo.open.R.id.title_bar_layout_rel);

        backIv = (ImageView) this.findViewById(com.videogo.open.R.id.baby_document_activity_back_iv);

        titleTv = (TextView) this.findViewById(com.videogo.open.R.id.baby_document_activity_title);

        rightTv = (TextView) this.findViewById(com.videogo.open.R.id.baby_document_right_title_tv);

        timeSwitchCb = (CheckBox) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_time_switch_cb);

        startTimeLl = (LinearLayout) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_start_time_ll);

        startTimetv = (TextView) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_start_time_tv);


        endTimeLl = (LinearLayout) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_end_time_ll);


        endTimeTv = (TextView) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_end_time_tv);


        confirmBtn = (Button) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_confirm_btn);

        serialNumberEt = (EditText) this.findViewById(com.videogo.open.R.id.video_time_controll_activity_serial_number_et);

        backIv.setOnClickListener(this);
        startTimeLl.setOnClickListener(this);
        endTimeLl.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);

    }

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



    private void showDateDialog(int hour,int minute,final TextView dateTv) {
        if (!timeSwitchCb.isChecked()) {
            Toast.makeText(this,"视频处于关闭状态", Toast.LENGTH_SHORT).show();
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
        requestData.put("schoolid", NetUrls.SCHOOL_ID);
        requestData.put("classid", servialNumber);

        GetVideoControlTimeInfoPaser lp = new GetVideoControlTimeInfoPaser();
        NetRequest.getInstance().requestTest(mQueue, this, NetUrls.GET_VIDEO_CONTROL_DATA, requestData, lp);
    }


    private void requestSaveVideoControllTime() {
        String serialNumber = serialNumberEt.getText().toString();
        if (TextUtils.isEmpty(serialNumber)) {
            Toast.makeText(this,"请输入摄像头序列号",Toast.LENGTH_SHORT).show();
            return;
        }

        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", NetUrls.SCHOOL_ID);
        requestData.put("classid", servialNumber);
        requestData.put("start_time", startTimetv.getText().toString().trim());
        requestData.put("end_time", endTimeTv.getText().toString().trim());
        requestData.put("serial_number", serialNumber.trim());
        if (timeSwitchCb.isChecked()) {
            requestData.put("is_allow_play", "0");
        } else {
            requestData.put("is_allow_play", "1");
        }

        SaveVideoControlTimeInfoPaser lp = new SaveVideoControlTimeInfoPaser();
        NetRequest.getInstance().requestTest(mQueue, this, NetUrls.SAVE_VIDEO_CONTROL_DATA, requestData, lp);
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
            Toast.makeText(this,nbs.getMsg(), Toast.LENGTH_SHORT).show();
        } else if (nbs.obj instanceof SaveVideoControlTimeInfo) {
            if (nbs.isSuccess()) {
                finish();
            }

            Toast.makeText(this,nbs.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }


    //防止按钮重复点击
    public boolean showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return false;
        }

        mProgressDialog = ProgressDialog.show(this, "", "请稍后", true, false);
        return true;
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }
}
