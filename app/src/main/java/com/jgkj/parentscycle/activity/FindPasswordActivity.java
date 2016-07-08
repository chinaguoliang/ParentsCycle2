package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/7.
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.find_password_activity_phone_num_et)
    EditText phoneNumEt;

    @Bind(R.id.find_password_activity_phone_verify_num_et)
    EditText phoneVerifyNumEt;

    @Bind(R.id.find_password_activity_next_step_tv)
    TextView nextStepTv;

    @Bind(R.id.find_password_activity_request_verify_num_tv)
    TextView requestVerifyNumTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_activity_layout);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.find_password_activity_phone_num_et,R.id.find_password_activity_phone_verify_num_et,R.id.find_password_activity_next_step_tv})

    @Override
    public void onClick(View v) {
      if (v == nextStepTv) {

      } else if (v == requestVerifyNumTv) {

      }
    }
}
