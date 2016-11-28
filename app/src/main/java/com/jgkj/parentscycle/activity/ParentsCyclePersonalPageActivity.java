package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ParentsCycleAdapter;
import com.jgkj.parentscycle.bean.ParentsCycleInfo;
import com.jgkj.parentscycle.bean.ParentsCycleListInfo;
import com.jgkj.parentscycle.bean.ParentsCyclePostsListItem;
import com.jgkj.parentscycle.customview.HeaderGridView;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.ParentsCyclePostsListItemPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/8/27.
 */
public class ParentsCyclePersonalPageActivity extends BaseActivity implements View.OnClickListener,NetListener {

    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.parents_cycle_personalpage_activity_lv)
    HeaderGridView mContentLv;

    @Bind(R.id.parents_cycle_personalpage_activity_send_msg_btn)
    Button sendMsgBtn;

    ParentsCycleAdapter mParentsCycleAdapter;

    ImageView perfectInfoIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parents_cycle_personalpage_activity);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("");
        titleTv.setTextColor(this.getResources().getColor(R.color.black));

        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.parents_cycle_personalpage_activity_listview_header_view,null);
        mContentLv.addHeaderView(headerView);



        perfectInfoIv = (ImageView)headerView.findViewById(R.id.parents_cycle_personalpage_activity_perfect_info_iv);
        perfectInfoIv.setOnClickListener(this);
        requestParentsCycleList();
    }

    //食谱发布
    public void requestParentsCycleList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows", "30");
        requestData.put("page","1");
        ParentsCyclePostsListItemPaser lp = new ParentsCyclePostsListItemPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PARENTS_CYCLE_POSTS_LIST, requestData, lp);
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.parents_cycle_personalpage_activity_send_msg_btn})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == perfectInfoIv) {
            startActivity(new Intent(v.getContext(),PerfectInformationActivity.class));
        } else if (v == sendMsgBtn) {
            startActivity(new Intent(v.getContext(),PublishParentsCycleActivity.class));
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ParentsCycleListInfo) {
            if (nbs.isSuccess()) {
                ParentsCycleListInfo tii = (ParentsCycleListInfo)nbs.obj;
                mParentsCycleAdapter = new ParentsCycleAdapter(this,tii.getObj());
                mContentLv.setAdapter(mParentsCycleAdapter);
                mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int targetPos = position - 2;
                        if (targetPos < 0) {
                            targetPos = 0;
                        }
                        ParentsCyclePostsListItem pcpli = (ParentsCyclePostsListItem)mParentsCycleAdapter.getItem(targetPos);
                        Intent intent = new Intent(view.getContext(),ParentsCycleDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("parentsinfo",pcpli);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
