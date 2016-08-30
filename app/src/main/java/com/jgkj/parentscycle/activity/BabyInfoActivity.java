package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.jgkj.parentscycle.R;

import butterknife.ButterKnife;

/**
 * Created by chen on 16/8/30.
 */
public class BabyInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_info_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }
}
