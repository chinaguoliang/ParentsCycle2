package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @Bind(R.id.main_activity_bottom_bar_main_channel_iv)
    ImageView mBtmMainChannelIv;

    @Bind(R.id.main_activity_bottom_bar_main_channel_ll)
    LinearLayout mBtmMainChannelLl;



    @Bind(R.id.main_activity_bottom_bar_dynamic_tv)
    TextView mBtmDynamicTv;

    @Bind(R.id.main_activity_bottom_bar_dynamic_iv)
    ImageView mBtmDynamicIv;

    @Bind(R.id.main_activity_bottom_bar_dynamic_ll)
    LinearLayout mBtmDynamicLl;

    @Bind(R.id.main_activity_bottom_bar_find_tv)
    TextView mBtmFindTv;

    @Bind(R.id.main_activity_bottom_bar_find_iv)
    ImageView mBtmFindIv;

    @Bind(R.id.main_activity_bottom_bar_find_ll)
    LinearLayout mBtmFindLl;

    @Bind(R.id.main_activity_bottom_bar_mine_tv)
    TextView mBtmMineTv;

    @Bind(R.id.main_activity_bottom_bar_mine_iv)
    ImageView mBtmMineIv;

    @Bind(R.id.main_activity_bottom_bar_mine_ll)
    LinearLayout mBtmMineLl;


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


    @OnClick({R.id.main_activity_bottom_bar_main_channel_ll,R.id.main_activity_bottom_bar_dynamic_ll,R.id.main_activity_bottom_bar_find_ll
            ,R.id.main_activity_bottom_bar_mine_ll,R.id.main_activity_bottom_bar_publish_iv})

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = mFragmentManager
                .beginTransaction();
        resetViews(transaction);
        if (v == mBtmMainChannelLl) {
            transaction.show(mHallMainChannelFragement).commitAllowingStateLoss();
        } else if (v == mBtmDynamicLl) {
            mBtmDynamicIv.setImageResource(R.mipmap.main_activity_btm_dynamic_selected);
            if (mHallDynamicFragement.isAdded()) {
                transaction.show(mHallDynamicFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl, mHallDynamicFragement);
                transaction.show(mHallDynamicFragement).commitAllowingStateLoss();
            }
        }  else if (v == mBtmFindLl) {
            mBtmFindIv.setImageResource(R.mipmap.main_activity_find_selected);
            if (mHallFindFragement.isAdded()) {
                transaction.show(mHallFindFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl,mHallFindFragement);
                transaction.show(mHallFindFragement).commitAllowingStateLoss();
            }
        }  else if (v == mBtmMineLl) {
            mBtmMineIv.setImageResource(R.mipmap.main_activity_mine_selected);
            if (mHallMeFragement.isAdded()) {
                transaction.show(mHallMeFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl,mHallMeFragement);
                transaction.show(mHallMeFragement).commitAllowingStateLoss();
            }
        }  else if (v == mBtmPublishIv) {
            mBtmPublishIv.setImageResource(R.mipmap.main_activity_publish_selected);
            if (mHallCircleMenuFragement.isAdded()) {
                transaction.show(mHallCircleMenuFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl, mHallCircleMenuFragement);
                transaction.show(mHallCircleMenuFragement).commitAllowingStateLoss();
            }
        }
    }

    private void resetViews(FragmentTransaction transaction) {

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

        mBtmDynamicIv.setImageResource(R.mipmap.main_activity_btm_dynamic_no_selected);
//        mBtmMainChannelIv
        mBtmPublishIv.setImageResource(R.mipmap.main_activity_publish_no_selected);
        mBtmFindIv.setImageResource(R.mipmap.main_activity_find_no_selected);
        mBtmMineIv.setImageResource(R.mipmap.main_activity_mine_no_selected);
    }
}
