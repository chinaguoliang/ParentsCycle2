package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.AnnouncementCommentAdapter;
import com.jgkj.parentscycle.bean.AnnouncementCommentListInfo;
import com.jgkj.parentscycle.bean.AnnouncementCommentListItemInfo;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.AnnouncementCommentListInfoPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.widget.ListViewForScrollView;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/9/30.
 */
public class AnnouncementDetailActivity extends BaseActivity implements View.OnClickListener,NetListener{

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTv;

    @Bind(R.id.announcement_detail_activity_comment_lv)
    ListViewForScrollView commentListLv;

    private String annId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announcement_detail_activity);
        ButterKnife.bind(this);
        initView();
        String id = this.getIntent().getStringExtra("ann_id");
        requestAnnounceMentList(id);
    }


    private void initView() {
        titleTv.setText("");
        rightTv.setVisibility(View.GONE);
    }


    @OnClick({R.id.baby_document_activity_back_iv,
            R.id.baby_document_activity_title,
            R.id.baby_document_right_title_tv

    })
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }

    //查询课程列表
    private void requestAnnounceMentList(String id) {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows","30");
        requestData.put("page","1");
        requestData.put("announid",id);
        AnnouncementCommentListInfoPaser lp = new AnnouncementCommentListInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_DETAIL, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof ClassedAndTeachersListInfo) {
            if (nbs.isSuccess()) {
                AnnouncementCommentListInfo tii = (AnnouncementCommentListInfo)nbs.obj;
                initListView(tii.getDataList());
            } else {
                ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
            }
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    private void initListView(List<AnnouncementCommentListItemInfo> dataList) {
        AnnouncementCommentAdapter adapter = new AnnouncementCommentAdapter(this,dataList);
        commentListLv.setAdapter(adapter);
    }

}
