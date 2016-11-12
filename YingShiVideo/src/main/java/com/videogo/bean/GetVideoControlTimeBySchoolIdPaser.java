package com.videogo.bean;

import android.text.TextUtils;
import android.util.Log;


import com.google.gson.reflect.TypeToken;
import com.videogo.GsonUtil;

import net.NetBeanSuper;
import net.NetListener;
import net.PaserJson;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 16/10/24.
 */
public class GetVideoControlTimeBySchoolIdPaser implements PaserJson {
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        Log.d("result", "the response code:" + response);
        if (response.obj != null) {
            VideoControlTimeInfo vcti = new VideoControlTimeInfo();
            Type type = new TypeToken<List<VideoControlTImeItem>>() {}.getType();
            List<VideoControlTImeItem> atatol = GsonUtil.getListObject(response.getObj().toString(),type);
            vcti.setObj(atatol);
            response.setObj(vcti);
            return response;
        } else {
            response.setObj(new GetVideoControlTimeInfo());
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
        meData.setObj(new VideoControlTimeInfo());
        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        NetBeanSuper meData = new NetBeanSuper();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        meData.setObj(new VideoControlTimeInfo());
        return meData;
    }
}
