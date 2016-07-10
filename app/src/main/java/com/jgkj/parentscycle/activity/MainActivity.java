package com.jgkj.parentscycle.activity;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.fragement.HallCircleMenuFragement;
import com.jgkj.parentscycle.fragement.HallDynamicFragement;
import com.jgkj.parentscycle.fragement.HallFindFragement;
import com.jgkj.parentscycle.fragement.HallMainChannelFragement;
import com.jgkj.parentscycle.fragement.HallMeFragement;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.main_activity_bottom_bar_main_channel_tv)
    private TextView mBtmMainChannelTv;

    @Bind(R.id.main_activity_bottom_bar_dynamic_tv)
    private TextView mBtmDynamicTv;

    @Bind(R.id.main_activity_bottom_bar_find_tv)
    private TextView mBtmFindTv;

    @Bind(R.id.main_activity_bottom_bar_mine_tv)
    private TextView mBtmMineTv;

    @Bind(R.id.main_activity_bottom_bar_publish_iv)
    private TextView mBtmPublishIv;




    private  HallCircleMenuFragement mHallCircleMenuFragement;
    private  HallDynamicFragement mHallDynamicFragement;
    private  HallFindFragement mHallFindFragement;
    private  HallMainChannelFragement mHallMainChannelFragement;
    private  HallMeFragement mHallMeFragement;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        mHallCircleMenuFragement = new HallCircleMenuFragement();
        mHallDynamicFragement = new HallDynamicFragement();
        mHallFindFragement = new HallFindFragement();
        mHallMainChannelFragement = new HallMainChannelFragement();
        mHallMeFragement = new HallMeFragement();

        FragmentManager mFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(R.id.main_activity_content_fl,mHallMainChannelFragement).commit();
    }


    @OnClick({R.id.main_activity_bottom_bar_main_channel_tv,R.id.main_activity_bottom_bar_dynamic_tv,R.id.main_activity_bottom_bar_find_tv
            ,R.id.main_activity_bottom_bar_mine_tv,R.id.main_activity_bottom_bar_publish_iv})

    @Override
    public void onClick(View v) {
        if (v == mBtmMainChannelTv) {

        } else if (v == mBtmDynamicTv) {

        }  else if (v == mBtmFindTv) {

        }  else if (v == mBtmMineTv) {

        }  else if (v == mBtmPublishIv) {

        }
    }
}
