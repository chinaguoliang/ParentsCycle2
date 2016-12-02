package com.jgkj.parentscycle.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.Volley;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.LoginPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.PreferenceUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by chen on 16/7/6.
 */
public class SplashActivity extends Activity implements NetListener {
    private String userName;
    private String password;
    //首页
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_layout);


        userName = PreferenceUtil.getStringKey(this,"jgkj_user_name");
        password = PreferenceUtil.getStringKey(this,"jgkjpassword");

        WindowManager wm = this.getWindowManager();
        UtilTools.SCREEN_WIDTH = wm.getDefaultDisplay().getWidth();
        UtilTools.SCREEN_HEIGHT = wm.getDefaultDisplay().getHeight();

        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestLogin(userName,password);
                }
            }, 2000);

        } else {
            UserInfo.isLogined = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    finish();
                }
            }, 2000);
        }

    }

    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof LoginInfo) {
            if (nbs.isSuccess()) {
                PreferenceUtil.setStringKey(SplashActivity.this,"jgkj_user_name",userName);
                PreferenceUtil.setStringKey(SplashActivity.this,"jgkjpassword",password);
                LoginInfo loginInfo = (LoginInfo)nbs.obj;
                Log.d("result",nbs.getMsg());
                UserInfo.isLogined = true;
                UserInfo.loginInfo = loginInfo;
                ConfigPara.SCHOOL_ID = loginInfo.getSchoolid();
                LogUtil.d("get school id:","---->" + ConfigPara.SCHOOL_ID);
                startActivity(new Intent(this,MainActivity.class));
                finish();
            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }

    public void requestLogin(String phone,String password) {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", phone);
        requestData.put("passwd", password);
        LoginPaser lp = new LoginPaser();
        NetRequest.getInstance().request(Volley.newRequestQueue(this), this,
                BgGlobal.LOGIN_URL, requestData, lp);
    }
}
