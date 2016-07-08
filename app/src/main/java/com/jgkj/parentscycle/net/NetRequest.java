package com.jgkj.parentscycle.net;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jgkj.parentscycle.global.BgGlobal;
import com.jgkj.parentscycle.utils.LogUtil;
import com.jgkj.parentscycle.utils.UtilTools;


public class NetRequest {
	private static final String TAG = "NetRequest";
	public static final String NET_RETRY_TIMES_KEY = "request_net_retry_time";
	private static NetRequest instance = null;

	private NetRequest() {
	};

	public static NetRequest getInstance() {
		if (instance == null) {
			synchronized (NetRequest.class) {
				if (instance == null) {
					instance = new NetRequest();
				}
			}
		}
		return instance;
	}

	public void request(RequestQueue mQueue, final NetListener netListener,
			final String requestUrl,
			final HashMap<String, String> requestParams, final PaserJson pj) {
		String url = BgGlobal.BASE_URL + requestUrl + BgGlobal.DEVICE_PARAMS;
		LogUtil.d(TAG, "request params:" + requestParams.toString());
		LogUtil.d(TAG, "request url:" + url);
		LogUtil.d(TAG, "request url api:" + requestUrl);
		final boolean isNetConnected = UtilTools.isNetworkConnected();

	
		StringRequest request = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							LogUtil.bigLog(TAG, "response:" + response);
							Object obj = pj.parseJSonObject(response);
							netListener.requestResponse(obj);
						} catch (Exception e) {
							e.printStackTrace();
							if (!isNetConnected) {
								netListener.requestResponse(pj
										.getNetNotConnectData());
							} else {
								netListener.requestResponse(pj
										.getErrorBeanData());
							}
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						if (!isNetConnected) {
							netListener.requestResponse(pj
									.getNetNotConnectData());
						} else {
							netListener.requestResponse(pj.getErrorBeanData());
						}
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return requestParams;
			}
		};

		if (requestParams.containsKey(NET_RETRY_TIMES_KEY)) {
			int retryTime = Integer.parseInt(requestParams.get(NET_RETRY_TIMES_KEY).toString());
			request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, retryTime, 1.0f));
		} else {
			// 设定超时后不重试
			request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, 1.0f));
		}
		mQueue.add(request);
	}



	public void requestGet(RequestQueue mQueue, final NetListener netListener,
			final String requestUrl,
			final HashMap<String, String> requestParams, final PaserJson pj) {
		final boolean isNetConnected = UtilTools.isNetworkConnected();
		String url = requestUrl;
		StringRequest request = new StringRequest(Method.GET, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							LogUtil.d(TAG, "response_get:" + response);
							// Object obj = pj.parseJSonObject(response);
							// netListener.requestResponse(obj);
						} catch (Exception e) {
							e.printStackTrace();
							// if (!isNetConnected) {
							// netListener.requestResponse(pj
							// .getNetNotConnectData());
							// } else {
							// netListener.requestResponse(pj
							// .getErrorBeanData());
							// }
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// if (!isNetConnected) {
						// netListener.requestResponse(pj
						// .getNetNotConnectData());
						// } else {
						// netListener.requestResponse(pj.getErrorBeanData());
						// }
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return requestParams;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String, String> headers = new HashMap<String, String>();
//				headers.put("Charset", "UTF-8");
//				headers.put("Content-Type", "application/x-javascript");
//				headers.put("Accept-Encoding", "gzip,deflate");
//				headers.put("X-LC-Id", MyApplication.getInstance().getAppId());
//				headers.put("X-LC-Key", HjsApplication.getInstance().getAppKey());
				return headers;
			}

		};
		// 设定超时后不重试
		request.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
		mQueue.add(request);
	}
}
