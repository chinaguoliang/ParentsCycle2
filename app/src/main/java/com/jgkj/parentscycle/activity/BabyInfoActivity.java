package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/30.
 */
public class BabyInfoActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.baby_info_activity_lv)
    ListViewForScrollView contentLv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_info_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleBg.setBackgroundColor(0x00000000);
        rightTv.setVisibility(View.GONE);
        titleTv.setText("宝宝详情");
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }

    private List<String> getContentData(TeacherInfoListInfo tii) {
        ArrayList<String> data = new ArrayList<String>();
        data.add("职务_" + tii.getAnalysis());
        data.add("权限_" + UserInfo.getTitleByPermission(tii.getPermissions()));
        data.add("昵称_" + tii.getNickname());
        data.add("姓名_" + tii.getTeachername());
        data.add("性别_" + UserInfo.getSexByServerData(tii.getTeachersex()));
        data.add("民族_" + tii.getNationality());
        data.add("出生日期_" + tii.getBirthdate());
        data.add("手机号_" + tii.getPhone());
        data.add("负责班级_" + tii.getClassid());
        data.add("更改职位与权限_");
        data.add("更改班级_");
        data.add("离开幼儿园_");
        return data;
    }
}
