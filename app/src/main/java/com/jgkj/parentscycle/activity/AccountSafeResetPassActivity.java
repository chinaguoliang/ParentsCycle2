package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.CreateClassInfo;
import com.jgkj.parentscycle.bean.ModifyPassByOldPassInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.ModifyPassByOldPassPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
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
 * Created by chen on 16/8/2.
 */
public class AccountSafeResetPassActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView submitTv;

    @Bind(R.id.account_safe_reset_pass_activity_current_pass_et)
    EditText currentPassEt;

    @Bind(R.id.account_safe_reset_pass_activity_new_pass_et)
    EditText newPassEt;

    @Bind(R.id.account_safe_reset_pass_activity_second_new_pass_et)
    EditText secondNewPassEt;

    String oldPassStr;
    String newPassStr;
    String secondPassStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_safe_reset_pass_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        submitTv.setText("提交");
        submitTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        titleTv.setText("修改密码");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.baby_document_right_title_tv})
    @Override
    public void onClick(View v) {
       if (v == backIv) {
            finish();
        } else if (v == submitTv) {
           oldPassStr = currentPassEt.getText().toString();
           newPassStr = newPassEt.getText().toString();
           secondPassStr = secondNewPassEt.getText().toString();
           if (TextUtils.isEmpty(oldPassStr)) {
               ToastUtil.showToast(this,"请输入旧密码",Toast.LENGTH_SHORT);
               return;
           }

           if (TextUtils.isEmpty(newPassStr)) {
               ToastUtil.showToast(this,"请输入新密码",Toast.LENGTH_SHORT);
               return;
           }

           if (TextUtils.isEmpty(secondPassStr)) {
               ToastUtil.showToast(this,"请再次输入新密码",Toast.LENGTH_SHORT);
               return;
           }

           boolean hadShow = showProgressDialog();
           if (!hadShow) {
               return;
           }
           requestModifyPass();
       }
    }

    //修改密码
    private void requestModifyPass() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("phone", UserInfo.loginInfo.getInfo().getPhone());  //登录时ID
        requestData.put("newpass", newPassStr);
        requestData.put("oldpass", oldPassStr);
        ModifyPassByOldPassPaser lp = new ModifyPassByOldPassPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.MODIFY_PASS_BY_OLD_PASS, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ModifyPassByOldPassInfo) {
            ModifyPassByOldPassInfo ccI = (ModifyPassByOldPassInfo)nbs.obj;
            if (nbs.isSuccess()) {
                finish();
            }
            ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap) {

    }
}
