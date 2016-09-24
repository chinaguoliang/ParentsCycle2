package com.jgkj.parentscycle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jgkj.parentscycle.R;
import com.jgkj.parentscycle.adapter.BabyDocumentAdapter;
import com.jgkj.parentscycle.bean.BabyDocumentListInfo;
import com.jgkj.parentscycle.bean.BabyDocumentListInfoItem;
import com.jgkj.parentscycle.bean.ParentsPerfectInfo;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.json.BabyDocumentListInfoPaser;
import com.jgkj.parentscycle.json.ResetPasswordPaser;
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
 * Created by chen on 16/7/14.
 */
public class BabyDocumentActivity extends BaseActivity implements View.OnClickListener,NetListener{
    @Bind(R.id.baby_document_activity_lv)
    ListView mListView;

    @Bind(R.id.baby_document_activity_title)
    TextView title;

    @Bind(R.id.baby_document_activity_back_iv)
    ImageView backIv;

    BabyDocumentAdapter mBabyDocumentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_document_activity);
        ButterKnife.bind(this);

        title.setText("宝宝档案");
        requestBabyList();


    }



    @OnClick({R.id.baby_document_activity_back_iv})
    @Override
    public void onClick(View v) {
        if (v == backIv) {
            finish();
        }
    }

    @Override
    public void uploadImgFinished(Bitmap bitmap,String uploadedKey) {

    }

    // 宝宝列表
    public void requestBabyList() {
        showProgressDialog();
        HashMap<String, String> requestData = new HashMap<String, String>();
        requestData.put("rows", "10");
        requestData.put("page","1");
        BabyDocumentListInfoPaser lp = new BabyDocumentListInfoPaser();
        NetRequest.getInstance().request(mQueue, this, BgGlobal.BABY_LIST, requestData, lp);
    }

    @Override
    public void requestResponse(Object obj) {
        NetBeanSuper nbs = (NetBeanSuper)obj;
        hideProgressDialog();
        if (nbs.obj instanceof BabyDocumentListInfo) {
            if (nbs.isSuccess()) {
                BabyDocumentListInfo bdlii = (BabyDocumentListInfo)nbs.obj;
                initListView(bdlii.getDataList());
            } else {

            }

            ToastUtil.showToast(this, nbs.getMsg(), Toast.LENGTH_SHORT);
        }
    }

    private void initListView( List<BabyDocumentListInfoItem> data) {
        mBabyDocumentAdapter = new BabyDocumentAdapter(this,data);
        mListView.setAdapter(mBabyDocumentAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mBabyDocumentAdapter.setCurrSelPos(position);
                mBabyDocumentAdapter.notifyDataSetChanged();
            }
        });
    }
}
