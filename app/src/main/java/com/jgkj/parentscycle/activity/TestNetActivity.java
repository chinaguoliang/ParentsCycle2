package com.jgkj.parentscycle.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/15.
 */
public class TestNetActivity extends BaseActivity implements View.OnClickListener,NetListener {
    @Bind(R.id.test_net_activity_btn)
    Button testNetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_net_activity);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.test_net_activity_btn})
    @Override
    public void onClick(View v) {
        if (v == testNetBtn) {

        }
    }

    @Override
    public void requestResponse(Object obj) {

    }
}
