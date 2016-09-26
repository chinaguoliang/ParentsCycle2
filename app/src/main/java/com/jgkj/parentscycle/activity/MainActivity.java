package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.AnnouncementListInfo;
import com.jgkj.parentscycle.bean.AnnouncementListItem;
import com.jgkj.parentscycle.bean.BabyDocumentListInfo;
import com.jgkj.parentscycle.fragement.HallPublishMenuFragment;
import com.jgkj.parentscycle.fragement.HallDynamicFragement;
import com.jgkj.parentscycle.fragement.HallFindFragement;
import com.jgkj.parentscycle.fragement.HallMainChannelFragement;
import com.jgkj.parentscycle.fragement.HallMeFragement;
import com.jgkj.parentscycle.global.ActivityResultCode;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.AnnouncementListPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ImageHandler;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener,NetListener{
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


    public ImageHandler handler = new ImageHandler(new WeakReference<MainActivity>(this));
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

    public void setViewPagerCurrentItem(int position) {
        if (mHallMainChannelFragement != null) {
            mHallMainChannelFragement.setViewPagerCurrentItem(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHallMeFragement != null) {
            mHallMeFragement.refreshUI();
        }
    }

    private void initFragment() {
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

        if (v == mBtmMainChannelLl) {
            resetViews(transaction);
            mBtmMainChannelIv.setImageResource(R.mipmap.main_activity_home_channel_no_selected);
            transaction.show(mHallMainChannelFragement).commitAllowingStateLoss();
            mBtmMainChannelTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));
        } else if (v == mBtmDynamicLl) {
            resetViews(transaction);
            mBtmDynamicIv.setImageResource(R.mipmap.main_activity_btm_dynamic_selected);
            if (mHallDynamicFragement.isAdded()) {
                transaction.show(mHallDynamicFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl, mHallDynamicFragement);
                transaction.show(mHallDynamicFragement).commitAllowingStateLoss();
            }
            mBtmDynamicTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));
            requestAnnouncementList();
        }  else if (v == mBtmFindLl) {
            resetViews(transaction);
            mBtmFindIv.setImageResource(R.mipmap.main_activity_find_selected);
            if (mHallFindFragement.isAdded()) {
                transaction.show(mHallFindFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl,mHallFindFragement);
                transaction.show(mHallFindFragement).commitAllowingStateLoss();
            }
            mBtmFindTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));
        }  else if (v == mBtmMineLl) {
            resetViews(transaction);

            mBtmMineIv.setImageResource(R.mipmap.main_activity_mine_selected);
            if (mHallMeFragement.isAdded()) {
                transaction.show(mHallMeFragement).commitAllowingStateLoss();
            } else {
                transaction.add(R.id.main_activity_content_fl,mHallMeFragement);
                transaction.show(mHallMeFragement).commitAllowingStateLoss();
            }
            mBtmMineTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));
        }  else if (v == mBtmPublishIv) {
           startActivityForResult(new Intent(this,CircleMenuActivity.class),10);
        }
    }

    private void resetViews(FragmentTransaction transaction) {


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
        mBtmMainChannelIv.setImageResource(R.mipmap.main_activity_home_channel_gray_selected);
        mBtmPublishIv.setImageResource(R.mipmap.main_activity_publish_no_selected);
        mBtmFindIv.setImageResource(R.mipmap.main_activity_find_no_selected);
        mBtmMineIv.setImageResource(R.mipmap.main_activity_mine_no_selected);

        mBtmMainChannelTv.setTextColor(this.getResources().getColor(R.color.text_gray_2));
        mBtmDynamicTv.setTextColor(this.getResources().getColor(R.color.text_gray_2));
        mBtmFindTv.setTextColor(this.getResources().getColor(R.color.text_gray_2));
        mBtmMineTv.setTextColor(this.getResources().getColor(R.color.text_gray_2));
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ActivityResultCode.MY_PUBLISH_CODE) {
            mBtmFindLl.performClick();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof AnnouncementListInfo) {
            if (nbs.isSuccess()) {
                AnnouncementListInfo bdlii = (AnnouncementListInfo)nbs.obj;
                mHallDynamicFragement.setDataList(bdlii.getDataList());
            } else {

            }

            ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }

    //公告列表
    private void requestAnnouncementList() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("id", UserInfo.loginInfo.getRole().getId());
        requestData.put("page", "1");
        requestData.put("rows", "10");
        AnnouncementListPaser lp = new AnnouncementListPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_LIST, requestData, lp);
    }
}
