package com.jgkj.parentscycle.activity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.widget.SexSelectDialog;

import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/20.
 */
public class AddBabyActivity extends BaseActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener,NetListener,SexSelectDialog.SexSlectDialogFinish{

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.baby_info_activity_name_et)
    EditText nameEt;

    @Bind(R.id.baby_info_activity_sex_rel)
    RelativeLayout sexRel;

    @Bind(R.id.baby_info_activity_sex_tv)
    TextView sexTv;

    @Bind(R.id.baby_info_activity_birthday_rel)
    RelativeLayout birthdayRel;

    @Bind(R.id.baby_info_activity_birthday_tv)
    TextView birthdayTv;

    @Bind(R.id.baby_info_activity_age_et)
    EditText ageEt;

    @Bind(R.id.baby_info_activity_to_school_time_rel)
    RelativeLayout toSchoolTimeRel;

    @Bind(R.id.baby_info_activity_to_school_time_tv)
    TextView toSchoolTimeTv;

    @Bind(R.id.baby_info_activity_farther_et)
    EditText fatherNameEt;

    @Bind(R.id.baby_info_activity_farther_phone_et)
    EditText fatherPhoneEt;

    @Bind(R.id.baby_info_activity_monther_et)
    EditText motherNameEt;

    @Bind(R.id.baby_info_activity_monther_phone_et)
    EditText motherPhoneEt;

    @Bind(R.id.baby_info_activity_save_btn)
    Button saveBtn;

    private int dateType = 0; // 1:出生日期  2：入校时间
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_baby_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        titleTv.setText("添加宝宝");
        rightTitleTv.setVisibility(View.GONE);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.baby_info_activity_save_btn,R.id.baby_info_activity_to_school_time_rel,R.id.baby_info_activity_birthday_rel,R.id.baby_info_activity_sex_rel})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == saveBtn) {

        } else if (v == toSchoolTimeRel) {
            dateType = 2;
            showDateDialog();
        } else if (v == sexRel) {
            SexSelectDialog.showSexSelectDialog(this,this,0);
        } else if (v == birthdayRel) {
            dateType = 1;
            showDateDialog();
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
    public void requestResponse(Object obj) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth;
        if (dateType == 1) {
            //出生日期
            birthdayTv.setText(date);
            birthdayTv.setTag(date);
        } else if (dateType == 2) {
            //入校时间
            toSchoolTimeTv.setText(date);
            toSchoolTimeTv.setTag(date);
        }
    }

    @Override
    public void finishSlecct(int index) {
        if (index == 1) {
            //男
            sexTv.setText("男");
        } else if (index == 0) {
            //女
            sexTv.setText("女");
        }
        sexTv.setTag(index);
    }
}
