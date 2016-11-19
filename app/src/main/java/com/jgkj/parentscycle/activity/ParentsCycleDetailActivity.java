package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.SchoolDetailListviewAdapter;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.CommonListInfo;
import com.jgkj.parentscycle.bean.ParentsCyclePostsListItem;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.CommonListInfoPaser;
import com.jgkj.parentscycle.json.ParentsCyclePostsListItemPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/7/24.
 */
public class ParentsCycleDetailActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.title_bar_layout_rel)
    RelativeLayout mWrapTitleRel;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.school_detail_activity_lv)
    ListView mListView;

    TextView nameTv;
    TextView timeTv;
    TextView contentTv;

    TextView commentCountTv;

    TextView focusTv;
    int commentCount = 0;

    ParentsCyclePostsListItem mParentsCyclePostsListItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_detail_activity);
        ButterKnife.bind(this);
        mParentsCyclePostsListItem = (ParentsCyclePostsListItem)this.getIntent().getExtras().getSerializable("parentsinfo");
        mWrapTitleRel.setBackgroundColor(this.getResources().getColor(R.color.white));
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("详情");
        titleTv.setTextColor(this.getResources().getColor(R.color.black));

        LayoutInflater inflater = LayoutInflater.from(this);
        View headerView = inflater.inflate(R.layout.school_detail_activity_listview_headerview,null);
        focusTv = (TextView)headerView.findViewById(R.id.school_detail_activity_listview_header_view_focus_tv);
        commentCountTv = (TextView)headerView.findViewById(R.id.school_detail_activity_listview_header_view_comment_count_tv);

        nameTv = (TextView)headerView.findViewById(R.id.school_detail_activity_listview_header_view_name_tv);
        timeTv = (TextView)headerView.findViewById(R.id.school_detail_activity_listview_header_view_time_tv);
        contentTv = (TextView)headerView.findViewById(R.id.school_detail_activity_listview_header_view_content_tv);

        nameTv.setText(mParentsCyclePostsListItem.getTopic());
        timeTv.setText(mParentsCyclePostsListItem.getUpdatetime());
        contentTv.setText(mParentsCyclePostsListItem.getTopictext());

        focusTv.setOnClickListener(this);
        mListView.addHeaderView(headerView);

        requestCommonList();
    }

    private List<String> getTestData() {
        ArrayList <String> data = new ArrayList<String>();
        for (int i = 0 ; i < 30 ; i++) {
            if (i % 2 == 0) {
                data.add("李雷");
            } else {
                data.add("韩梅梅");
            }
        }
        return data;
    }

    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == focusTv) {
            if (focusTv.getTag() == null) {
                focusTv.setTag("setFocus");

                focusTv.setText("已关注");
                focusTv.setBackgroundResource(R.mipmap.had_focus);
                focusTv.setTextColor(this.getResources().getColor(R.color.white));

            } else {
                focusTv.setTag(null);
                focusTv.setText("关注");
                focusTv.setBackgroundResource(R.mipmap.not_focus);
                focusTv.setTextColor(this.getResources().getColor(R.color.main_blue_color));
            }
        } else if (v == backIv) {
            finish();
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    public void requestCommonList() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows", "10");
        requestData.put("page","1");
        CommonListInfoPaser lp = new CommonListInfoPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.COMMENTS_LIST, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof CommonListInfo) {
            if (nbs.isSuccess()) {
                CommonListInfo tii = (CommonListInfo)nbs.obj;
                commentCount = commentCount + tii.getObj().size();
                SchoolDetailListviewAdapter sla = new  SchoolDetailListviewAdapter(this,tii.getObj());
                mListView.setAdapter(sla);
                commentCountTv.setText("评论" + commentCount + "条");
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }
}
