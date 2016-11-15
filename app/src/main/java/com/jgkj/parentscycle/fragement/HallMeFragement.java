package com.jgkj.parentscycle.fragement;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.activity.AccountInfoActivity;
import com.jgkj.parentscycle.activity.LoginActivity;
import com.jgkj.parentscycle.activity.ParentsCycleActivity;
import com.jgkj.parentscycle.activity.PerfectInformationActivity;
import com.jgkj.parentscycle.activity.SchoolInfoActivity;
import com.jgkj.parentscycle.activity.SettingActivity;
import com.jgkj.parentscycle.activity.TestNetActivity;
import com.jgkj.parentscycle.adapter.HallMineAdapter;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.AsyncImageUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;
import com.jgkj.parentscycle.widget.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/9.
 */
public class HallMeFragement extends Fragment implements View.OnClickListener{

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    TextView  userNameTv;
    TextView  phoneNumTv;
    ImageView userIconIv;

    private ListViewForScrollView mMyItemContentLv;
    private ImageView mUserIconIv;
    private View phoneNumberLl;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_mine_fragment_layout, container,
                false);

        ButterKnife.bind(this, view);


        backIv.setVisibility(View.GONE);
        titleTv.setText("个人中心");
        rightTitleTv.setText("    ");
        rightTitleTv.setPadding(30,10,30,10);

        mMyItemContentLv = (ListViewForScrollView)view.findViewById(R.id.hall_mine_fragment_lv);
        View headerView = inflater.inflate(R.layout.hall_mine_fragment_lv_header_layout,null,false);
        mMyItemContentLv.addHeaderView(headerView, null, false);

        userNameTv = (TextView)headerView.findViewById(R.id.hall_mine_fragment_lv_header_layout_user_name_tv);
        phoneNumTv = (TextView)headerView.findViewById(R.id.hall_mine_fragment_lv_header_layout_phone_number_tv);
        userIconIv = (ImageView)headerView.findViewById(R.id.hall_mine_fragment_lv_header_user_icon_iv);

        phoneNumberLl = headerView.findViewById(R.id.hall_mine_fragment_lv_header_layout_phone_number_ll);

        HallMineAdapter hallMineAdapter = new HallMineAdapter(this.getContext(),getContentData());

        mMyItemContentLv.setAdapter(hallMineAdapter);
        mUserIconIv = (ImageView)headerView.findViewById(R.id.hall_mine_fragment_lv_header_user_icon_iv);

        mUserIconIv.setOnClickListener(this);
        userNameTv.setOnClickListener(this);

        mMyItemContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!UserInfo.isLogined) {
                    ToastUtil.showToast(mMyItemContentLv.getContext(),"请先登录", Toast.LENGTH_SHORT);
                    return;
                }
                switch (position){
                    case 1:
                        startActivity(new Intent(mUserIconIv.getContext(), AccountInfoActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(mUserIconIv.getContext(), SchoolInfoActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(mUserIconIv.getContext(),ParentsCycleActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(mUserIconIv.getContext(),SettingActivity.class));
                        break;
                    case 5:
                        UtilTools.toChatModule(view.getContext());
                        break;
                }
            }
        });




        refreshUI();
        return view;
    }

    public void refreshUI() {
        if (UserInfo.isLogined) {
            if (userNameTv != null) {
                if (UserInfo.loginInfo.getRole() != null)
                //userNameTv.setText(UserInfo.loginInfo.getRole().getName());
                phoneNumberLl.setVisibility(View.VISIBLE);
                if (UserInfo.loginInfo.getInfo() != null) {
                    userNameTv.setText(UserInfo.loginInfo.getInfo().getNickname());
                }

                if (UserInfo.loginInfo.getRole() != null)
                phoneNumTv.setText(UserInfo.loginInfo.getRole().getPhone());
            }

            if (mUserIconIv != null) {
                if (UserInfo.loginInfo.getInfo() != null)
                AsyncImageUtil.asyncLoadImage(mUserIconIv,
                        UserInfo.loginInfo.getInfo().getHeadportrait(),
                        R.mipmap.user_default_icon, true, false);
            }
        } else {
            if (mUserIconIv != null)
            AsyncImageUtil.asyncLoadImage(mUserIconIv, "", R.mipmap.user_default_icon, true, false);

            if (userNameTv != null)
            userNameTv.setText("未登录");

            if (phoneNumTv != null)
                phoneNumberLl.setVisibility(View.GONE);
        }
    }

    private List<String> getContentData() {
        ArrayList <String> data = new ArrayList<String>();
        data.add("帐号信息");
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

    @OnClick({R.id.baby_document_right_title_tv})
    @Override
    public void onClick(View v) {
        if (v == rightTitleTv) {
            startActivity(new Intent(v.getContext(),TestNetActivity.class));
        } else if (v == mUserIconIv) {
            startActivity(new Intent(v.getContext(),LoginActivity.class));
        } else if (v == userNameTv) {
            startActivity(new Intent(v.getContext(),LoginActivity.class));
        }
    }
}
