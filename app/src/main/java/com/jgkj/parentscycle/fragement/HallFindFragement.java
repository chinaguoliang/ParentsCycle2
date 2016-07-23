package com.jgkj.parentscycle.fragement;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/7/9.
 */
public class HallFindFragement extends Fragment {
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_find_fragment_layout, container,
                false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        backIv.setVisibility(View.GONE);
        titleTv.setText("发现");
        rightTitleTv.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

}
