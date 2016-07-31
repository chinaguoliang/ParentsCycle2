package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.CheckFindPasswordInfo;
import com.jgkj.parentscycle.bean.GetVerifyNumInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.CheckFindResetPasswordPaser;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.widget.ButtonTimeCount;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/7.
 */
public class FindPasswordActivity extends BaseActivity implements View.OnClickListener,NetListener {
    private static final String TAG = "FindPasswordActivity";

    @Bind(R.id.find_password_activity_phone_num_et)
    EditText phoneNumEt;

    @Bind(R.id.find_password_activity_phone_verify_num_et)
    EditText phoneVerifyNumEt;

    @Bind(R.id.find_password_activity_next_step_tv)
    TextView nextStepTv;

    @Bind(R.id.find_password_activity_request_verify_num_tv)
    TextView requestVerifyNumTv;



    ButtonTimeCount mTimeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_activity_layout);
        ButterKnife.bind(this);
        mTimeCount = new ButtonTimeCount(60000, 1000);
        mTimeCount.setSendButton(requestVerifyNumTv);
    }

    @OnClick({R.id.find_password_activity_phone_num_et,R.id.find_password_activity_phone_verify_num_et,R.id.find_password_activity_next_step_tv,R.id.find_password_activity_request_verify_num_tv})

    @Override
    public void onClick(View v) {
      if (v == nextStepTv) {
          boolean hadShow = showProgressDialog();
          if (!hadShow) {
              return;
          }
          requestResetPassword();
      } else if (v == requestVerifyNumTv) {
          boolean hadShow = showProgressDialog();
          if (!hadShow) {
              return;
          }
          mTimeCount.start();
          requestVerifyNum();
      }
    }

    public void requestVerifyNum() {
        String phone = phoneNumEt.getText().toString();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone",phone);
        GetVerifyPhoneNumPaser lp = new GetVerifyPhoneNumPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.VERIFY_PHONE_NUMBER_URL, requestData, lp);
    }

    public void requestResetPassword() {
        String phone = phoneNumEt.getText().toString();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", phone);
        requestData.put("phoneCode",phoneVerifyNumEt.getText().toString());
        CheckFindResetPasswordPaser lp = new CheckFindResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.CHECK_RESET_PASSWORD, requestData, lp);

    }

    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (nbs.obj instanceof GetVerifyNumInfo) {
            GetVerifyNumInfo gvn = (GetVerifyNumInfo)nbs.obj;
            LogUtil.d(TAG, "get verify 1");
            if (nbs.isSuccess()) {
                LogUtil.d(TAG,"get verify 2");
                ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
            } else {
                ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
                LogUtil.d(TAG, "get verify 3" + nbs.getMsg());
            }
        } else if (nbs.obj instanceof CheckFindPasswordInfo) {
            CheckFindPasswordInfo cfpi = (CheckFindPasswordInfo)nbs.obj;
            if (nbs.isSuccess()) {
                ToastUtil.showToast(this, "成功", Toast.LENGTH_SHORT);
                Intent intent = new Intent(FindPasswordActivity.this,SetPasswordActivity.class);
                intent.putExtra("phone",phoneNumEt.getText().toString());
                startActivity(intent);
            } else {
                ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
