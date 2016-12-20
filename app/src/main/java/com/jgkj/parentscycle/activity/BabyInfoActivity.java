package com.jgkj.parentscycle.activity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.BabyDocumentListInfoItem;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.PerfectBabyInfo;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.PerfectBabyInfoPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.AsyncImageUtil;
import com.jgkj.parentscycle.utils.CircularImage;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.widget.ListViewForScrollView;
import com.jgkj.parentscycle.widget.SexSelectDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/30.
 */
public class BabyInfoActivity extends BaseActivity implements View.OnClickListener,SexSelectDialog.SexSlectDialogFinish,DatePickerDialog.OnDateSetListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    View titleBg;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.baby_info_activity_user_icon_iv)
    CircularImage iconIv;

    @Bind(R.id.baby_info_activity_top_name_tv)
    TextView topNameTv;


    @Bind(R.id.baby_info_activity_name_rel)
    RelativeLayout nameRel;

    @Bind(R.id.baby_info_activity_name_et)
    EditText nameet;


    @Bind(R.id.baby_info_activity_sex_rel)
    RelativeLayout sexRel;

    @Bind(R.id.baby_info_activity_sex_tv)
    TextView sexTv;


    @Bind(R.id.baby_info_activity_birthday_rel)
    RelativeLayout birthdayRel;

    @Bind(R.id.baby_info_activity_birthday_tv)
    TextView birthdayTv;


    @Bind(R.id.baby_info_activity_age_rel)
    RelativeLayout ageRel;

    @Bind(R.id.baby_info_activity_age_et)
    EditText ageEt;


    @Bind(R.id.baby_info_activity_to_school_time_rel)
    RelativeLayout toSchoolTimeRel;

    @Bind(R.id.baby_info_activity_to_school_time_tv)
    TextView toSchoolTimeTv;


    @Bind(R.id.baby_info_activity_farther_rel)
    RelativeLayout fartherRel;

    @Bind(R.id.baby_info_activity_farther_et)
    EditText fartherEt;


    @Bind(R.id.baby_info_activity_farther_phone_rel)
    RelativeLayout fartherPhoneRel;

    @Bind(R.id.baby_info_activity_farther_phone_et)
    EditText fartherPhoneEt;


    @Bind(R.id.baby_info_activity_monther_rel)
    RelativeLayout montherRel;

    @Bind(R.id.baby_info_activity_monther_et)
    EditText montherEt;


    @Bind(R.id.baby_info_activity_monther_phone_rel)
    RelativeLayout montherPhoneRel;

    @Bind(R.id.baby_info_activity_monther_phone_et)
    EditText montherPhoneEt;

    @Bind(R.id.baby_info_activity_belong_class_rel)
    RelativeLayout belongClassRel;

    @Bind(R.id.baby_info_activity_belong_class_tv)
    TextView belongClassTv;


    @Bind(R.id.baby_info_activity_join_class_rel)
    RelativeLayout joinClassRel;

    @Bind(R.id.baby_info_activity_transfer_class_rel)
    RelativeLayout transferClassRel;

    @Bind(R.id.baby_info_activity_leave_school_rel)
    RelativeLayout leaveSchoolRel;

    @Bind(R.id.baby_info_activity_save_btn)
    Button saveBtn;

    private String nameStr;
    private String ageStr;
    private String sexStr;
    private String birthdayStr;


    String headUrl = "";

    int selSex = -1;

    BabyDocumentListInfoItem  mBabyDocumentListInfoItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_info_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        Bundle bundle = this.getIntent().getExtras();
        mBabyDocumentListInfoItem = (BabyDocumentListInfoItem)bundle.get("baby_info");
        titleBg.setBackgroundColor(0x00000000);
        rightTv.setVisibility(View.GONE);
        titleTv.setText("宝宝详情");
        nameet.setText(mBabyDocumentListInfoItem.getUsername());
        String sex = mBabyDocumentListInfoItem.getSex();
        if (TextUtils.equals(sex,"0")) {
            sexTv.setText("女");
        } else {
            sexTv.setText("男");
        }
    }

    @OnClick({R.id.baby_document_activity_back_iv,
              R.id.baby_info_activity_join_class_rel,
              R.id.baby_info_activity_sex_rel,
            R.id.baby_info_activity_birthday_rel,
            R.id.baby_info_activity_transfer_class_rel,
            R.id.baby_info_activity_leave_school_rel,
            R.id.baby_info_activity_to_school_time_rel,
            R.id.baby_info_activity_user_icon_iv,
            R.id.baby_info_activity_save_btn


    })
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == joinClassRel) {

        } else if (v == sexRel) {
            SexSelectDialog.showSexSelectDialog(BabyInfoActivity.this,BabyInfoActivity.this,0);
        } else if (v == birthdayRel) {
            showDateDialog(1);
        } else if (v == transferClassRel) {

        } else if (v == leaveSchoolRel) {

        } else if (v == toSchoolTimeRel) {
            showDateDialog(2);
        } else if (v == iconIv) {
            showChangePhotoDialog();
        } else if (v == saveBtn) {
            if (!checkInput()) {
                return;
            }
            requestPerfectBabyInfo();
        }
    }

    private boolean checkInput() {
        nameStr = nameet.getText().toString();
        if (TextUtils.isEmpty(nameStr)) {
            ToastUtil.showToast(this,"请输入姓名",Toast.LENGTH_SHORT);
            return false;
        }

        ageStr = ageEt.getText().toString();
        if (TextUtils.isEmpty(ageStr)) {
            ToastUtil.showToast(this,"请输入年龄",Toast.LENGTH_SHORT);
            return false;
        }

        sexStr = sexTv.getText().toString();
        if (TextUtils.isEmpty(sexStr)) {
            ToastUtil.showToast(this,"请输入性别",Toast.LENGTH_SHORT);
            return false;
        }

        birthdayStr = birthdayTv.getText().toString();
        if (TextUtils.isEmpty(birthdayStr)) {
            ToastUtil.showToast(this,"请输入生日",Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }


    private void showDateDialog(int flag) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        int year=d.get(Calendar.YEAR);
        int month=d.get(Calendar.MONTH);
        int day=d.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,android.R.style.Theme_Holo_Light_Dialog,this,year,month,day);
        dpd.getDatePicker().setTag(flag);
        dpd.getWindow().setBackgroundDrawable(new BitmapDrawable()); //设置为透明
        dpd.show();
    }

    @Override
    public void finishSlecct(int index) {
        if (index == 1) {
            sexTv.setText("男");
            selSex = 1;
        } else if (index == 0) {
            sexTv.setText("女");
            selSex = 0;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
        int flag = Integer.parseInt(view.getTag().toString());
        if (flag == 1) {
            birthdayTv.setText(date);
        } else if (flag == 2) {
            toSchoolTimeTv.setText(date);
        }
    }


    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {
        headUrl = BgGlobal.IMG_SERVER_PRE_URL + uploadedKey;
        AsyncImageUtil.asyncLoadImage(iconIv,
                headUrl,
                R.mipmap.user_default_icon, true, false);
    }


    // 宝宝信息完善
    public void requestPerfectBabyInfo() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("age", ageStr);
        requestData.put("bgurl","1");
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("bobybirthdate",date);
        SimpleDateFormat sdf1 =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1 = sdf.format(System.currentTimeMillis());
        requestData.put("bobyenrollmentdate",date1);
        requestData.put("iocurl","1");
        requestData.put("isshool","1");

        requestData.put("nickname","1");
        requestData.put("ostmpid","66");
        requestData.put("sex",sexStr);
        requestData.put("username",nameStr);

        PerfectBabyInfoPaser lp = new PerfectBabyInfoPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PERFECT_BYBY_INFO, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof PerfectBabyInfo) {
            if (nbs.isSuccess()) {
                finish();
            } else {
            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
