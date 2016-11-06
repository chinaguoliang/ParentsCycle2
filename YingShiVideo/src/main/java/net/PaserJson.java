package net;

import org.json.JSONException;

public interface PaserJson {
	public Object parseJSonObject(NetBeanSuper response) throws JSONException;
	public Object getErrorBeanData(String msg);
	public Object getNetNotConnectData();
}
