package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.AnnouncementCommentSaveInfo;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.CommentSaveInfo;
import com.jgkj.parentscycle.bean.CommonListInfo;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.AnnouncementCommentSaveInfoPaser;
import com.jgkj.parentscycle.json.CommentSaveInfoPaser;
import com.jgkj.parentscycle.json.PerfectInfoPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ToastUtil;
import com.jgkj.parentscycle.utils.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/11/20.
 */

public class CommentsSaveActivity extends BaseActivity implements View.OnClickListener,NetListener {
    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {

    }

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    private String contentStr;

    @Bind(R.id.comments_save_activity_content_et)
    EditText contentEt;

    @Bind(R.id.comments_save_activity_save_btn)
    Button saveBtn;

    private String topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_save_activity);
        topicId = this.getIntent().getStringExtra("topic_id");
        ButterKnife.bind(this);
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("评论");
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.comments_save_activity_save_btn})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == saveBtn) {
            if (!checkInput()) {
                return;
            }
            requestSaveComments();
        }
    }

    private boolean checkInput() {
        contentStr = contentEt.getText().toString();
        if (TextUtils.isEmpty(contentStr)) {
            ToastUtil.showToast(this,"请输入评论内容", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private void requestSaveComments() {
        showProgressDialog();
        HashMap <String,String> requestData = new HashMap<String,String>();

        requestData.put("id", UserInfo.loginInfo.getRole().getId());
        requestData.put("title", "");
        requestData.put("bannerimg", "1.jpg");
        requestData.put("announcement", "bullshit");
        requestData.put("selectrange", "10");
        requestData.put("imags", "");
        requestData.put("voidurls", "6.avi");
        requestData.put("ospersion", "10");
        requestData.put("criticsid","2");
        requestData.put("announid",topicId);
        requestData.put("isdispy","1");
        requestData.put("critics",contentStr);

        requestData.put("tocipid", topicId);
        requestData.put("criticsid", UserInfo.loginInfo.getRole().getId());
        requestData.put("criticstext", contentStr);
//        if (TextUtils.isEmpty(UserInfo.loginInfo.getRole().getName())) {
        requestData.put("critics",contentStr);
//        } else {
//            requestData.put("critics",UserInfo.loginInfo.getRole().getName());
//        }

        requestData.put("isdispy","1");
        AnnouncementCommentSaveInfoPaser lp = new AnnouncementCommentSaveInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_COMMENT_SAVE, requestData, lp);
    }


    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof AnnouncementCommentSaveInfo) {
            if (nbs.isSuccess()) {
                setResult(10);
                finish();
            } else {

            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
