package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ParentsCycleAdapter;
import com.jgkj.parentscycle.bean.ParentsCycleInfo;
import com.jgkj.parentscycle.customview.HeaderGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/8/27.
 */
public class ParentsCyclePersonalPageActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.parents_cycle_personalpage_activity_lv)
    HeaderGridView mContentLv;

    ParentsCycleAdapter mParentsCycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parents_cycle_personalpage_activity);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.white));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("");
        titleTv.setTextColor(this.getResources().getColor(R.color.black));

        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.parents_cycle_personalpage_activity_listview_header_view,null);
        mContentLv.addHeaderView(headerView);

        ArrayList<ParentsCycleInfo> dataList = getTestData(0);
        mParentsCycleAdapter = new ParentsCycleAdapter(this,dataList);
        mContentLv.setAdapter(mParentsCycleAdapter);
        mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(view.getContext(),SchoolDetailActivity.class));
            }
        });
    }

    private ArrayList<ParentsCycleInfo> getTestData(int flag) {
        ArrayList<ParentsCycleInfo> dataList = new ArrayList<ParentsCycleInfo>();
        for(int i = 0 ; i < 30 ; i++) {
            if (i % 2 == 0) {
                ParentsCycleInfo pci = new ParentsCycleInfo();
                pci.setName("小明");
                pci.setTime("1小时前");
                pci.setContent("这个幼儿园很好");
                if (flag == 0) {
                    pci.setImgRes(R.mipmap.baby_school_inside);
                } else if (flag == 1) {
                    pci.setImgRes(R.mipmap.school2);
                } else if (flag == 2) {
                    pci.setImgRes(R.mipmap.school3);
                }
                dataList.add(pci);
            } else {
                ParentsCycleInfo pci = new ParentsCycleInfo();
                pci.setName("小惠");
                pci.setTime("2小时前");
                pci.setContent("市内最好的幼儿园");
                dataList.add(pci);
                if (flag == 0) {
                    pci.setImgRes(R.mipmap.baby_school_inside);
                } else if (flag == 1) {
                    pci.setImgRes(R.mipmap.school2);
                } else if (flag == 2) {
                    pci.setImgRes(R.mipmap.school3);
                }
            }
        }
        return dataList;
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }
}
