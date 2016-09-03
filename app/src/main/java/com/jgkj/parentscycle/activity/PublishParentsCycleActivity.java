package com.jgkj.parentscycle.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/9/1.
 */
public class PublishParentsCycleActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView sendTv;

    @Bind(R.id.publish_parents_cycle_activity_publish_content_tv)
    EditText publishContentEt;

    @Bind(R.id.publish_parents_cycle_activity_publish_img_ll)
    LinearLayout publishImgLl;

    @Bind(R.id.publish_parents_cycle_activity_add_img_tv)
    TextView addImgTv;

    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_parents_cycle_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        titleTv.setText("父母圈");
        sendTv.setText("发送");
        ViewTreeObserver companyTreeObserver = addImgTv.getViewTreeObserver();
        companyTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                addImgTv.getViewTreeObserver().removeOnPreDrawListener(this);
                publishImgLl.setTag(addImgTv.getWidth());
                return true;
            }
        });
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.baby_document_right_title_tv,R.id.publish_parents_cycle_activity_add_img_tv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == sendTv) {

        } else if (v == addImgTv) {
            showChangePhotoDialog();
        }
    }


    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {
        ImageView iv = new ImageView(this);
        int width = Integer.parseInt(publishImgLl.getTag().toString());
        iv.setImageBitmap(bitmap);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width,width);
        iv.setPadding(10,10,10,10);
        iv.setLayoutParams(params);
        publishImgLl.addView(iv,0);
    }


}
