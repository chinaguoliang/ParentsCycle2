package com.jgkj.parentscycle.activity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AccountInfoAdapter;
import com.jgkj.parentscycle.bean.PerfectInfoInfo;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.PerfectInfoPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.widget.ListViewForScrollView;
import com.jgkj.parentscycle.widget.SexSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/18.
 */
public class AccountInfoActivity extends BaseActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,NetListener ,SexSelectDialog.SexSlectDialogFinish {

    private static final String TAG = "AccountInfoActivity";
    @Bind(R.id.account_info_activity_lv)
    ListViewForScrollView mContentLv;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.account_info_activity_save_btn)
    Button saveBtn;

    AccountInfoAdapter mAccountInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info_activity_layout);
        ButterKnife.bind(this);
        mAccountInfoAdapter = new AccountInfoAdapter(this, getContentData());
        mContentLv.setAdapter(mAccountInfoAdapter);
        titleTv.setText("帐号信息");
        rightTitleTv.setVisibility(View.GONE);
        mWrapTitleRel.setBackgroundColor(Color.parseColor("#00000000"));
        mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {
                    showDateDialog();
                } else if (position == 6) {
                    //帐号信息
                    startActivity(new Intent(AccountInfoActivity.this,AccountSafeActivity.class));
                } else if (position == 2) {
                    SexSelectDialog.showSexSelectDialog(AccountInfoActivity.this,AccountInfoActivity.this);
                }


//                if (position == 0 || position == 1 || position == 3) {
//                    View contentEt = view.findViewById(R.id.hall_mine_fragment_lv_item_content_et);
//                    contentNameTv.setVisibility(View.GONE);
//                    contentEt.setVisibility(View.VISIBLE);
////                    holder.contentEt.setVisibility(View.VISIBLE);
////                    holder.conentNameTv.setVisibility(View.GONE);
//                }
            }
        });

        requestNet();
    }

    private List<String> getContentData() {
        ArrayList<String> data = new ArrayList<String>();
        data.add("昵称_ ");
        data.add("姓名_ ");
        data.add("性别_ ");
        data.add("民族_ ");
        data.add("出生日期_ ");
        data.add("手机号_ ");
        data.add("账户安全_0");
        data.add("捆绑微信_ ");
        data.add("捆绑QQ_0");

        return data;
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.account_info_activity_save_btn})
    @Override
    public void onClick(View v) {
       if (v == backIv) {
           finish();
       } else if (v == saveBtn) {
           requestSave();
       }
    }

    private void showDateDialog() {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        int year=d.get(Calendar.YEAR);
        int month=d.get(Calendar.MONTH);
        int day=d.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,this,year,month,day);
        dpd.getWindow().setBackgroundDrawable(new BitmapDrawable()); //设置为透明
        dpd.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        List<String> dataList = mAccountInfoAdapter.getList();
        String date = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
//        dataList.remove(4);
        dataList.set(4,"出生日期_" + date);
        mAccountInfoAdapter.getData().put(4,date);
        mAccountInfoAdapter.notifyDataSetChanged();
    }

    private void requestNet() {
        if (UserInfo.isLogined) {
            showProgressDialog();
            HashMap<String, String> requestData = new HashMap<String, String>();
            requestData.put("tmpinfoid", UserInfo.loginInfo.getRole().getId());
            requestData.put("schoolid", "1");  //暂时传1
            TeacherInfoLIstPaser lp = new TeacherInfoLIstPaser();
            NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_LIST, requestData, lp);
        }
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;

        if (nbs.obj instanceof PerfectInfoInfo) {
            if (nbs.isSuccess()) {
                finish();
            }

            ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
        } else {
            if (nbs.isSuccess()) {
                TeacherInfoListInfo tii = (TeacherInfoListInfo)nbs.obj;
                ArrayList<String> data = new ArrayList<String>();
                data.add("昵称_" + tii.getNickname());
                data.add("姓名_" + tii.getSchoolname());
                data.add("性别_ ");
                data.add("民族_ ");
                data.add("出生日期_ ");
                data.add("手机号_ " + tii.getPhone() );
                data.add("账户安全_ ");
                data.add("捆绑微信_ ");
                data.add("捆绑QQ_ ");
                mAccountInfoAdapter = new AccountInfoAdapter(this, data);
                mContentLv.setAdapter(mAccountInfoAdapter);
                LogUtil.d(TAG,"success");
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }


    public void requestSave() {
        String uploadKey = "";
        HashMap<String, String> requestData = new HashMap<String, String>();
        HashMap<Integer,String> data = mAccountInfoAdapter.getData();
        requestData.put("analysis","1");
        requestData.put("birthdate",data.get(4));
        requestData.put("classid","1,2,3");
        if (TextUtils.isEmpty(uploadKey)) {
            requestData.put("headportrait","");
        } else {
            requestData.put("headportrait",BgGlobal.IMG_SERVER_PRE_URL + uploadKey);
        }
        requestData.put("kbwx","1"); //1: 是  0：否
        requestData.put("kbqq","1");
        requestData.put("nationality","1");
        requestData.put("nickname",data.get(0));
        requestData.put("onthejob","1"); // 1:在职  0： 离职
        requestData.put("permissions","1");
        requestData.put("phone","");
        requestData.put("schoolname","");
        requestData.put("teacherid","1");
        requestData.put("teachername","");
        requestData.put("teachersex",data.get(2));
        requestData.put("tmpinfoid", UserInfo.loginInfo.getRole().getId());
        requestData.put("schoolid", "1");  //暂时传1
        PerfectInfoPaser lp = new PerfectInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHER_INFO_SAVE, requestData, lp);
    }


    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    @Override
    public void finishSlecct(int index) {
        List<String> dataList = mAccountInfoAdapter.getList();
        if (index == 1) {
            dataList.set(2,"性别_男");
        } else if (index == 0) {
            dataList.set(2,"性别_女");
        }

        mAccountInfoAdapter.getData().put(2,index + "");
        mAccountInfoAdapter.notifyDataSetChanged();
    }
}
