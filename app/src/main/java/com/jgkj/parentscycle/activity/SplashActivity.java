package com.jgkj.parentscycle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.jgkj.parentscycle.R;
import android.os.Handler;
/**
 * Created by chen on 16/7/6.
 */
public class SplashActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        }, 2000);
    }
}
