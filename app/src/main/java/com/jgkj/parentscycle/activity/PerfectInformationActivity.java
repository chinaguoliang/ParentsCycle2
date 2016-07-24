package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AccountInfoAdapter;
import com.jgkj.parentscycle.adapter.PerfectInformationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/24.
 */
public class PerfectInformationActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.perfect_information_activity_lv)
    ListView mListView;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfect_information_activity);
        ButterKnife.bind(this);
        mListView.setAdapter(new PerfectInformationAdapter(this, getContentData()));
        titleTv.setText("完善资料");
        rightTitleTv.setVisibility(View.GONE);
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("头像_ ");
        data.add("昵称_奋斗的小鸟");
        data.add("帐号_36222@163.com");
        data.add("地区_北京");
        data.add("性别_女");
        data.add("家庭角色_妈妈");
        data.add("宝宝名字_花泽类");
        data.add("宝宝性别_男");
        data.add("宝宝年龄_3岁");
        return data;
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }
}
