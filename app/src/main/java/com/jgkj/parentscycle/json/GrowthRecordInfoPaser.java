package com.jgkj.parentscycle.json;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jgkj.parentscycle.bean.AnnouncementCommentListInfo;
import com.jgkj.parentscycle.bean.AnnouncementCommentListItemInfo;
import com.jgkj.parentscycle.bean.GrowthRecordInfo;
import com.jgkj.parentscycle.bean.GrowthRecordItemInfo;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;
import com.jgkj.parentscycle.utils.GsonUtil;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 16/10/1.
 */
public class GrowthRecordInfoPaser implements PaserJson {
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        Log.d("result", "the response code:" + response);
        if (response.obj != null) {
            GrowthRecordInfo tli = new GrowthRecordInfo();
            Type type = new TypeToken<List<GrowthRecordItemInfo>>() {}.getType();
            List<GrowthRecordItemInfo> atatol = GsonUtil.getListObject(response.getObj().toString(),type);
            tli.setObj(atatol);
            response.setObj(tli);
            return response;
        } else {
            response.setObj(new GrowthRecordInfo());
            return response;
        }

    }

    @Override
    public Object getErrorBeanData(String msg) {
        NetBeanSuper meData = new NetBeanSuper();
        meData.setResult(NetListener.REQUEST_NET_ERROR_CODE);
        if (TextUtils.isEmpty(msg)) {
            meData.setMsg(NetListener.REQUEST_NET_ERROR_MSG);
        } else {
            meData.setMsg(msg);
        }
        meData.setObj(new GrowthRecordInfo());
        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        NetBeanSuper meData = new NetBeanSuper();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        meData.setObj(new GrowthRecordInfo());
        return meData;
    }
}
