package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.CreateClassInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.MakeClassPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/2.
 */
public class MakeClassActivity extends BaseActivity implements NetListener,View.OnClickListener{
    private static final String TAG = "MakeClassActivity";

    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView submitTv;

    @Bind(R.id.make_class_activity_add_teacher_rel)
    RelativeLayout addTeacherRel;

    @Bind(R.id.make_class_activity_class_name_et)
    EditText classNameEt;

    @Bind(R.id.make_class_activity_class_master_name_et)
    EditText classMasterEt;


    private String teacherIdsDataStr;
    private String classNameStr;
    private String classMasterStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_class_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        submitTv.setText("提交");
        submitTv.setTextColor(this.getResources().getColor(R.color.text_gray));
        titleTv.setText("建立班级");
        titleTv.setTextColor(Color.BLACK);
        titleBg.setBackgroundColor(Color.WHITE);
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.make_class_activity_add_teacher_rel,R.id.baby_document_right_title_tv})
    @Override
    public void onClick(View v) {
       if (v == backIv) {
            finish();
        } else if (v == addTeacherRel) {
           startActivityForResult(new Intent(this, MakeClassAddPersonActivity.class), 1);
       } else if (v == submitTv) {
           classNameStr = classNameEt.getText().toString();
           classMasterStr = classMasterEt.getText().toString();
           if (TextUtils.isEmpty(classNameStr)) {
               ToastUtil.showToast(this,"请输入班级名称",Toast.LENGTH_SHORT);
               return;
           }

           if (TextUtils.isEmpty(classMasterStr)) {
               ToastUtil.showToast(this,"请输入班主任名字",Toast.LENGTH_SHORT);
               return;
           }

           boolean hadShow = showProgressDialog();
           if (!hadShow) {
               return;
           }

           requestCreateClass();
       }
    }


    //建立班级
    private void requestCreateClass() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", "1"); //暂时传1
        requestData.put("classname", classNameStr);
        requestData.put("classadviser", classMasterStr);
        ArrayList<String> ids = new ArrayList<String>();
        requestData.put("teacherid", teacherIdsDataStr);
        MakeClassPaser lp = new MakeClassPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.CREATE_CLASS, requestData, lp);
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

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof CreateClassInfo) {
            CreateClassInfo ccI = (CreateClassInfo)nbs.obj;
            if (nbs.isSuccess()) {
                finish();
            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
