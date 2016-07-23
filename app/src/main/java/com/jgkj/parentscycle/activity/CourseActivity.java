package com.jgkj.parentscycle.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.activity.BaseActivity;

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


    @Bind(R.id.back_iv)
    ImageView backIv;

    private LayoutInflater mInflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @OnClick({R.id.back_iv,R.id.course_activity_afternoon_add_course_btn,R.id.course_activity_morning_add_course_btn})

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
}
