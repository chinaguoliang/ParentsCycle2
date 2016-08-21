package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/21.
 */
public class OnineForumActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.online_forum_activity);
        ButterKnife.bind(this);
        rightTitleTv.setText("申请");
        titleTv.setText("直播培训");
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }


    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }
}
