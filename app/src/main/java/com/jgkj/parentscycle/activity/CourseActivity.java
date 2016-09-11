package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ModifyClassDialogLvAdapter;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.bean.MakeClassAddPersonInfo;
import com.jgkj.parentscycle.bean.PublishCourseInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ClassedAndTeachersPaser;
import com.jgkj.parentscycle.json.PublishCourseInfoPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/23.
 */
public class CourseActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.course_activity_morning_add_course_btn)
    Button addCourseMorningBtn;

    @Bind(R.id.course_activity_afternoon_add_course_btn)
    Button addCourseAfternootBtn;

    @Bind(R.id.course_activity_afternoon_course_ll)
    LinearLayout afternoonCourseLl;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.course_activity_morning_course_ll)
    LinearLayout morningCourseLl;

    @Bind(R.id.course_activity_filter_iv)
    ImageView courseFilterIv;

    @Bind(R.id.back_iv)
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

    @Bind(R.id.course_activity_publish_btn)
    Button publishBtn;

    private LayoutInflater mInflater;
    Dialog selectClassDialog;

    private MakeClassAddPersonInfo makeClassAddPersonInfo = new MakeClassAddPersonInfo();
    Dialog mModifyClassDialog;

    private String weekNum = "";
    private String corseIds = "";
    private String corseNames = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @OnClick({R.id.back_iv,R.id.course_activity_afternoon_add_course_btn,R.id.course_activity_morning_add_course_btn,R.id.course_activity_filter_iv,
            R.id.corse_activity_week_1,
            R.id.corse_activity_week_2,
            R.id.corse_activity_week_3,
            R.id.corse_activity_week_4,
            R.id.corse_activity_week_5,
            R.id.corse_activity_week_6,
            R.id.corse_activity_week_7,
            R.id.course_activity_publish_btn
    })

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == addCourseMorningBtn) {
            addCourseView(morningCourseLl);
            refreshCourseNumber();
        }
//        else if (v == courseFilterIv) {
//            showChangePhotoDialog();
//        }
        else if (v == weekTv1) {
            resetWeekTvBg(v);
            weekNum = "1";
        } else if (v == weekTv2) {
            resetWeekTvBg(v);
            weekNum = "2";
        } else if (v == weekTv3) {
            resetWeekTvBg(v);
            weekNum = "3";
        } else if (v == weekTv4) {
            resetWeekTvBg(v);
            weekNum = "4";
        } else if (v == weekTv5) {
            resetWeekTvBg(v);
            weekNum = "5";
        } else if (v == weekTv6) {
            resetWeekTvBg(v);
            weekNum = "6";
        } else if (v == weekTv7) {
            resetWeekTvBg(v);
            weekNum = "7";
        } else if (v == courseFilterIv) {
            requestClassListBySchoolId();
        } else if (v == publishBtn) {
            requestPublishCourse();
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

    private void addCourseView(final LinearLayout viewLl) {
        final LinearLayout courseItem = (LinearLayout)mInflater.inflate(R.layout.corse_activity_course_item,null);
        courseItem.setTag("courseItem");
        int childCount = courseItem.getChildCount();
        ImageView delIv = (ImageView) courseItem.findViewById(R.id.corse_activity_corse_item_del_iv);
        delIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewLl.removeView(courseItem);
            }
        });

        viewLl.addView(courseItem,childCount - 2);
    }

    private void refreshCourseNumber() {
        int courseNumber = 0;
        int mCount = morningCourseLl.getChildCount();
        for (int i = 0 ; i < mCount ; i++) {
            View obj = morningCourseLl.getChildAt(i);
            if (obj.getTag() != null) {
                courseNumber++;
                TextView tv = (TextView)(((LinearLayout)obj).getChildAt(0));
                tv.setText("第" + courseNumber + "节课");
            }
        }

//        int aCount = afternoonCourseLl.getChildCount();
//        for (int i = 0 ; i < aCount ; i++) {
//            View obj = afternoonCourseLl.getChildAt(i);
//            if (obj.getTag() != null) {
//                courseNumber++;
//                TextView tv = (TextView)(((LinearLayout)obj).getChildAt(0));
//                tv.setText("第" + courseNumber + "节课");
//            }
//        }
    }


    private void getRequestIds() {
        corseIds = "";
        corseNames = "";
        int initNum = 1;
        int count = morningCourseLl.getChildCount();
        for (int i = 0 ; i < count ; i++) {
            View tempView = morningCourseLl.getChildAt(i);
            if (tempView instanceof LinearLayout) {
                if (tempView.getTag() == null) {
                    continue;
                }

                corseIds = corseIds + initNum + ",";
                initNum++;
                EditText tempContentEt = (EditText)tempView.findViewById(R.id.corse_activity_corse_item_corse_name_et);
                corseNames = corseNames + tempContentEt.getText().toString() + ",";
            }
        }

        if (corseIds.contains(",")) {
            corseIds = corseIds.substring(0,corseIds.length()-1);
        }

        if (corseNames.contains(",")) {
            corseNames = corseNames.substring(0,corseNames.length()-1);
        }
    }

    //按学校ID 查询班级列表 （发布选择班级展示）
    private void requestClassListBySchoolId() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        ClassedAndTeachersPaser lp = new ClassedAndTeachersPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.SEARCH_CLASS_LIST_BY_SCHOOL_ID, requestData, lp);
    }

    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.select_course_activity_confirm_btn) {
                selectClassDialog.dismiss();
            }
        }
    };

    //课程表发布   (不管有多少节数，必须有多少课数)
    public void requestPublishCourse() {
        getRequestIds();
        if (!checkInput()) {
            return;
        }

        showProgressDialog();

        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("weeknum", weekNum);
        requestData.put("festivals",corseIds);
        requestData.put("course",corseNames);
        requestData.put("clssids",makeClassAddPersonInfo.getId());
        requestData.put("osperion", UserInfo.loginInfo.getInfo().getNickname());
        PublishCourseInfoPaser lp = new PublishCourseInfoPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PUBLISH_COURSE, requestData, lp);
    }

    private boolean checkInput() {
        if (TextUtils.isEmpty(weekNum)) {
            ToastUtil.showToast(this,"请选择星期",Toast.LENGTH_SHORT);
            return false;
        } else if (TextUtils.isEmpty(makeClassAddPersonInfo.getId())) {
            ToastUtil.showToast(this,"请选择班级",Toast.LENGTH_SHORT);
            return false;
        } else if (TextUtils.isEmpty(corseNames)) {
            ToastUtil.showToast(this,"请输入课程名称",Toast.LENGTH_SHORT);
            return false;
        }
        return true;
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
        } else if (nbs.obj instanceof PublishCourseInfo) {
            if (nbs.isSuccess()) {
                finish();
            } else {

            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
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
