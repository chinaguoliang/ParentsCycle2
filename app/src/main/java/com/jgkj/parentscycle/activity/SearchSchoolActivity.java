package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AddSchoolExpanLvAdapter;
import com.jgkj.parentscycle.adapter.SchoolDetailListviewAdapter;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/10/10.
 */
public class SearchSchoolActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.search_school_activity_cancel_btn)
    Button cancelBtn;

    @Bind(R.id.search_school_activity_ep_lv)
    ExpandableListView expandLv;

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_school_activity);
        ButterKnife.bind(this);
        expandLv.setGroupIndicator(null);
        expandLv.setChildDivider(new BitmapDrawable());
        initTestData();
    }

    private void initTestData(){
        List<String> groupTitle = new ArrayList<String>();
        groupTitle.add("飞翔幼儿园");
        groupTitle.add("育才幼儿园");
        groupTitle.add("精英幼儿园");

        final HashMap<Integer, List<ClassesAndTeachersListItemInfo>> childDataString = new  HashMap<Integer, List<ClassesAndTeachersListItemInfo>>();

        List<ClassesAndTeachersListItemInfo> tempList = new ArrayList<ClassesAndTeachersListItemInfo>();
        ClassesAndTeachersListItemInfo cali1 = new ClassesAndTeachersListItemInfo();
        cali1.setClassname("大一班");
        cali1.setClassadviser("大一班");
        ClassesAndTeachersListItemInfo cali2 = new ClassesAndTeachersListItemInfo();
        cali2.setClassname("大二班");
        cali2.setClassadviser("大二班");
        ClassesAndTeachersListItemInfo cali3 = new ClassesAndTeachersListItemInfo();
        cali3.setClassname("大三班");
        cali3.setClassadviser("大三班");

        tempList.add(cali1);
        tempList.add(cali2);
        tempList.add(cali3);

        childDataString.put(0,tempList);
        childDataString.put(1,tempList);
        childDataString.put(2,tempList);


        AddSchoolExpanLvAdapter sdla = new AddSchoolExpanLvAdapter(this,groupTitle,childDataString);
        expandLv.setAdapter(sdla);

        expandLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ClassesAndTeachersListItemInfo cali = childDataString.get(groupPosition).get(childPosition);
                return false;
            }
        });
    }


    @OnClick({R.id.search_school_activity_cancel_btn})
    @Override
    public void onClick(View v) {
        if (v == cancelBtn) {
            finish();
        }
    }
}
