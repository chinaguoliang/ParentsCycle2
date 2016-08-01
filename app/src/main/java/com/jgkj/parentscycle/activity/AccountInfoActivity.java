package com.jgkj.parentscycle.activity;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AccountInfoAdapter;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.GetVerifyPhoneNumPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/18.
 */
public class AccountInfoActivity extends BaseActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,NetListener {
    private static final String TAG = "AccountInfoActivity";
    @Bind(R.id.account_info_activity_lv)
    ListView mContentLv;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;
    AccountInfoAdapter mAccountInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info_activity_layout);
        ButterKnife.bind(this);
        mAccountInfoAdapter = new AccountInfoAdapter(this, getContentData());
        mContentLv.setAdapter(mAccountInfoAdapter);
        titleTv.setText("帐号信息");
        rightTitleTv.setVisibility(View.GONE);
        mWrapTitleRel.setBackgroundColor(Color.parseColor("#00000000"));
        mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    showDateDialog();
                } else if (position == 6) {
                    //帐号信息
                    startActivity(new Intent(AccountInfoActivity.this,AccountSafeActivity.class));
                }
            }
        });

        requestNet();
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("昵称_ ");
        data.add("姓名_ ");
        data.add("性别_ ");
        data.add("民族_ ");
        data.add("出生日期_ ");
        data.add("手机号_ ");
        data.add("账户安全_0");
        data.add("捆绑微信_ ");
        data.add("捆绑QQ_0");

        return data;
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
       if (v == backIv) {
           finish();
       }
    }

    private void showDateDialog() {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        int year=d.get(Calendar.YEAR);
        int month=d.get(Calendar.MONTH);
        int day=d.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,this,year,month,day);
        dpd.getWindow().setBackgroundDrawable(new BitmapDrawable()); //设置为透明
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + dayOfMonth;
    }

    private void requestNet() {
        if (UserInfo.isLogined) {
            showProgressDialog();
            HashMap<String, String> requestData = new HashMap<String, String>();
            requestData.put("tmpinfoid", UserInfo.loginInfo.getId());
            TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
            NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_LIST, requestData, lp);
        }
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;

        if (nbs.isSuccess()) {
            TeacherInfoListInfo tii = (TeacherInfoListInfo)nbs.obj;
            ArrayList<String> data = new ArrayList<String>();
            data.add("昵称_" + tii.getNickname());
            data.add("姓名_" + tii.getSchoolname());
            data.add("性别_ ");
            data.add("民族_ ");
            data.add("出生日期_ ");
            data.add("手机号_ ");
            data.add("账户安全_ ");
            data.add("捆绑微信_ ");
            data.add("捆绑QQ_ ");
            mAccountInfoAdapter = new AccountInfoAdapter(this, data);
            mContentLv.setAdapter(mAccountInfoAdapter);
            LogUtil.d(TAG,"success");
        } else {
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
