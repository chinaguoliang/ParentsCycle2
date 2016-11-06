package com.jgkj.parentscycle.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.db.DemoDBManager;
import com.hyphenate.chatuidemo.ui.*;
import com.hyphenate.chatuidemo.ui.LoginActivity;
import com.hyphenate.chatuidemo.ui.MainActivity;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.GetVerifyNumInfo;
import com.jgkj.parentscycle.bean.RegisterInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.json.LoginPaser;
import com.jgkj.parentscycle.json.RegisterPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;
import com.jgkj.parentscycle.widget.ButtonTimeCount;

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

    ButtonTimeCount mTimeCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity_layout);
        ButterKnife.bind(this);
        mTimeCount = new ButtonTimeCount(60000, 1000);
        mTimeCount.setSendButton(getVerifyPhoneNumTv);
    }

    @OnClick({R.id.register_activity_submit_tv,R.id.register_activity_get_verify_phone_num_tv})

    @Override
    public void onClick(View v) {
        if (v == submitTv) {
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }

            String phone = phoneNumEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();
            logout(phone,password);

        } else if (v == getVerifyPhoneNumTv) {
            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }
            mTimeCount.start();
            requestVerifyNum();
        }
    }

    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (nbs.obj instanceof RegisterInfo) {
            RegisterInfo ri = (RegisterInfo)nbs.obj;
            if (nbs.isSuccess()) {
                ToastUtil.showToast(this,"成功",Toast.LENGTH_SHORT);
                finish();
            } else {
                ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
            }
        } else if (nbs.obj instanceof GetVerifyNumInfo) {
            GetVerifyNumInfo gvn = (GetVerifyNumInfo)nbs.obj ;
            LogUtil.d(TAG,"get verify 1");
            if (nbs.isSuccess()) {
                LogUtil.d(TAG,"get verify 2");
                ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
            } else {
                ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
                LogUtil.d(TAG, "get verify 3" + nbs.getMsg());
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

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }


    private void registerChatAndLogin(final String userName,final String userNamePwd) {
        showProgressDialog();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String userNameMd5Str = UtilTools.getMD5(userName);
                    EMClient.getInstance().createAccount(userNameMd5Str, userNamePwd);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            // save current user
                            DemoHelper.getInstance().setCurrentUserName(userNameMd5Str);
                            loginChat(userName,userNamePwd);
                        }
                    });
                } catch (Exception e) {
                    hideProgressDialog();
                }
            }
        }).start();
    }


    private void loginChat(String userName,String userPwd) {
        final String userNameMd5Str = UtilTools.getMD5(userName);
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(userNameMd5Str);

        final long start = System.currentTimeMillis();
        // call login method
        LogUtil.d("result","----->userName:" + EMClient.getInstance().getCurrentUser() + "|" + userNameMd5Str);
        EMClient.getInstance().login(userNameMd5Str, userPwd, new EMCallBack() {

            @Override
            public void onSuccess() {

                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        DemoApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }

                // get user's info (this should be get from App's server or 3rd party service)
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                hideProgressDialog();
                requestRegister();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                runOnUiThread(new Runnable() {
                    public void run() {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(), getString(com.hyphenate.chatuidemo.R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    void logout(final String phone,final String password) {
        DemoHelper.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                registerChatAndLogin(phone,password);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }
}
