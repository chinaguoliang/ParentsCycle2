package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.SchoolDetailListviewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/24.
 */
public class SchoolDetailActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.school_detail_activity_lv)
    ListView mListView;

    TextView focusTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_detail_activity);
        ButterKnife.bind(this);

        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.white));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("详情");
        titleTv.setTextColor(this.getResources().getColor(R.color.black));

        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.school_detail_activity_listview_headerview,null);
        focusTv = (TextView)headerView.findViewById(R.id.school_detail_activity_listview_header_view_focus_tv);
        focusTv.setOnClickListener(this);
        mListView.addHeaderView(headerView);
        SchoolDetailListviewAdapter sla = new  SchoolDetailListviewAdapter(this,getTestData());
        mListView.setAdapter(sla);
    }

    private List<String> getTestData() {
        ArrayList <String> data = new ArrayList<String>();
        for (int i = 0 ; i < 30 ; i++) {
            if (i % 2 == 0) {
                data.add("李雷");
            } else {
                data.add("韩梅梅");
            }
        }
        return data;
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == focusTv) {
            if (focusTv.getTag() == null) {
                focusTv.setTag("setFocus");

                focusTv.setText("已关注");
                focusTv.setBackgroundResource(R.mipmap.had_focus);
                focusTv.setTextColor(this.getResources().getColor(R.color.white));

            } else {
                focusTv.setTag(null);
                focusTv.setText("关注");
                focusTv.setBackgroundResource(R.mipmap.not_focus);
                focusTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));
            }
        } else if (v == backIv) {
            finish();
        }
    }
}
