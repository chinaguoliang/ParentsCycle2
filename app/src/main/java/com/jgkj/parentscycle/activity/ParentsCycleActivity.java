package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.ParentsCycleAdapter;
import com.jgkj.parentscycle.bean.ParentsCycleListInfo;
import com.jgkj.parentscycle.bean.ParentsCyclePostsListItem;
import com.jgkj.parentscycle.customview.HeaderGridView;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.ParentsCyclePostsListItemPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/24.
 */
public class ParentsCycleActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.parents_cycle_activity_lv)
    HeaderGridView mContentLv;

    TextView newestTopicTv;
    TextView recmommedTv;
    TextView myCycleTv;
    ImageView userIconIv;

    int textGrayColor;
    int blackColor;
    ParentsCycleAdapter mParentsCycleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parents_cycle_activity);
        ButterKnife.bind(this);
        initView();
        requestParentsCycleList();
    }

    private void initView() {
        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.white));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("父母圈");
        titleTv.setTextColor(this.getResources().getColor(R.color.black));
        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.parents_cycle_activity_listview_header_view,null);
        mContentLv.addHeaderView(headerView);

        newestTopicTv = (TextView)headerView.findViewById(R.id.parents_cycle_activity_listview_header_view_newest_topic_tv);
        recmommedTv = (TextView)headerView.findViewById(R.id.parents_cycle_activity_listview_header_view_recommend_tv);
        myCycleTv = (TextView)headerView.findViewById(R.id.parents_cycle_activity_listview_header_my_cycle_tv);
        userIconIv = (ImageView)headerView.findViewById(R.id.parents_cycle_activity_listview_header_view_user_icon_iv);

        newestTopicTv.setOnClickListener(this);
        recmommedTv.setOnClickListener(this);
        myCycleTv.setOnClickListener(this);
        userIconIv.setOnClickListener(this);

        textGrayColor = this.getResources().getColor(R.color.text_gray);
        blackColor = this.getResources().getColor(R.color.black);
        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        titleTv.setTextColor(this.getResources().getColor(R.color.white));
    }



    @OnClick({R.id.baby_document_activity_back_iv})

    @Override
    public void onClick(View v) {
        if (backIv == v) {
            finish();
        } else if (newestTopicTv == v) {
            resetColor();
            newestTopicTv.setTextColor(blackColor);
//            ArrayList<ParentsCycleInfo> dataList = getTestData(0);
//
//            mParentsCycleAdapter.setDataList(dataList);
//            mParentsCycleAdapter.notifyDataSetChanged();
        } else if (recmommedTv == v) {
            resetColor();
            recmommedTv.setTextColor(blackColor);
//            ArrayList<ParentsCycleInfo> dataList = getTestData(1);
//
//
//            mParentsCycleAdapter.setDataList(dataList);
//            mParentsCycleAdapter.notifyDataSetChanged();
        } else if (myCycleTv == v) {
            resetColor();
            myCycleTv.setTextColor(blackColor);
//            ArrayList<ParentsCycleInfo> dataList = getTestData(2);
//
//
//            mParentsCycleAdapter.setDataList(dataList);
//            mParentsCycleAdapter.notifyDataSetChanged();
        } else if (userIconIv == v) {
            startActivity(new Intent(v.getContext(),ParentsCyclePersonalPageActivity.class));
        }
    }

    private void resetColor() {
        newestTopicTv.setTextColor(textGrayColor);
        recmommedTv.setTextColor(textGrayColor);
        myCycleTv.setTextColor(textGrayColor);
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

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

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ParentsCycleListInfo) {
            if (nbs.isSuccess()) {
                ParentsCycleListInfo tii = (ParentsCycleListInfo)nbs.obj;
                initList(tii.getObj());
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }

    private void initList(List<ParentsCyclePostsListItem> data) {

        mParentsCycleAdapter = new ParentsCycleAdapter(this,data);
        mContentLv.setAdapter(mParentsCycleAdapter);
        mContentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParentsCyclePostsListItem pcpli = (ParentsCyclePostsListItem)mParentsCycleAdapter.getItem(position - 2);
                Intent intent = new Intent(ParentsCycleActivity.this,ParentsCycleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("parentsinfo",pcpli);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
