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
import com.jgkj.parentscycle.bean.PublishGrowthRecordInfo;
import com.jgkj.parentscycle.bean.PublishParentsCycleInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.global.ConfigPara;
import com.jgkj.parentscycle.json.ParentsParentsCycleInfoPaser;
import com.jgkj.parentscycle.json.PublishGrowthRecordInfoPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.NetRequest;
import com.jgkj.parentscycle.user.UserInfo;
import com.jgkj.parentscycle.utils.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 16/9/11.
 */
public class PublishGrowthRecordActivity extends BaseActivity implements View.OnClickListener,NetListener {
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
        setContentView(R.layout.publish_growth_record_activity);
        ButterKnife.bind(this);
        mInflater = (LayoutInflater)getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        initView();
    }

    private void initView() {
        titleTv.setText("成长日记");
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
                ToastUtil.showToast(this,"请输入发布内容", Toast.LENGTH_SHORT);
                return;
            }

            requestPublishBabyGrowRecord();
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

    // 宝宝成长记录发布
    public void requestPublishBabyGrowRecord() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(System.currentTimeMillis());
        requestData.put("babyid", "11");
        requestData.put("publisher",UserInfo.loginInfo.getRole().getId());
        String name = UserInfo.loginInfo.getRole().getName();
        if (TextUtils.isEmpty(name)) {
            requestData.put("publishername", "匿名");
        } else {
            requestData.put("publishername", name);
        }

        requestData.put("publishertext",sendContentStr);
        requestData.put("bobypublisherdate",date);
        requestData.put("publisherimge",imgUrls);



        PublishGrowthRecordInfoPaser lp = new PublishGrowthRecordInfoPaser();
        NetRequest.getInstance().request(mQueue, this,
                BgGlobal.PUBLISH_BABY_GROW_RECORD, requestData, lp);
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
        if (nbs.obj instanceof PublishGrowthRecordInfo) {
            if (nbs.isSuccess()) {
                finish();
            } else {

            }
            ToastUtil.showToast(this,nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }
}
