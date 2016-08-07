package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.AccountSafeModifyPhoneInfo;
import com.jgkj.parentscycle.bean.CheckFindPasswordInfo;
import com.jgkj.parentscycle.bean.GetVerifyNumInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.AccountSafeModifyPhonePaser;
import com.jgkj.parentscycle.json.CheckFindResetPasswordPaser;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.widget.ButtonTimeCount;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/2.
 */
public class AccountSafeResetPhoneActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView submitTv;

    @Bind(R.id.register_activity_get_verify_phone_num_tv)
    TextView getVerifyNumTv;

    @Bind(R.id.account_safe_reset_phone_activity_verify_num_et)
    TextView verifyNumEt;

    @Bind(R.id.account_safe_reset_phone_activity_phone_et)
    TextView phoneEt;

    String phoneStr;
    String verifyNumberStr;
    ButtonTimeCount mTimeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_safe_reset_phone_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        submitTv.setText("提交");
        submitTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        titleTv.setText("更换手机号");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);
        mTimeCount = new ButtonTimeCount(60000, 1000);
        mTimeCount.setSendButton(getVerifyNumTv);
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.baby_document_right_title_tv,R.id.register_activity_get_verify_phone_num_tv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == submitTv) {
            phoneStr = phoneEt.getText().toString();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtil.showToast(this, "请输入手机号", Toast.LENGTH_SHORT);
                return;
            }

            verifyNumberStr = verifyNumEt.getText().toString();
            if (TextUtils.isEmpty(verifyNumberStr)) {
                ToastUtil.showToast(this, "请输入验证码", Toast.LENGTH_SHORT);
                return;
            }

            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            requestResetPassword();


        } else if (v == getVerifyNumTv) {
            phoneStr = phoneEt.getText().toString();
            if (TextUtils.isEmpty(phoneStr)) {
                ToastUtil.showToast(this, "请输入手机号", Toast.LENGTH_SHORT);
                return;
            }
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            mTimeCount.start();
            requestVerifyNum();
        }
    }

    public void requestVerifyNum() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone",phoneStr);
        GetVerifyPhoneNumPaser lp = new GetVerifyPhoneNumPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.VERIFY_PHONE_NUMBER_URL, requestData, lp);
    }

    public void requestModifyPhone() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone",phoneStr);
        requestData.put("oldphone", UserInfo.loginInfo.getPhone());
        requestData.put("role","1"); //暂时定为1
        AccountSafeModifyPhonePaser lp = new AccountSafeModifyPhonePaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.MODIFY_PHONE_BY_OLD_PHONE, requestData, lp);
    }

    public void requestResetPassword() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", phoneStr);
        requestData.put("phoneCode",verifyNumberStr);
        CheckFindResetPasswordPaser lp = new CheckFindResetPasswordPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.CHECK_RESET_PASSWORD, requestData, lp);

    }

    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;
        hideProgressDialog();
        if (nbs.obj instanceof GetVerifyNumInfo) {
            GetVerifyNumInfo gvn = (GetVerifyNumInfo)nbs.obj ;
            if (nbs.isSuccess()) {
                ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
            } else {
                ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }else if (nbs.obj instanceof CheckFindPasswordInfo) {
            CheckFindPasswordInfo cfpi = (CheckFindPasswordInfo)nbs.obj;
            if (nbs.isSuccess()) {
                requestModifyPhone();
            } else {
                ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }else if (nbs.obj instanceof AccountSafeModifyPhoneInfo) {
            AccountSafeModifyPhoneInfo cfpi = (AccountSafeModifyPhoneInfo)nbs.obj;
            if (nbs.isSuccess()) {
                finish();
            }
            ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
