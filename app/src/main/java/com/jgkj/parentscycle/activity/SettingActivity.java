package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.PreferenceUtil;
import com.jgkj.parentscycle.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/9/25.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }


    @Bind(R.id.setting_activity_logout_btn)
    Button loginOutBtn;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.setting_activity_layout_download_pic_tv)
    TextView downloadPicTv;

    @Bind(R.id.setting_activity_layout_download_video_tv)
    TextView downloadVideoTv;

    @Bind(R.id.setting_activity_layout_upload_data_tv)
    TextView uploadDataTv;

    @Bind(R.id.setting_activity_layout_auto_clear_cache_tv)
    TextView clearCacheDataTv;

    @Bind(R.id.setting_activity_layout_auto_clear_temp_data_tv)
    TextView clearTempDataTv;

    @Bind(R.id.setting_activity_layout_parents_chat_notification_tv)
    TextView parentsChatNotiTv;

    @Bind(R.id.setting_activity_layout_teacher_chat_notification_tv)
    TextView teacherChatNotiTv;

    @Bind(R.id.setting_activity_layout_parents_set_goods_notification_tv)
    TextView parentsSetGoodsTv;

    @Bind(R.id.setting_activity_layout_parents_focus_notification_tv)
    TextView parentsFocusNotiTv;

    @Bind(R.id.setting_activity_layout_check_update_tv)
    TextView checkUpdateTv;

    @Bind(R.id.setting_activity_layout_curr_version_tv)
    TextView currVersionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity_layout);
        ButterKnife.bind(this);
        titleTv.setText("设置");
        titleTv.setTextColor(Color.WHITE);
        rightTitleTv.setVisibility(View.GONE);
        //mWrapTitleRel.setBackgroundColor(Color.WHITE);

        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            currVersionTv.setText("当前版本号:" + versionName);
        } catch (Exception e) {
        }

    }


    @OnClick({R.id.baby_document_activity_back_iv,R.id.setting_activity_logout_btn,
            R.id.setting_activity_layout_download_pic_tv,
            R.id.setting_activity_layout_download_video_tv,
            R.id.setting_activity_layout_auto_clear_cache_tv,
            R.id.setting_activity_layout_auto_clear_temp_data_tv,
            R.id.setting_activity_layout_parents_chat_notification_tv,
            R.id.setting_activity_layout_teacher_chat_notification_tv,
            R.id.setting_activity_layout_parents_set_goods_notification_tv,
            R.id.setting_activity_layout_parents_focus_notification_tv,
            R.id.setting_activity_layout_check_update_tv,
            R.id.setting_activity_layout_upload_data_tv})

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == loginOutBtn) {
            UserInfo.isLogined = false;
            PreferenceUtil.setStringKey(SettingActivity.this,"jgkj_user_name","");
            PreferenceUtil.setStringKey(SettingActivity.this,"jgkjpassword","");
            finish();
        } else {

            TextView tv = (TextView)v;
            Object tagObj = tv.getTag();
            if (tagObj == null) {

                tv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.my_switch_normal,0);
                tv.setTag("normal");
            } else {
                ToastUtil.showToast(this,"清除缓存成功", Toast.LENGTH_SHORT);
                tv.setTag(null);
                tv.setCompoundDrawablesWithIntrinsicBounds(0,0,R.mipmap.my_switch_clicked,0);
            }
        }
    }
}
