package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ResetPasswordInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
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

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;


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
        rightTv.setVisibility(View.GONE);
        titleTv.setText("设置新密码");
    }

    @OnClick({R.id.set_password_activity_submit_tv,R.id.baby_document_activity_back_iv})

    @Override
    public void onClick(View v) {
        if (v == submitTv) {
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            requestSetNewPass();
        } else if (v == backIv) {
            finish();
        }
    }

    private void requestSetNewPass() {
        String pass1 = newPassEt.getText().toString();
        String pass2 = secondNewPassEt.getText().toString();
        if (!TextUtils.equals(pass1,pass2)) {
            ToastUtil.showToast(this,"2次输入密码不一致",Toast.LENGTH_SHORT);
            return;
        }

        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", mPhone);
        requestData.put("passwd",newPassEt.getText().toString().trim());
        ResetPasswordPaser lp = new ResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.RESET_PASSWORD, requestData, lp);

    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ResetPasswordInfo) {
            ResetPasswordInfo rpi = (ResetPasswordInfo) nbs.obj;
            if (nbs.isSuccess()) {
                UserInfo.phoneNumber = mPhone;
                UserInfo.isLogined = true;
                ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
                finish();
            } else {
                ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap) {

    }
}
