package com.jgkj.parentscycle.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.GetVerifyNumInfo;
import com.jgkj.parentscycle.bean.RegisterInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.json.LoginPaser;
import com.jgkj.parentscycle.json.RegisterPaser;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/7.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener,NetListener {
    private static final String TAG = "RegisterActivity";

    @Bind(R.id.register_activity_phone_num_et)
    EditText phoneNumEt;

    @Bind(R.id.register_activity_password_et)
    EditText passwordEt;

    @Bind(R.id.register_activity_submit_tv)
    TextView submitTv;

    @Bind(R.id.register_activity_get_verify_phone_num_tv)
    TextView getVerifyPhoneNumTv;

    @Bind(R.id.register_activity_verify_phone_num_et)
    EditText phoneVerifyNumEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register_activity_submit_tv,R.id.register_activity_get_verify_phone_num_tv})

    @Override
    public void onClick(View v) {
        if (v == submitTv) {
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            requestRegister();
        } else if (v == getVerifyPhoneNumTv) {
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            requestVerifyNum();
        }
    }

    @Override
    public void requestResponse(Object obj) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (obj instanceof RegisterInfo) {
            RegisterInfo ri = (RegisterInfo)obj;
            if (ri.isSuccess()) {
                ToastUtil.showToast(this,"成功",Toast.LENGTH_SHORT);
                finish();
            } else {
                ToastUtil.showToast(this,ri.getMsg(),Toast.LENGTH_SHORT);
            }
        } else if (obj instanceof GetVerifyNumInfo) {
            GetVerifyNumInfo gvn = (GetVerifyNumInfo)obj;
            LogUtil.d(TAG,"get verify 1");
            if (gvn.isSuccess()) {
                LogUtil.d(TAG,"get verify 2");
                ToastUtil.showToast(this,"成功",Toast.LENGTH_SHORT);
            } else {
                ToastUtil.showToast(this,gvn.getMsg(),Toast.LENGTH_SHORT);
                LogUtil.d(TAG, "get verify 3" + gvn.getMsg());
            }
        }
    }

    public void requestRegister() {
        String phone = phoneNumEt.getText().toString().trim();
        String password = passwordEt.getText().toString().trim();
        String verifyNum = phoneVerifyNumEt.getText().toString().trim();


        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone",phone);
        requestData.put("passwd",password);
        requestData.put("phoneCode",verifyNum);


        RegisterPaser lp = new RegisterPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.REGISTER_URL, requestData, lp);
    }

    public void requestVerifyNum() {
        String phone = phoneNumEt.getText().toString();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone",phone);
        GetVerifyPhoneNumPaser lp = new GetVerifyPhoneNumPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.VERIFY_PHONE_NUMBER_URL, requestData, lp);
    }
}
