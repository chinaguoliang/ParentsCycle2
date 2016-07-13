package com.jgkj.parentscycle.json;

import android.util.Log;

import com.jgkj.parentscycle.bean.GetVerifyNumInfo;
import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;

import org.json.JSONException;

/**
 * Created by chen on 16/7/13.
 */
public class GetVerifyPhoneNumPaser implements PaserJson {
    @Override
    public Object parseJSonObject(String response) throws JSONException {
        Log.d("result", "the response code:" + response);
        GetVerifyNumInfo atatol=(GetVerifyNumInfo) JsonUtil.getObject(response, GetVerifyNumInfo.class);
        return atatol;
    }

    @Override
    public Object getErrorBeanData() {
        GetVerifyNumInfo meData = new GetVerifyNumInfo();
        meData.setResult(NetListener.REQUEST_NET_ERROR_CODE);
        meData.setMsg(NetListener.REQUEST_NET_ERROR_MSG);
        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        GetVerifyNumInfo meData = new GetVerifyNumInfo();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        return meData;
    }
}
