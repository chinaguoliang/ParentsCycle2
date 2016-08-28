package com.videogo.ui.devicelist;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.videogo.constant.IntentConsts;
import com.videogo.device.DeviceInfoEx;
import com.videogo.device.DeviceManager;
import com.videogo.exception.InnerException;

/**
 * 一键连接设备重置界面
 * 
 * @author chengjuntao
 * @data 2014-4-9
 */
public class ResetIntroduceActivity extends Activity {
    private static final String TAG = "ResetIntroduceActivity";
    public static String IS_FROM_DEVICE_SETTING = "FromPage";

    private View btnBack;

    private TextView btnReset;

    private String seriaNo;

    DeviceInfoEx device;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.videogo.open.R.layout.auto_wifi_reset_introduce);

        btnBack = findViewById(com.videogo.open.R.id.btnBack);
        tvTitle = (TextView) findViewById(com.videogo.open.R.id.tvTitle);
        btnReset = (TextView) findViewById(com.videogo.open.R.id.btnReset);
        btnReset.setText(com.videogo.open.R.string.already_reset);
        seriaNo = getIntent().getStringExtra(IntentConsts.EXTRA_DEVICE_CODE);
        TextView tvTip = (TextView) findViewById(com.videogo.open.R.id.tvTip);
        try {
            device = DeviceManager.getInstance().getDeviceInfoExById(seriaNo);
        } catch (InnerException e) {
            e.printStackTrace();
        }
        
        //设备设置
//        tvTitle.setText(R.string.wifi_set);
//        tvTip.setText(R.string.set_device_wifi_network_need_reset_the_device);
//        btnReset.setText(R.string.next_button_txt);
        
        tvTitle.setText(com.videogo.open.R.string.reset_device);
        tvTip.setText(com.videogo.open.R.string.reset_10_sec_to_release);
        btnReset.setText(com.videogo.open.R.string.already_reset);

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnReset.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
              //设备设置
//                Intent intent = new Intent(ResetIntroduceActivity.this, AutoWifiNetConfigActivity.class);
//                intent.putExtra(IntentConsts.EXTRA_DEVICE_CODE, seriaNo);
//                startActivity(intent);
                
                finish();
            }
        });
    }
}