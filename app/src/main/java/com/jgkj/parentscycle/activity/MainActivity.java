package com.jgkj.parentscycle.activity;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
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
    TextView mBtmMainChannelTv;

    @Bind(R.id.main_activity_bottom_bar_dynamic_tv)
    TextView mBtmDynamicTv;

    @Bind(R.id.main_activity_bottom_bar_find_tv)
    TextView mBtmFindTv;

    @Bind(R.id.main_activity_bottom_bar_mine_tv)
    TextView mBtmMineTv;

    @Bind(R.id.main_activity_bottom_bar_publish_iv)
    ImageView mBtmPublishIv;




    private  HallCircleMenuFragement mHallCircleMenuFragement;
    private  HallDynamicFragement mHallDynamicFragement;
    private  HallFindFragement mHallFindFragement;
    private  HallMainChannelFragement mHallMainChannelFragement;
    private  HallMeFragement mHallMeFragement;
    private  FragmentManager mFragmentManager;


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

        mFragmentManager = this.getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.main_activity_content_fl,mHallMainChannelFragement).commit();
    }


    @OnClick({R.id.main_activity_bottom_bar_main_channel_tv,R.id.main_activity_bottom_bar_dynamic_tv,R.id.main_activity_bottom_bar_find_tv
            ,R.id.main_activity_bottom_bar_mine_tv,R.id.main_activity_bottom_bar_publish_iv})

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = mFragmentManager
                .beginTransaction();
        hideAllFrament(transaction);
        if (v == mBtmMainChannelTv) {
            transaction.show(mHallMainChannelFragement).commitAllowingStateLoss();
        } else if (v == mBtmDynamicTv) {
            if (mHallDynamicFragement.isAdded()) {
                transaction.show(mHallDynamicFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl, mHallDynamicFragement);
                transaction.show(mHallDynamicFragement).commitAllowingStateLoss();
            }
        }  else if (v == mBtmFindTv) {
            if (mHallFindFragement.isAdded()) {
                transaction.show(mHallFindFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl,mHallFindFragement);
                transaction.show(mHallFindFragement).commitAllowingStateLoss();
            }
        }  else if (v == mBtmMineTv) {
            if (mHallMeFragement.isAdded()) {
                transaction.show(mHallMeFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl,mHallMeFragement);
                transaction.show(mHallMeFragement).commitAllowingStateLoss();
            }
        }  else if (v == mBtmPublishIv) {
            if (mHallCircleMenuFragement.isAdded()) {
                transaction.show(mHallCircleMenuFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl, mHallCircleMenuFragement);
                transaction.show(mHallCircleMenuFragement).commitAllowingStateLoss();
            }
        }
    }

    private void hideAllFrament(FragmentTransaction transaction) {

        if (mHallCircleMenuFragement.isAdded()) {
            transaction.hide(mHallCircleMenuFragement);
        }

        if (mHallDynamicFragement.isAdded()) {
            transaction.hide(mHallDynamicFragement);
        }

        if (mHallFindFragement.isAdded()) {
            transaction.hide(mHallFindFragement);
        }

        if (mHallMainChannelFragement.isAdded()) {
            transaction.hide(mHallMainChannelFragement);
        }

        if (mHallMeFragement.isAdded()) {
            transaction.hide(mHallMeFragement);
        }
    }
}
