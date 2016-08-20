package com.jgkj.parentscycle.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jgkj.parentscycle.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/20.
 */
public class PublishFoodListActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.back_iv)
    ImageView backIv;

    @Bind(R.id.publish_food_list_activity_sv_ll)
    LinearLayout contentSvLl;

    @Bind(R.id.publish_food_list_activity_publish_btn)
    Button publishBtn;

    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_food_list_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @OnClick({R.id.back_iv,R.id.publish_food_list_activity_publish_btn})

    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == publishBtn) {
            addFoodList(contentSvLl);
        }
    }

    private void addFoodList(final LinearLayout viewLl) {
        final LinearLayout courseItem = (LinearLayout)mInflater.inflate(R.layout.publish_food_list_item,null);
        courseItem.setTag("courseItem");
        int childCount = courseItem.getChildCount();
        viewLl.addView(courseItem,childCount - 1);

        ImageView deleteIv = (ImageView) courseItem.findViewById(R.id.publish_food_list_item_del_iv);
        deleteIv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                viewLl.removeView(courseItem);
            }
        });
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }
}
