package com.jgkj.parentscycle.json;

import com.jgkj.parentscycle.bean.ModifyPassInfo;
import com.jgkj.parentscycle.bean.RegisterInfo;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;

import org.json.JSONException;

/**
 * Created by chen on 16/7/12.
 */
public class ModifyPassPaser implements PaserJson {
    @Override
    public Object parseJSonObject(String response) throws JSONException {
        ModifyPassInfo atatol=(ModifyPassInfo) JsonUtil.getObject(response, ModifyPassInfo.class);
        return atatol;
    }

    @Override
    public Object getErrorBeanData() {
        ModifyPassInfo meData = new ModifyPassInfo();
        meData.setResult(NetListener.REQUEST_NET_ERROR_CODE);
        meData.setMsg(NetListener.REQUEST_NET_ERROR_MSG);
        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        ModifyPassInfo meData = new ModifyPassInfo();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        return meData;
    }
}
