package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AddSchoolExpanLvAdapter;
import com.jgkj.parentscycle.adapter.ModifyPermissionDialogLvAdapter;
import com.jgkj.parentscycle.adapter.SchoolDetailListviewAdapter;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.utils.UtilTools;

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

    Dialog addSchoolDialog;

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
                showWhetherAddSchoolDialog();
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


    private void showWhetherAddSchoolDialog() {
        addSchoolDialog = new Dialog(this, R.style.DialogTheme);
        addSchoolDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.add_school_dialog, null);
        Button cancel = (Button) contentView.findViewById(R.id.add_school_dialog_no_btn);
        Button yes = (Button) contentView.findViewById(R.id.add_school_dialog_yes_btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchoolDialog.dismiss();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchoolDialog.dismiss();
                showTipDialog();
            }
        });


        addSchoolDialog.setContentView(contentView);
        addSchoolDialog.setCanceledOnTouchOutside(true);
        addSchoolDialog.show();

        WindowManager.LayoutParams params = addSchoolDialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = UtilTools.SCREEN_WIDTH - UtilTools.SCREEN_WIDTH / 5;
        addSchoolDialog.getWindow().setAttributes(params);
    }


    private void showTipDialog() {
        addSchoolDialog = new Dialog(this, R.style.DialogTheme);
        addSchoolDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.add_school_verify_dialog, null);
        Button yes = (Button) contentView.findViewById(R.id.add_school_verify_dialog_yes_btn);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSchoolDialog.dismiss();
                finish();
            }
        });


        addSchoolDialog.setContentView(contentView);
        addSchoolDialog.setCanceledOnTouchOutside(true);
        addSchoolDialog.show();

        WindowManager.LayoutParams params = addSchoolDialog.getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = UtilTools.SCREEN_WIDTH - UtilTools.SCREEN_WIDTH / 5;
        addSchoolDialog.getWindow().setAttributes(params);
    }
}
