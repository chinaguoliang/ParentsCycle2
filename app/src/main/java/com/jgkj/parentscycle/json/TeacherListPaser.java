package com.jgkj.parentscycle.json;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jgkj.parentscycle.bean.TeachersListInfo;
import com.jgkj.parentscycle.bean.TeachersListItemInfo;
import com.jgkj.parentscycle.net.JsonUtil;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;
import com.jgkj.parentscycle.utils.GsonUtil;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 16/8/23.
 */
public class TeacherListPaser implements PaserJson {
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        Log.d("result", "the response code:" + response);
        if (response.obj != null) {
            TeachersListInfo tli = new TeachersListInfo();
            Type type = new TypeToken<List<TeachersListItemInfo>>() {}.getType();
            List<TeachersListItemInfo> atatol = GsonUtil.getListObject(response.getObj().toString(),type);
            tli.setData(atatol);
            response.setObj(tli);
            return response;
        } else {
            response.setObj(new TeachersListInfo());
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
        meData.setObj(new TeachersListInfo());
        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        NetBeanSuper meData = new NetBeanSuper();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        meData.setObj(new TeachersListInfo());
        return meData;
    }
}
