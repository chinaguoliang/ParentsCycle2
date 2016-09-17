package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.jgkj.parentscycle.adapter.ShowCourseListAdapter;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.bean.PublishCourseInfo;
import com.jgkj.parentscycle.bean.ShowCourseListInfo;
import com.jgkj.parentscycle.bean.ShowCourseListInfoItem;
import com.jgkj.parentscycle.bean.ShowFoodListInfo;
import com.jgkj.parentscycle.bean.ShowFoodListInfoItem;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ClassedAndTeachersPaser;
import com.jgkj.parentscycle.json.ShowCourseListInfoPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
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
 * Created by chen on 16/9/18.
 */
public class ShowCourseListActivity extends BaseActivity implements View.OnClickListener,NetListener {
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;


    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.course_activity_filter_iv)
    ImageView courseFilterIv;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.corse_activity_week_1)
    TextView weekTv1;

    @Bind(R.id.corse_activity_week_2)
    TextView weekTv2;

    @Bind(R.id.corse_activity_week_3)
    TextView weekTv3;

    @Bind(R.id.corse_activity_week_4)
    TextView weekTv4;

    @Bind(R.id.corse_activity_week_5)
    TextView weekTv5;

    @Bind(R.id.corse_activity_week_6)
    TextView weekTv6;

    @Bind(R.id.corse_activity_week_7)
    TextView weekTv7;

    @Bind(R.id.show_course_list_activity_lv)
    ListView courseLv;

    private MakeClassAddPersonInfo makeClassAddPersonInfo = new MakeClassAddPersonInfo();
    Dialog mModifyClassDialog;

    private String weekNum = "";
    private String corseIds = "";
    private String corseNames = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_course_list_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        courseFilterIv.setVisibility(View.VISIBLE);
        rightTv.setVisibility(View.GONE);
        titleTv.setText("课程");
    }


    @OnClick({R.id.baby_document_activity_back_iv,R.id.course_activity_filter_iv,
            R.id.corse_activity_week_1,
            R.id.corse_activity_week_2,
            R.id.corse_activity_week_3,
            R.id.corse_activity_week_4,
            R.id.corse_activity_week_5,
            R.id.corse_activity_week_6,
            R.id.corse_activity_week_7
    })

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }else if (v == weekTv1) {
            resetWeekTvBg(v);
            weekNum = "1";
            requestCourseList();
        } else if (v == weekTv2) {
            resetWeekTvBg(v);
            weekNum = "2";
            requestCourseList();
        } else if (v == weekTv3) {
            resetWeekTvBg(v);
            weekNum = "3";
            requestCourseList();
        } else if (v == weekTv4) {
            resetWeekTvBg(v);
            weekNum = "4";
            requestCourseList();
        } else if (v == weekTv5) {
            resetWeekTvBg(v);
            weekNum = "5";
            requestCourseList();
        } else if (v == weekTv6) {
            resetWeekTvBg(v);
            weekNum = "6";
            requestCourseList();
        } else if (v == weekTv7) {
            resetWeekTvBg(v);
            weekNum = "7";
            requestCourseList();
        } else if (v == courseFilterIv) {
            requestClassListBySchoolId();
        }
    }

    private void resetWeekTvBg(View v) {
        weekTv1.setBackgroundColor(Color.WHITE);
        weekTv2.setBackgroundColor(Color.WHITE);
        weekTv3.setBackgroundColor(Color.WHITE);
        weekTv4.setBackgroundColor(Color.WHITE);
        weekTv5.setBackgroundColor(Color.WHITE);
        weekTv6.setBackgroundColor(Color.WHITE);
        weekTv7.setBackgroundColor(Color.WHITE);


        TextView tv = (TextView)v;
        int blueColor = this.getResources().getColor(R.color.main_blue_color);
        tv.setBackgroundColor(blueColor);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }


    //按学校ID 查询班级列表 （发布选择班级展示）
    private void requestClassListBySchoolId() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        ClassedAndTeachersPaser lp = new ClassedAndTeachersPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.SEARCH_CLASS_LIST_BY_SCHOOL_ID, requestData, lp);
    }

    //查询课程列表
    private void requestCourseList() {

        if (TextUtils.isEmpty(weekNum)) {
            ToastUtil.showToast(this,"请选择星期",Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(makeClassAddPersonInfo.getId())) {
            ToastUtil.showToast(this,"请选择班级",Toast.LENGTH_SHORT);
            return;
        }

        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","10");
        requestData.put("page","1");
        requestData.put("weeknum",weekNum);
        requestData.put("clssids", makeClassAddPersonInfo.getId());
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        ShowCourseListInfoPaser lp = new ShowCourseListInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.QUERY_COURSE_LIST, requestData, lp);
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
        }else if (nbs.obj instanceof ShowCourseListInfo) {
            if (nbs.isSuccess()) {
                ShowCourseListInfo tii = (ShowCourseListInfo)nbs.obj;
                List <ShowCourseListInfoItem> data = tii.getDataList();
                ShowCourseListAdapter scla = new ShowCourseListAdapter(this,data);
                courseLv.setAdapter(scla);
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
        showModifyClassDialog(sourceData);
    }

    private void showModifyClassDialog(ArrayList< MakeClassAddPersonInfo > sourceData) {
        mModifyClassDialog = new Dialog(this, R.style.DialogTheme);
        mModifyClassDialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        View contentView = LayoutInflater.from(this).inflate(R.layout.modify_class_dialog, null);
        Button confirm = (Button) contentView.findViewById(R.id.modify_class_dialog_confirm_btn);
        ListView classLv = (ListView) contentView.findViewById(R.id.modify_class_dialog_lv);
        confirm.setOnClickListener(changePhotoListener);

        final ModifyClassDialogLvAdapter mcda = new ModifyClassDialogLvAdapter(this,sourceData);
        classLv.setAdapter(mcda);
        classLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                makeClassAddPersonInfo = (MakeClassAddPersonInfo)mcda.getItem(position);
                mcda.setCurrentPosition(position);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                titleTv.setText(makeClassAddPersonInfo.getName() + "课程");
                mModifyClassDialog.dismiss();
                requestCourseList();
            }
        });

        mModifyClassDialog.setContentView(contentView);
        mModifyClassDialog.setCanceledOnTouchOutside(true);
        mModifyClassDialog.show();

        WindowManager.LayoutParams params = mModifyClassDialog.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = UtilTools.SCREEN_WIDTH;
        mModifyClassDialog.getWindow().setAttributes(params);
    }
}
