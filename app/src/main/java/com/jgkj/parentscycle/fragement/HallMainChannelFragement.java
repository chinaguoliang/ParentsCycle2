package com.jgkj.parentscycle.fragement;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.HallMainChannelAdapter;
import com.jgkj.parentscycle.bean.HallMainChannelLvInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/7/9.
 */
public class HallMainChannelFragement  extends Fragment {
    @Bind(R.id.hall_main_channel_fragment_layout_Lv)
    ListView mListView;

    @Bind(R.id.baby_document_activity_title)
    TextView centerTitleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    HallMainChannelAdapter mHallMainChannelAdapter;

    List<HallMainChannelLvInfo> classInfoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_main_channel_fragment_layout, container,
                false);
        ButterKnife.bind(this, view);

        classInfoList = new ArrayList<HallMainChannelLvInfo>();
        initTestData();

        centerTitleTv.setText("可视化设备");
        rightTitleTv.setText("添加设备");
        mHallMainChannelAdapter = new HallMainChannelAdapter(view.getContext(), classInfoList);
        mListView.setAdapter(mHallMainChannelAdapter);
        backIv.setVisibility(View.GONE);

        return view;
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
