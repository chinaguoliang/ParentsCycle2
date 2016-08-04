package com.jgkj.parentscycle.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.MangementClassExpanLvAdapter;
import com.jgkj.parentscycle.bean.MangementClassChildItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/4.
 */
public class MangementClassActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.mangement_class_activity_expand_listview)
    ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mangement_class_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleBg.setBackgroundColor(Color.WHITE);
        titleTv.setTextColor(Color.BLACK);
        titleTv.setText("班级管理");
        rightTv.setTextColor(Color.BLACK);
        rightTv.setText("提交");
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setChildDivider(new BitmapDrawable());

        ArrayList groupData = new ArrayList<String>();
        groupData.add("橘子班");
        groupData.add("橙子班");
        groupData.add("芒果班");


        List<MangementClassChildItem> childItems = new ArrayList<MangementClassChildItem>();
        MangementClassChildItem childData1 = new MangementClassChildItem("苹果", R.mipmap.ic_launcher);
        childItems.add(childData1);
        MangementClassChildItem childData2 = new MangementClassChildItem("樱桃", R.mipmap.ic_launcher);
        childItems.add(childData2);
        MangementClassChildItem childData3 = new MangementClassChildItem("草莓", R.mipmap.ic_launcher);
        childItems.add(childData3);

        List<MangementClassChildItem> childItems2 = new ArrayList<MangementClassChildItem>();
        MangementClassChildItem childData4 = new MangementClassChildItem("香蕉", R.mipmap.ic_launcher);
        childItems2.add(childData4);
        MangementClassChildItem childData5 = new MangementClassChildItem("芒果", R.mipmap.ic_launcher);
        childItems2.add(childData5);
        MangementClassChildItem childData6 = new MangementClassChildItem("橘子", R.mipmap.ic_launcher);
        childItems2.add(childData6);
        MangementClassChildItem childData7 = new MangementClassChildItem("梨子", R.mipmap.ic_launcher);
        childItems2.add(childData7);

        List<MangementClassChildItem> childItems3 = new ArrayList<MangementClassChildItem>();
        MangementClassChildItem childData8 = new MangementClassChildItem("葡萄", R.mipmap.ic_launcher);
        childItems3.add(childData8);
        MangementClassChildItem childData9 = new MangementClassChildItem("西瓜", R.mipmap.ic_launcher);
        childItems3.add(childData9);

        HashMap<Integer, List<MangementClassChildItem>> childData = new HashMap<Integer, List<MangementClassChildItem>>();
        childData.put(0, childItems);
        childData.put(1, childItems2);
        childData.put(2, childItems3);

        MangementClassExpanLvAdapter mcea = new MangementClassExpanLvAdapter(this,groupData,childData);
        mExpandableListView.setAdapter(mcea);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }
}
