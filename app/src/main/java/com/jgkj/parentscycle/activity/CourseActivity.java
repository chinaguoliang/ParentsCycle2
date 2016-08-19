package com.jgkj.parentscycle.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/23.
 */
public class CourseActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.course_activity_morning_add_course_btn)
    Button addCourseMorningBtn;

    @Bind(R.id.course_activity_afternoon_add_course_btn)
    Button addCourseAfternootBtn;

    @Bind(R.id.course_activity_afternoon_course_ll)
    LinearLayout afternoonCourseLl;

    @Bind(R.id.course_activity_morning_course_ll)
    LinearLayout morningCourseLl;

    @Bind(R.id.course_activity_filter_iv)
    ImageView courseFilterIv;

    @Bind(R.id.back_iv)
    ImageView backIv;

    private LayoutInflater mInflater;
    Dialog selectClassDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @OnClick({R.id.back_iv,R.id.course_activity_afternoon_add_course_btn,R.id.course_activity_morning_add_course_btn,R.id.course_activity_filter_iv})

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == addCourseMorningBtn) {
            addCourseView(morningCourseLl);
            refreshCourseNumber();
        } else if (v == addCourseAfternootBtn) {
            addCourseView(afternoonCourseLl);
            refreshCourseNumber();
        } else if (v == courseFilterIv) {
            showChangePhotoDialog();
        }
    }

    private void addCourseView(LinearLayout viewLl) {
        LinearLayout courseItem = (LinearLayout)mInflater.inflate(R.layout.corse_activity_course_item,null);
        courseItem.setTag("courseItem");
        int childCount = courseItem.getChildCount();
        viewLl.addView(courseItem,childCount - 1);
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

        int aCount = afternoonCourseLl.getChildCount();
        for (int i = 0 ; i < aCount ; i++) {
            View obj = afternoonCourseLl.getChildAt(i);
            if (obj.getTag() != null) {
                courseNumber++;
                TextView tv = (TextView)(((LinearLayout)obj).getChildAt(0));
                tv.setText("第" + courseNumber + "节课");
            }
        }
    }


//    private void showChangePhotoDialog() {
//        selectClassDialog = new Dialog(this, R.style.DialogTheme);
//        selectClassDialog.getWindow().setWindowAnimations(
//                R.style.dialogWindowAnim);
//
//        View contentView = LayoutInflater.from(this).inflate(
//                R.layout.select_course_activity_sel_course_dialog, null);
//
//        Button confirmBtn = (Button)contentView.findViewById(R.id.select_course_activity_confirm_btn);
//        confirmBtn.setOnClickListener(dialogListener);
//
//        selectClassDialog.setContentView(contentView);
//        selectClassDialog.setCanceledOnTouchOutside(true);
//        selectClassDialog.show();
//
//        WindowManager.LayoutParams params = selectClassDialog.getWindow()
//                .getAttributes();
//        params.gravity = Gravity.CENTER;
//        params.width = UtilTools.SCREEN_WIDTH - UtilTools.SCREEN_WIDTH / 10;
//        selectClassDialog.getWindow().setAttributes(params);
//
//    }

    private View.OnClickListener dialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.select_course_activity_confirm_btn) {
                selectClassDialog.dismiss();
            }
        }
    };
}
