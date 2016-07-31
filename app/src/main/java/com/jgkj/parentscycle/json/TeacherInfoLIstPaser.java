package com.jgkj.parentscycle.json;

import android.text.TextUtils;
import android.util.Log;

import com.jgkj.parentscycle.bean.LoginInfo;
import com.jgkj.parentscycle.bean.ResetPasswordInfo;
import com.jgkj.parentscycle.bean.TeacherInfoListInfo;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;

import org.json.JSONException;

/**
 * Created by chen on 16/8/1.
 */
public class TeacherInfoLIstPaser implements PaserJson{
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        Log.d("result", "the response code:" + response);
        TeacherInfoListInfo atatol=(TeacherInfoListInfo) JsonUtil.getObject(response.getObj().toString(), TeacherInfoListInfo.class);
        response.setObj(atatol);
        return response;
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

        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        NetBeanSuper meData = new NetBeanSuper();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        return meData;
    }
}
