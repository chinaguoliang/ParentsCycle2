package com.jgkj.parentscycle.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.PublishParentsCycleInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ParentsParentsCycleInfoPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/9/1.
 */
public class PublishParentsCycleActivity extends BaseActivity implements View.OnClickListener,NetListener{

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    @Bind(R.id.baby_document_activity_title)
    TextView titleTv;

    @Bind(R.id.baby_document_right_title_tv)
    TextView sendTv;

    @Bind(R.id.publish_parents_cycle_activity_publish_content_tv)
    EditText publishContentEt;

    @Bind(R.id.publish_parents_cycle_activity_publish_img_ll)
    LinearLayout publishImgLl;

    @Bind(R.id.publish_parents_cycle_activity_add_img_tv)
    TextView addImgTv;


    private String sendContentStr;
    private String imgUrls;

    LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_parents_cycle_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        titleTv.setText("父母圈");
        sendTv.setText("发送");
        ViewTreeObserver companyTreeObserver = addImgTv.getViewTreeObserver();
        companyTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                addImgTv.getViewTreeObserver().removeOnPreDrawListener(this);
                publishImgLl.setTag(addImgTv.getWidth());
                return true;
            }
        });
    }

    @OnClick({R.id.baby_document_activity_back_iv,R.id.baby_document_right_title_tv,R.id.publish_parents_cycle_activity_add_img_tv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        } else if (v == sendTv) {
            getImgAndContent();

            if (TextUtils.isEmpty(sendContentStr)) {
                ToastUtil.showToast(this,"请输入发布内容",Toast.LENGTH_SHORT);
                return;
            }

            requestParentsCycleSendArticle();
        } else if (v == addImgTv) {
            showChangePhotoDialog();
        }
    }


    @Override
    public void uploadImgFinished(Bitmap bitmap, String uploadedKey) {
        ImageView iv = new ImageView(this);
        int width = Integer.parseInt(publishImgLl.getTag().toString());
        iv.setImageBitmap(bitmap);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width,width);
        iv.setPadding(10,10,10,10);
        iv.setLayoutParams(params);
        String imgUrl = BgGlobal.IMG_SERVER_PRE_URL + uploadedKey;
        iv.setTag(imgUrl);
        publishImgLl.addView(iv,0);
    }

    // 父母圈发帖
    public void requestParentsCycleSendArticle() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("schoolid", ConfigPara.SCHOOL_ID);
        requestData.put("address","空");
        requestData.put("topic","空");
        requestData.put("topictext",sendContentStr);
        requestData.put("topicimg",imgUrls);
        requestData.put("topictype","1");

        ParentsParentsCycleInfoPaser lp = new ParentsParentsCycleInfoPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PARENTS_CYCLE_SEND_ARTICLE, requestData, lp);
    }

    private void getImgAndContent() {
        sendContentStr = publishContentEt.getText().toString();
        imgUrls = "";
        int count = publishImgLl.getChildCount();
        for (int i = 0 ; i < count ; i++) {
            View tempView = publishImgLl.getChildAt(i);
            if (tempView instanceof ImageView) {
                ImageView tempIv = (ImageView)tempView;
                Object urlObj = tempIv.getTag();
                if (urlObj != null) {
                    imgUrls = imgUrls + urlObj.toString() + ",";
                }
            }
        }

        if (imgUrls.contains(",")) {
            imgUrls = imgUrls.substring(0,imgUrls.length()-1);
        }


    }

    @Override
    public void requestResponse(Object obj) {
        hideProgressDialog();
        NetBeanSuper nbs = (NetBeanSuper)obj;
        if (nbs.obj instanceof PublishParentsCycleInfo) {
            if (nbs.isSuccess()) {
                finish();
            } else {

            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
