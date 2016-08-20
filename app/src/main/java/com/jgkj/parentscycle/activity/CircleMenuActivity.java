package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/16.
 */
public class CircleMenuActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.circle_menu_activity_close_tv)
    TextView closeTv;

    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.circle_menu_activity_chat_tv)
    TextView chatTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.circle_menu_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleTv.setText("选择发布类别");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);
        backIv.setVisibility(View.GONE);
        rightTv.setText("我的发布");
        rightTv.setTextColor(Color.BLACK);
    }

    @OnClick({R.id.circle_menu_activity_close_tv,R.id.circle_menu_activity_chat_tv})
    @Override
    public void onClick(View v) {
        if (v == closeTv) {
            finish();
        } else if (v == chatTv) {
            UtilTools.toChatModule(CircleMenuActivity.this);
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap) {

    }
}
