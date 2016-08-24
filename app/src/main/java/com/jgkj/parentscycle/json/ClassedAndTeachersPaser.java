package com.jgkj.parentscycle.json;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.jgkj.parentscycle.bean.ClassedAndTeachersListInfo;
import com.jgkj.parentscycle.bean.ClassesAndTeachersListItemInfo;
import com.jgkj.parentscycle.bean.TeachersListInfo;
import com.jgkj.parentscycle.bean.TeachersListItemInfo;
import com.jgkj.parentscycle.net.NetBeanSuper;
import com.jgkj.parentscycle.net.NetListener;
import com.jgkj.parentscycle.net.PaserJson;
import com.jgkj.parentscycle.utils.GsonUtil;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by chen on 16/8/25.
 */
public class ClassedAndTeachersPaser implements PaserJson {
    @Override
    public Object parseJSonObject(NetBeanSuper response) throws JSONException {
        Log.d("result", "the response code:" + response);
        if (response.obj != null) {
            ClassedAndTeachersListInfo tli = new ClassedAndTeachersListInfo();
            Type type = new TypeToken<List<ClassesAndTeachersListItemInfo>>() {}.getType();
            List<ClassesAndTeachersListItemInfo> atatol = GsonUtil.getListObject(response.getObj().toString(),type);
            tli.setDataList(atatol);
            response.setObj(tli);
            return response;
        } else {
            response.setObj(new ClassedAndTeachersListInfo());
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
        meData.setObj(new ClassedAndTeachersListInfo());
        return meData;
    }

    @Override
    public Object getNetNotConnectData() {
        NetBeanSuper meData = new NetBeanSuper();
        meData.setResult(NetListener.REQUEST_NET_NOT_CONNECT_CODE);
        meData.setMsg(NetListener.REQUEST_NOT_NET_ERROR_MSG);
        meData.setObj(new ClassedAndTeachersListInfo());
        return meData;
    }
}
