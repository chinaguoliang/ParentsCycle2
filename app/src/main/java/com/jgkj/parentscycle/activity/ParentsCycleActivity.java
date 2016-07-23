package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ParentsCycleAdapter;
import com.jgkj.parentscycle.customview.HeaderGridView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/24.
 */
public class ParentsCycleActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.parents_cycle_activity_lv)
    HeaderGridView mContentLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parents_cycle_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.white));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("父母圈");
        titleTv.setTextColor(this.getResources().getColor(R.color.black));
        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.parents_cycle_activity_listview_header_view,null);
        mContentLv.addHeaderView(headerView);

        ArrayList<String> dataList = new ArrayList<String>();
        for(int i = 0 ; i < 30 ; i++) {
            if (i % 2 == 0) {
                dataList.add("小明");
            } else {
                dataList.add("小惠");
            }
        }

        ParentsCycleAdapter pca = new ParentsCycleAdapter(this,dataList);
        mContentLv.setAdapter(pca);
    }


    @OnClick({R.id.baby_document_activity_back_iv})

    @Override
    public void onClick(View v) {
        if (backIv == v) {
            finish();
        }
    }
}
