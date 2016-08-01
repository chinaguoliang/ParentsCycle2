package com.jgkj.parentscycle.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/2.
 */
public class AccountSafeResetPassActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_safe_reset_pass_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rightTv.setText("提交");
        rightTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        titleTv.setText("修改密码");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
       if (v == backIv) {
            finish();
        }
    }
}
