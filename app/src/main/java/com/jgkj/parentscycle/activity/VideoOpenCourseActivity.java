package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.VideoOpenCourseActivityAdapter;
import com.jgkj.parentscycle.bean.HallMainChannelLvInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/21.
 */
public class VideoOpenCourseActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.hall_main_channel_fragment_layout_Lv)
    ListView mListView;

    @Bind(R.id.baby_document_activity_title)
    TextView centerTitleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;


    VideoOpenCourseActivityAdapter mHallMainChannelAdapter;

    List<HallMainChannelLvInfo> classInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_open_course_activity);
        ButterKnife.bind(this);

        classInfoList = new ArrayList<HallMainChannelLvInfo>();
        initTestData();

        centerTitleTv.setText("可视化设备");
        rightTitleTv.setText("添加设备");
        mHallMainChannelAdapter = new VideoOpenCourseActivityAdapter(this, classInfoList);
        mListView.setAdapter(mHallMainChannelAdapter);
    }

    private void initTestData() {
        for (int i = 0 ; i <20 ; i++) {
            if (i % 2 == 0) {
                HallMainChannelLvInfo tempData = new HallMainChannelLvInfo();
                tempData.setClassName("柠檬班");
                tempData.setOnLineStatus("在线");
                classInfoList.add(tempData);
            } else {
                HallMainChannelLvInfo tempData = new HallMainChannelLvInfo();
                tempData.setClassName("彩虹班");
                tempData.setOnLineStatus("下线");
                classInfoList.add(tempData);
            }
        }
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
