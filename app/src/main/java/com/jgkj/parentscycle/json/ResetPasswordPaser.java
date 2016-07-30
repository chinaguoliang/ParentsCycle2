package com.jgkj.parentscycle.json;

import android.text.TextUtils;
import android.util.Log;

import com.jgkj.parentscycle.bean.CheckFindPasswordInfo;
import com.jgkj.parentscycle.bean.ResetPasswordInfo;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;

import org.json.JSONException;

/**
 * Created by chen on 16/7/24.
 */
public class ResetPasswordPaser implements PaserJson{
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        Log.d("result", "the response code:" + response);
        ResetPasswordInfo atatol=(ResetPasswordInfo) JsonUtil.getObject(response.getObj().toString(), ResetPasswordInfo.class);
        return atatol;
    }

    @Override
    public Object getErrorBeanData(String msg) {
        ResetPasswordInfo meData = new ResetPasswordInfo();
        meData.setResult(NetListener.REQUEST_NET_ERROR_CODE);
        if (TextUtils.isEmpty(msg)) {
            meData.setMsg(NetListener.REQUEST_NET_ERROR_MSG);
        } else {
            meData.setMsg(msg);
        }

        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        ResetPasswordInfo meData = new ResetPasswordInfo();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        return meData;
    }
}
