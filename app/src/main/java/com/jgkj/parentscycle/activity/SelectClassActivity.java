package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ModifyClassDialogLvAdapter;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ClassedAndTeachersPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/10/23.
 */
public class SelectClassActivity extends BaseActivity implements NetListener,View.OnClickListener{
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.modify_class_dialog_lv)
    ListView classDataLv;

    private MakeClassAddPersonInfo makeClassAddPersonInfo = new MakeClassAddPersonInfo();
    Dialog mModifyClassDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_class_activity);
        ButterKnife.bind(this);
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("选择班级");
        requestClassListBySchoolId();
    }

    //按学校ID 查询班级列表 （发布选择班级展示）
    private void requestClassListBySchoolId() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        ClassedAndTeachersPaser lp = new ClassedAndTeachersPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.SEARCH_CLASS_LIST_BY_SCHOOL_ID, requestData, lp);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

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

    private void initListView(final List<ClassesAndTeachersListItemInfo> dataList) {
        int count = dataList.size();
        HashMap <String,String> nameIntMap = new HashMap <String,String>();
        ArrayList< MakeClassAddPersonInfo > sourceData = new ArrayList<MakeClassAddPersonInfo>();
        for (int i = 0 ; i < count ; i++) {
            ClassesAndTeachersListItemInfo catli = dataList.get(i);
            String className = catli.getClassname();
            String classId = catli.getClassid();
            if (nameIntMap.get(classId) != null) {
                continue;
            }

            nameIntMap.put(classId,classId);
            MakeClassAddPersonInfo mcpi = new MakeClassAddPersonInfo();
            mcpi.setId(classId);
            mcpi.setName(className);
            sourceData.add(mcpi);
        }

        final ModifyClassDialogLvAdapter mcda = new ModifyClassDialogLvAdapter(this,sourceData);
        classDataLv.setAdapter(mcda);
        classDataLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makeClassAddPersonInfo = (MakeClassAddPersonInfo)mcda.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("class_data",makeClassAddPersonInfo);
                Intent intent = new Intent(view.getContext(),VideoTImeControllActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//        showModifyClassDialog(sourceData);
    }


    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }
}
