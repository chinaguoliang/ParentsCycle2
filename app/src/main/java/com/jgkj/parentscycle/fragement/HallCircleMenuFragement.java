package com.jgkj.parentscycle.fragement;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 16/7/9.
 */
public class HallCircleMenuFragement extends Fragment {
    RelativeLayout mCircleMenu;
    private  final String TAG = "HallCircleMenuFragement";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_publish_fragment_layout, container,
                false);
        mCircleMenu = (RelativeLayout)view.findViewById(R.id.hall_publish_fragment_circle_bg_rel);
//        mCircleMenu.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                mCircleMenu.getViewTreeObserver().removeOnPreDrawListener(this);
//                ViewGroup.LayoutParams params = mCircleMenu.getLayoutParams();
//                params.height = params.width;
//                mCircleMenu.setLayoutParams(params);
//                LogUtil.d(TAG,"the size:" + params.height + "|" + params.width );
//                return true;
//            }
//        });

//        mCircleMenu.post(new Runnable() {
//            @Override
//            public void run() {
//                ViewGroup.LayoutParams params = mCircleMenu.getLayoutParams();
//                params.height = params.width;
//                mCircleMenu.setLayoutParams(params);
//                LogUtil.d(TAG,"the size:" + params.height + "|" + params.width );
//            }
//        });

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        ViewGroup.LayoutParams params = mCircleMenu.getLayoutParams();
        params.height = width;
        mCircleMenu.setLayoutParams(params);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }



}
