package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.LoginPaser;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.utils.LogUtil;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Bind;

import butterknife.OnClick;

/**
 * Created by chen on 16/7/6.
 */
public class LoginActivity extends BaseActivity implements NetListener,View.OnClickListener {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.login_activity_login_tv)
    TextView loginTv;

    @Bind(R.id.login_activity_register_tv)
    TextView registerTv;

    @Bind(R.id.login_activity_forget_pass_tv)
    TextView forgetPassTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        ButterKnife.bind(this);
    }

    public void requestRegister() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        LoginPaser lp = new LoginPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.REGISTER_URL, requestData, lp);
    }


    @OnClick({R.id.login_activity_login_tv,R.id.login_activity_register_tv,R.id.login_activity_forget_pass_tv})

    @Override
    public void onClick(View v) {
        if (v == loginTv) {
            requestRegister();
        } else if (v == registerTv) {
            startActivity(new Intent(this,RegisterActivity.class));
        } else if (v == forgetPassTv) {
            startActivity(new Intent(this,FindPasswordActivity.class));
        }
    }

    @Override
    public void requestResponse(Object obj) {
        if (obj instanceof  LoginInfo) {
            LoginInfo loginInfo = (LoginInfo)obj;
            LogUtil.d(TAG,"response:" + loginInfo.getStatus());
        }
    }
}
