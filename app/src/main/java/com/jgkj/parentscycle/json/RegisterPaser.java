package com.jgkj.parentscycle.json;

import android.text.TextUtils;
import android.util.Log;

import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.bean.RegisterInfo;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;
import com.jgkj.parentscycle.utils.LogUtil;

import org.json.JSONException;

/**
 * Created by chen on 16/7/12.
 */
public class RegisterPaser implements PaserJson{
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        RegisterInfo atatol=(RegisterInfo) JsonUtil.getObject(response.getObj().toString(), RegisterInfo.class);
        return atatol;
    }

    @Override
    public Object getErrorBeanData(String msg) {
        RegisterInfo meData = new RegisterInfo();
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
        RegisterInfo meData = new RegisterInfo();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        return meData;
    }
}
