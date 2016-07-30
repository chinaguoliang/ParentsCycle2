package com.jgkj.parentscycle.net;

import org.json.JSONException;
import org.json.JSONObject;

public interface PaserJson {
	public Object parseJSonObject(NetBeanSuper response) throws JSONException;
	public Object getErrorBeanData(String msg);
	public Object getNetNotConnectData();
}
