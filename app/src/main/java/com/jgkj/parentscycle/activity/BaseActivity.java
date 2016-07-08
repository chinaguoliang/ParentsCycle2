package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.jgkj.parentscycle.utils.ActivityManager;
import com.jgkj.parentscycle.utils.LogUtil;


public class BaseActivity extends FragmentActivity {
    public RequestQueue mQueue;
    private PopupWindow mMsgPopWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);
        ActivityManager.getInstance().pushActivity(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        ActivityManager.getInstance().popActivity(this);
        //CusDialogManager.getInstance().closeCusDailog();
    }

    public void exitApp() {
        ActivityManager.getInstance().popAllActivityExceptOne(getClass());
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //showTopbarPopWindow();
    }



    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }





    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }
        return super.onKeyUp(keyCode, event);
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mMsgPopWindow != null) {
            mMsgPopWindow.dismiss();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (this instanceof MainActivity) {
//
//        }

        super.onActivityResult(requestCode, resultCode, data);

}

    public void delayFinish() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },300);
    }



}
