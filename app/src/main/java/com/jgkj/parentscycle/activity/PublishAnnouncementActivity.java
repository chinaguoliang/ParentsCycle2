package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/20.
 */
public class PublishAnnouncementActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.publish_announcement_activity_add_pic_tv)
    TextView addPicTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_announcement_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("公告");
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.publish_announcement_activity_add_pic_tv})
    @Override
    public void onClick(View v) {
        if (backIv == v) {
            finish();
        } else if (addPicTv == v) {
            showChangePhotoDialog();
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap) {
        addPicTv.setText("");
        addPicTv.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }
}
