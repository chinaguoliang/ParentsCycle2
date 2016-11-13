package com.jgkj.parentscycle.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.hyphenate.EMCallBack;
//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chatuidemo.DemoApplication;
//import com.hyphenate.chatuidemo.DemoHelper;
//import com.hyphenate.chatuidemo.db.DemoDBManager;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.LoginPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Bind;

import butterknife.OnClick;

/**
 * Created by chen on 16/7/6.
 */
public class LoginActivity extends BaseActivity implements NetListener,View.OnClickListener {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.login_activity_user_name_et)
    EditText userNameEt;

    @Bind(R.id.login_activity_password_et)
    EditText passwordEt;


    @Bind(R.id.login_activity_login_tv)
    TextView loginTv;

    @Bind(R.id.login_activity_register_tv)
    TextView registerTv;

    @Bind(R.id.login_activity_forget_pass_tv)
    TextView forgetPassTv;

    @Bind(R.id.login_activity_layout_Immediate_experience_btn)
    Button immediateExperienceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_layout);
        ButterKnife.bind(this);
    }

    public void requestLogin(String phone,String password) {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", phone);
        requestData.put("passwd", password);
        LoginPaser lp = new LoginPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.LOGIN_URL, requestData, lp);
    }


    @OnClick({R.id.login_activity_login_tv,R.id.login_activity_register_tv,R.id.login_activity_forget_pass_tv,R.id.login_activity_layout_Immediate_experience_btn})

    @Override
    public void onClick(View v) {
        if (v == loginTv) {

            String phone = userNameEt.getText().toString();
            String password = passwordEt.getText().toString();

            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showToast(this,"手机号不能为空",Toast.LENGTH_SHORT);
                return;
            }

            if (TextUtils.isEmpty(password)) {
                ToastUtil.showToast(this,"密码不能为空",Toast.LENGTH_SHORT);
                return;
            }


            boolean hadShow = showProgressDialog();
            if (!hadShow) {
                return;
            }

            requestLogin(phone,password);
            //loginChat(phone,password);

        } else if (v == registerTv) {
            startActivity(new Intent(this,RegisterActivity.class));
        } else if (v == forgetPassTv) {
            startActivity(new Intent(this,FindPasswordActivity.class));
        } else if (v == immediateExperienceBtn) {
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }


    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }

        if (nbs.obj instanceof  LoginInfo) {
            if (nbs.isSuccess()) {
                LoginInfo loginInfo = (LoginInfo)nbs.obj;
                Log.d("result",nbs.getMsg());
                UserInfo.isLogined = true;
                UserInfo.loginInfo = loginInfo;
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
            ToastUtil.showToast(this,nbs.getMsg(),Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

//    private void loginChat(final String userName,final String userPwd) {
//        showProgressDialog();
//        final String userNameMd5Str = UtilTools.getMD5(userName);
//        DemoDBManager.getInstance().closeDB();
//
//        // reset current user name before login
//        DemoHelper.getInstance().setCurrentUserName(userNameMd5Str);
//
//        final long start = System.currentTimeMillis();
//        // call login method
//        Log.d(TAG, "EMClient.getInstance().login");
//        EMClient.getInstance().login(userNameMd5Str, userPwd, new EMCallBack() {
//
//            @Override
//            public void onSuccess() {
//
//                // ** manually load all local groups and conversation
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//
//                // update current user's display name for APNs
//                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
//                        DemoApplication.currentUserNick.trim());
//                if (!updatenick) {
//                    Log.e("LoginActivity", "update current user nick fail");
//                }
//
//                // get user's info (this should be get from App's server or 3rd party service)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
//                hideProgressDialog();
//                requestLogin(userName,userPwd);
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//                Log.d(TAG, "login: onProgress");
//            }
//
//            @Override
//            public void onError(final int code, final String message) {
//                Log.d(TAG, "login: onError: " + code);
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        hideProgressDialog();
//                        Toast.makeText(getApplicationContext(), getString(com.hyphenate.chatuidemo.R.string.Login_failed) + message,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }
}
