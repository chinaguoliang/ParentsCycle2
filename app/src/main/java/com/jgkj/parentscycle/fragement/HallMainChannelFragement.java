package com.jgkj.parentscycle.fragement;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.activity.BabyDocumentActivity;
import com.jgkj.parentscycle.activity.CheckAttendanceActivity;
import com.jgkj.parentscycle.activity.PublishCourseActivity;
import com.jgkj.parentscycle.activity.MainActivity;
import com.jgkj.parentscycle.activity.ParentsCycleActivity;
import com.jgkj.parentscycle.activity.PublishFoodListActivity;
import com.jgkj.parentscycle.activity.ShowCourseListActivity;
import com.jgkj.parentscycle.activity.ShowFoodListActivity;
import com.jgkj.parentscycle.activity.TestNetActivity;
import com.jgkj.parentscycle.adapter.ImageAdapter;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ModifyPassByOldPassPaser;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ImageHandler;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;


import java.util.ArrayList;
import java.util.HashMap;

import android.os.Message;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/9.
 */
public class HallMainChannelFragement  extends Fragment implements View.OnClickListener{
    private String flag = "remove the no use";
    @Bind(R.id.baby_document_activity_title)
    TextView centerTitleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.hall_main_channel_fragment_layout_viewpager)
    ViewPager viewPager;

    @Bind(R.id.main_activity_circle_iv_1)
    ImageView circleIv1;

    @Bind(R.id.main_activity_circle_iv_2)
    ImageView circleIv2;

    @Bind(R.id.main_activity_circle_iv_3)
    ImageView circleIv3;

    @Bind(R.id.hall_main_channel_fragment_layout_video_course_tv)
    TextView publicVideoCourseTv;

    @Bind(R.id.hall_main_channel_fragment_layout_safe_send_tv)
    TextView safeSendTv;

    @Bind(R.id.hall_main_channel_fragment_layout_food_tv)
    TextView foodTv;

    @Bind(R.id.hall_main_channel_fragment_course_list_tv)
    TextView courseListTv;

    @Bind(R.id.hall_main_channel_fragment_parents_cycle_tv)
    TextView parentsCycleTv;

    @Bind(R.id.hall_main_channel_fragment_baby_doc_tv)
    TextView babyDocTv;

    @Bind(R.id.hall_main_channel_fragment_course_line_tv)
    TextView courseLineTv;

    @Bind(R.id.hall_main_channel_fragment_listen_bar_tv)
    TextView listenBarTv;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_main_channel_fragment_layout, container, false);
        ButterKnife.bind(this, view);
        initViews();
        initViewPager();
        return view;
    }

    private void resetCircle() {
        circleIv1.setImageResource(R.drawable.white_circle);
        circleIv2.setImageResource(R.drawable.white_circle);
        circleIv3.setImageResource(R.drawable.white_circle);
    }

    private void initViews() {
        rightTitleTv.setText("23842038490");
        rightTitleTv.setPadding(0,20,0,20);
        rightTitleTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));

        backIv.setVisibility(View.GONE);
    }

    private void initViewPager() {
        final ImageHandler handler = ((MainActivity)getActivity()).handler;
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        ImageView view1 = (ImageView) inflater.inflate(R.layout.main_viewpager_item_layout, null);
        ImageView view2 = (ImageView) inflater.inflate(R.layout.main_viewpager_item_layout, null);
        ImageView view3 = (ImageView) inflater.inflate(R.layout.main_viewpager_item_layout, null);
        view1.setImageResource(R.mipmap.baby_school_1);
        view2.setImageResource(R.mipmap.baby_school_inside);
        view3.setImageResource(R.mipmap.hall_main_channel_lv_item_bg);
        ArrayList<ImageView> views = new ArrayList<ImageView>();
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new ImageAdapter(views));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //閰嶅悎Adapter鐨刢urrentItem瀛楁杩涜璁剧疆銆�
            @Override
            public void onPageSelected(int arg0) {
                handler.sendMessage(Message.obtain(handler, ImageHandler.MSG_PAGE_CHANGED, arg0, 0));
                resetCircle();
                if (arg0 % 3 == 0) {
                    circleIv1.setImageResource(R.drawable.blue_circle);
                } else if (arg0 % 3 == 1) {
                    circleIv2.setImageResource(R.drawable.blue_circle);
                } else if (arg0 % 3 == 2) {
                    circleIv3.setImageResource(R.drawable.blue_circle);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }


            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        handler.sendEmptyMessage(ImageHandler.MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
        int mode = Integer.MAX_VALUE % 3;
        int value = Integer.MAX_VALUE / 2;
        int currentItem;
        if (mode != 0) {
            currentItem = value + mode;
        } else {
            currentItem = value;
        }
        currentItem = currentItem - 1;
        viewPager.setCurrentItem(currentItem);


        //寮�杞挱鏁堟灉
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(ImageHandler.MSG_UPDATE_IMAGE, ImageHandler.MSG_DELAY);
            }
        }, ImageHandler.MSG_DELAY);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setViewPagerCurrentItem(int position) {
        viewPager.setCurrentItem(position);
    }

    @OnClick({R.id.baby_document_right_title_tv,

            R.id.hall_main_channel_fragment_layout_video_course_tv,
            R.id.hall_main_channel_fragment_layout_safe_send_tv,
            R.id.hall_main_channel_fragment_layout_food_tv,
            R.id.hall_main_channel_fragment_course_list_tv,
            R.id.hall_main_channel_fragment_parents_cycle_tv,
            R.id.hall_main_channel_fragment_baby_doc_tv,
            R.id.hall_main_channel_fragment_course_line_tv,
            R.id.hall_main_channel_fragment_listen_bar_tv,
    })
    @Override
    public void onClick(View v) {
        if (v == rightTitleTv) {
            startActivity(new Intent(getContext(), TestNetActivity.class));
        } else if (v == publicVideoCourseTv) {
            if (UserInfo.isLogined) {
//                UtilTools.toVideoModule(v.getContext(), Volley.newRequestQueue(v.getContext()));
                ((MainActivity)this.getActivity()).requestVideoData();
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
            //startActivity(new Intent(getContext(), VideoOpenCourseActivity.class));

        } else if (v == safeSendTv) {

            if (UserInfo.isLogined) {
                startActivity(new Intent(getContext(), CheckAttendanceActivity.class));
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
        } else if (v == foodTv) {

            if (UserInfo.isLogined) {
                startActivity(new Intent(getContext(), ShowFoodListActivity.class));
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
        } else if (v == courseListTv) {
            if (UserInfo.isLogined) {
                startActivity(new Intent(getContext(), ShowCourseListActivity.class));
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
        } else if (v == parentsCycleTv) {

            if (UserInfo.isLogined) {
                startActivity(new Intent(getContext(), ParentsCycleActivity.class));
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
        } else if (v == babyDocTv) {

            if (UserInfo.isLogined) {
                startActivity(new Intent(getContext(), BabyDocumentActivity.class));
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
        } else if (v == courseLineTv) {

            if (UserInfo.isLogined) {
                ((MainActivity)this.getActivity()).requestVideoData();
            } else {
                ToastUtil.showToast(v.getContext(),"请先登录", Toast.LENGTH_SHORT);
            }
        } else if (v == listenBarTv) {

        }
    }

}
