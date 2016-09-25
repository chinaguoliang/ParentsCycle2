package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.user.UserInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/9/25.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }


    @Bind(R.id.setting_activity_logout_btn)
    Button loginOutBtn;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_layout);
        ButterKnife.bind(this);
        titleTv.setText("设置");
        titleTv.setTextColor(Color.BLACK);
        rightTitleTv.setVisibility(View.GONE);
        mWrapTitleRel.setBackgroundColor(Color.WHITE);
    }


    @OnClick({R.id.baby_document_activity_back_iv,R.id.setting_activity_logout_btn})

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == loginOutBtn) {
            UserInfo.isLogined = false;
            finish();
        }
    }
}
