package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/2.
 */
public class MakeClassActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MakeClassActivity";

    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.make_class_activity_add_teacher_rel)
    RelativeLayout addTeacherRel;

    private String teacherIdsDataStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_class_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rightTv.setText("提交");
        rightTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        titleTv.setText("建立班级");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.make_class_activity_add_teacher_rel})
    @Override
    public void onClick(View v) {
       if (v == backIv) {
            finish();
        } else if (v == addTeacherRel) {
           startActivityForResult(new Intent(this, MakeClassAddPersonActivity.class), 1);
       }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            teacherIdsDataStr = data.getStringExtra("teacher_ids_data");
            if (TextUtils.isEmpty(teacherIdsDataStr)) {
                teacherIdsDataStr = "";
            }
            LogUtil.d(TAG,teacherIdsDataStr);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
