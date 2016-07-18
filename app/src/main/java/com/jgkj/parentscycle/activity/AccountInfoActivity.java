package com.jgkj.parentscycle.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AccountInfoAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/7/18.
 */
public class AccountInfoActivity extends BaseActivity {
    @Bind(R.id.account_info_activity_lv)
    ListView mContentLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info_activity_layout);
        ButterKnife.bind(this);
        mContentLv.setAdapter(new AccountInfoAdapter(this,getContentData()));
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("昵称_老师");
        data.add("姓名_小李");
        data.add("性别_女");
        data.add("民族_汉");
        data.add("出生日期_1985");
        data.add("手机号_13673668068");
        data.add("账户安全_>");
        data.add("捆绑微信_已捆绑");
        data.add("捆绑QQ_>");

        return data;
    }
}
