package com.jgkj.parentscycle.fragement;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.activity.MainActivity;
import com.jgkj.parentscycle.adapter.ImageAdapter;
import com.jgkj.parentscycle.bean.HallMainChannelLvInfo;
import com.jgkj.parentscycle.utils.ImageHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import android.os.Message;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/7/9.
 */
public class HallMainChannelFragement  extends Fragment {

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
        circleIv1.setImageResource(R.drawable.gray_circle);
        circleIv2.setImageResource(R.drawable.gray_circle);
        circleIv3.setImageResource(R.drawable.gray_circle);
    }

    private void initViews() {
        rightTitleTv.setVisibility(View.GONE);
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
                    circleIv1.setImageResource(R.drawable.white_circle);
                } else if (arg0 % 3 == 1) {
                    circleIv2.setImageResource(R.drawable.white_circle);
                } else if (arg0 % 3 == 2) {
                    circleIv3.setImageResource(R.drawable.white_circle);
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
}
