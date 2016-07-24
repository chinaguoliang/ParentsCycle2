package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ResetPasswordInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/7.
 */
public class SetPasswordActivity extends BaseActivity implements View.OnClickListener, NetListener {
    @Bind(R.id.set_password_activity_new_pass_et)
    EditText newPassEt;

    @Bind(R.id.set_password_activity_second_new_pass_et)
    EditText secondNewPassEt;

    @Bind(R.id.set_password_activity_submit_tv)
    TextView submitTv;

    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_password_activity_layout);
        ButterKnife.bind(this);
        mPhone = this.getIntent().getStringExtra("phone");
    }

    @OnClick({R.id.set_password_activity_submit_tv})

    @Override
    public void onClick(View v) {
        if (v == submitTv) {
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            requestSetNewPass();
        }
    }

    private void requestSetNewPass() {


        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", mPhone);
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.RESET_PASSWORD, requestData, lp);

    }

    @Override
    public void requestResponse(Object obj) {
        if (obj instanceof ResetPasswordInfo) {
            ResetPasswordInfo rpi = (ResetPasswordInfo) obj;
            if (rpi.isSuccess()) {
                UserInfo.phoneNumber = mPhone;
                UserInfo.isLogined = true;
                finish();
            } else {
                ToastUtil.showToast(this, rpi.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
