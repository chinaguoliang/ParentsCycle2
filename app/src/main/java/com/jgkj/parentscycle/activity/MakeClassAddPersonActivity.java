package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.MakeClassAddPersonAdapter;
import com.jgkj.parentscycle.adapter.SelectTeacherListAdapter;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.bean.TeachersListInfo;
import com.jgkj.parentscycle.bean.TeachersListItemInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.TeacherListPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ActivityUtils;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/3.
 */
public class MakeClassAddPersonActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.make_class_add_person_activity_content_lv)
    ListView mContentLv;

    @Bind(R.id.make_class_add_person_activity_submit_btn)
    Button confirmBtn;

    MakeClassAddPersonAdapter makeClassAddPersonAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_class_add_person_activity);
        ButterKnife.bind(this);
        initViews();
        requestTeachersList();
    }

    private void requestTeachersList() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        TeacherListPaser lp = new TeacherListPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.TEACHERS_LIST, requestData, lp);
    }

    private void initViews() {
        rightTv.setVisibility(View.GONE);
        titleTv.setText("添加老师");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);

    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.make_class_add_person_activity_submit_btn})

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            setIdsData();
            finish();
        } else if (v == confirmBtn) {
            setIdsData();
            finish();
        }
    }


    private void setIdsData(){
        Intent intent = new Intent();
        String idsData = makeClassAddPersonAdapter.getIdsData();
        intent.putExtra("teacher_ids_data",idsData);
        setResult(1, intent);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof TeachersListInfo) {
            if (nbs.isSuccess()) {
                TeachersListInfo tii = (TeachersListInfo)nbs.obj;
                makeClassAddPersonAdapter = new MakeClassAddPersonAdapter(this,tii.getData());
                mContentLv.setAdapter(makeClassAddPersonAdapter);
                mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        makeClassAddPersonAdapter.setSelectPosition(position);
                    }
                });
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
