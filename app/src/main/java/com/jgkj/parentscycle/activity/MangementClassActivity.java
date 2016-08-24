package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.MangementClassExpanLvAdapter;
import com.jgkj.parentscycle.adapter.TeacherInfoAdapter;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.bean.MangementClassChildItem;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.ClassedAndTeachersPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/4.
 */
public class MangementClassActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.mangement_class_activity_expand_listview)
    ExpandableListView mExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mangement_class_activity);
        ButterKnife.bind(this);
        initViews();
        requestClassListBySchoolId();
    }

    private void initViews() {
        titleBg.setBackgroundColor(Color.WHITE);
        titleTv.setTextColor(Color.BLACK);
        titleTv.setText("班级管理");
        rightTv.setTextColor(Color.BLACK);
        rightTv.setText("提交");
        mExpandableListView.setGroupIndicator(null);
        mExpandableListView.setChildDivider(new BitmapDrawable());


    }

    private void initListView(List<ClassesAndTeachersListItemInfo> dataList) {
        int count = dataList.size();
        ArrayList groupData = new ArrayList<String>();
        HashMap <String,Integer> nameIntMap = new HashMap <String,Integer>();
        HashMap<Integer, List<ClassesAndTeachersListItemInfo>> childDataString = new HashMap<Integer, List<ClassesAndTeachersListItemInfo>>();
        for (int i = 0 ; i < count ; i++) {
            ClassesAndTeachersListItemInfo catli = dataList.get(i);
            String className = catli.getClassname();
            if (!groupData.contains(className)) {
                groupData.add(className);
                List<ClassesAndTeachersListItemInfo> tempDataList = new ArrayList<ClassesAndTeachersListItemInfo> ();
                tempDataList.add(catli);
                childDataString.put(groupData.indexOf(className),tempDataList);
            } else {
                childDataString.get(groupData.indexOf(className)).add(catli);
            }
        }

        MangementClassExpanLvAdapter mcea = new MangementClassExpanLvAdapter(this,groupData,childDataString);
        mExpandableListView.setAdapter(mcea);
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }


    //按学校ID 查询班级列表 （发布选择班级展示）
    private void requestClassListBySchoolId() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1");
        ClassedAndTeachersPaser lp = new ClassedAndTeachersPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.SEARCH_CLASS_LIST_BY_SCHOOL_ID, requestData, lp);
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ClassedAndTeachersListInfo) {
            if (nbs.isSuccess()) {
                ClassedAndTeachersListInfo tii = (ClassedAndTeachersListInfo)nbs.obj;
                initListView(tii.getDataList());
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
