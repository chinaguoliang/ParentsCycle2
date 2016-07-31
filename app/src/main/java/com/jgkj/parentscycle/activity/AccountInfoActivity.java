package com.jgkj.parentscycle.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AccountInfoAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/18.
 */
public class AccountInfoActivity extends BaseActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {
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
        mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    showDateDialog();
                }
            }
        });
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("昵称_老师");
        data.add("姓名_小李");
        data.add("性别_女");
        data.add("民族_汉");
        data.add("出生日期_1985");
        data.add("手机号_13673668068");
        data.add("账户安全_0");
        data.add("捆绑微信_已捆绑");
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
        DatePickerDialog dpd = new DatePickerDialog(this,this,year,month,day);
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

    }
}
