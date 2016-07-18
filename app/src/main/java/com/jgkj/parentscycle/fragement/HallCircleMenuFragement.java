package com.jgkj.parentscycle.fragement;

import android.content.Context;
import android.content.Intent;
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
import com.jgkj.parentscycle.activity.BabyDocumentActivity;
import com.jgkj.parentscycle.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/9.
 */
public class HallCircleMenuFragement extends Fragment implements View.OnClickListener{
    RelativeLayout mCircleMenu;
    private  final String TAG = "HallCircleMenuFragement";
    private View babyDocument;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hall_publish_fragment_layout, container,
                false);
        mCircleMenu = (RelativeLayout)view.findViewById(R.id.hall_publish_fragment_circle_bg_rel);

        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        ViewGroup.LayoutParams params = mCircleMenu.getLayoutParams();
        params.height = width - 30;
        params.width = params.height;
        mCircleMenu.setLayoutParams(params);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        babyDocument = view.findViewById(R.id.hall_publish_fragment_baby_document_ll);
        babyDocument.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    @Override
    public void onClick(View v) {
        if (v == babyDocument) {
            startActivity(new Intent(v.getContext(), BabyDocumentActivity.class));
        }
    }
}
