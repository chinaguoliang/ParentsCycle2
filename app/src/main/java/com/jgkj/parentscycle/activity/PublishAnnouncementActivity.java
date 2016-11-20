package com.jgkj.parentscycle.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.PublishAnnouncementInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.PublishAnnouncementInfoPaser;
import com.jgkj.parentscycle.json.TeacherInfoLIstPaser;
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
 * Created by chen on 16/8/20.
 */
public class PublishAnnouncementActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView rightTitleTv;

    @Bind(R.id.publish_announcement_activity_add_pic_tv)
    TextView addPicTv;

    @Bind(R.id.publish_announcement_activity_publish_btn)
    Button publishBtn;

    @Bind(R.id.publish_announcement_activity_title_et)
    EditText titleEt;

    @Bind(R.id.publish_announcement_activity_content_et)
    EditText contentEt;

    private String titleStr;
    private String contentStr;
    private String coverUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_announcement_activity);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        rightTitleTv.setVisibility(View.GONE);
        titleTv.setText("公告");
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.publish_announcement_activity_add_pic_tv,R.id.publish_announcement_activity_publish_btn})
    @Override
    public void onClick(View v) {
        if (backIv == v) {
            finish();
        } else if (addPicTv == v) {
            showChangePhotoDialog();
        } else if (publishBtn == v) {
            if (!checkInput()) {
                return;
            }

            requestAnnouncementPublish();
        }
    }

    private boolean checkInput() {
        titleStr = titleEt.getText().toString();
        contentStr = contentEt.getText().toString();
        if (TextUtils.isEmpty(titleStr)) {
            ToastUtil.showToast(this,"请输入标题", Toast.LENGTH_SHORT);
            return false;
        }

        if (TextUtils.isEmpty(contentStr)) {
            ToastUtil.showToast(this,"请输入内容", Toast.LENGTH_SHORT);
            return false;
        }

        if (TextUtils.isEmpty(coverUrl)) {
            ToastUtil.showToast(this,"请上传图片", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }


    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {
        addPicTv.setText("");
        addPicTv.setBackgroundDrawable(new BitmapDrawable(bitmap));
        coverUrl = BgGlobal.IMG_SERVER_PRE_URL + uploadedKey;

    }

//    //公告评论保存
//    private void requestAnnouncementCommentSave() {
//        showProgressDialog();
//        HashMap<String, String> requestData = new HashMap<String, String>();
//        requestData.put("id", UserInfo.loginInfo.getRole().getId());
//        requestData.put("title", titleStr);
//        requestData.put("bannerimg", "1.jpg");
//        requestData.put("announcement", contentStr);
//        requestData.put("selectrange", "10");
//        requestData.put("imags", coverUrl);
//        requestData.put("voidurls", "6.avi");
//        requestData.put("ospersion", "10");
//        requestData.put("criticsid","2");
//        requestData.put("announid","2");
//        requestData.put("isdispy","1");
//        requestData.put("critics","");
//        PublishAnnouncementInfoPaser lp = new PublishAnnouncementInfoPaser();
//        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_COMMENT_SAVE, requestData, lp);
//    }


    //公告发布
    private void requestAnnouncementPublish() {
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("title", titleStr);
        requestData.put("bannerimg", "6");
        requestData.put("announcement", contentStr);
        requestData.put("selectrange", "6");
//        ArrayList<String> result = new ArrayList<String>();
//        result.add("1.jpg");
//        result.add("2.jpg");
        requestData.put("imags", coverUrl);
        requestData.put("voidurls", "6");
        requestData.put("ospersion", "apple class");
        PublishAnnouncementInfoPaser lp = new PublishAnnouncementInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.ANNOUNCEMENT_PUBLISH, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof PublishAnnouncementInfo) {
            if (nbs.isSuccess()) {
                finish();
            }
            ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
