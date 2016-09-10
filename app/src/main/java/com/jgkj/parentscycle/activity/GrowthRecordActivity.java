package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.GrowthRecordActivityLvAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/21.
 */
public class GrowthRecordActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.growth_record_activity_lv)
    ListView mContentLv;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.growth_record_activity_send_record_iv)
    ImageView sendRecordIv;

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.growth_record_activity);
        ButterKnife.bind(this);
        mContentLv.setAdapter(new GrowthRecordActivityLvAdapter(this,initTestData()));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("成长记录");
    }

    private ArrayList<String> initTestData() {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("今天");
        temp.add("昨天");
        temp.add("明天");
        temp.add("后天");
        temp.add("今天");
        return temp;
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.growth_record_activity_send_record_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == sendRecordIv) {
            startActivity(new Intent(GrowthRecordActivity.this,PublishGrowthRecordActivity.class));
        }
    }
}
