package com.jgkj.parentscycle.fragement;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.activity.LoginActivity;
import com.jgkj.parentscycle.adapter.HallMineAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16/7/9.
 */
public class HallMeFragement extends Fragment implements View.OnClickListener{
    private ListView mMyItemContentLv;
    private ImageView mUserIconIv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_mine_fragment_layout, container,
                false);
        mMyItemContentLv = (ListView)view.findViewById(R.id.hall_mine_fragment_lv);
        View headerView = inflater.inflate(R.layout.hall_mine_fragment_lv_header_layout, container,
                false);
        HallMineAdapter hallMineAdapter = new HallMineAdapter(container.getContext(),getContentData());
        mMyItemContentLv.setAdapter(hallMineAdapter);
        mMyItemContentLv.addHeaderView(headerView);
        mUserIconIv = (ImageView)headerView.findViewById(R.id.hall_mine_fragment_lv_header_user_icon_iv);

        mUserIconIv.setOnClickListener(this);

        return view;
    }

    private List<String> getContentData() {
        ArrayList <String> data = new ArrayList<String>();
        data.add("帐号信息");
        data.add("完善资料");
        data.add("学校信息");
        data.add("父母圈");
        data.add("设置");
        data.add("家长咨询");
        return data;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        if (v == mUserIconIv) {
            startActivity(new Intent(v.getContext(), LoginActivity.class));
        }
    }
}
