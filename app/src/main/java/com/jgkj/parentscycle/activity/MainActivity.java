package com.jgkj.parentscycle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jgkj.parentscycle.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        startActivity(new Intent(this,LoginActivity.class));
    }
}
