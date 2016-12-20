package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.jgkj.parentscycle.R;

/**
 * Created by Administrator on 2016/12/20.
 */
public class AddBabyActivity extends BaseActivity {
    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_baby_activity);
    }
}
