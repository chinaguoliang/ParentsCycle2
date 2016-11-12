package net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


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

	public void requestTest(RequestQueue mQueue, final NetListener netListener,
						final String requestUrl,
						final HashMap<String, String> requestParams, final PaserJson pj) {
		String url = NetUrls.CHEN_BASE_URL + requestUrl;
		final boolean isNetConnected = true;


		StringRequest request = new StringRequest(Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							NetBeanSuper nbs = (NetBeanSuper)JsonUtil.getTopObject(response, NetBeanSuper.class);
							Object obj = pj.parseJSonObject(nbs);

							if (obj == null) {
								responseError(pj,isNetConnected,netListener,response);
							} else {
								netListener.requestResponse(obj);
							}
						} catch (Exception e) {
							e.printStackTrace();
							responseError(pj,isNetConnected,netListener,null);
						}
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				responseError(pj,isNetConnected,netListener,null);
			}
		}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				return requestParams;
			}
		};

		if (requestParams.containsKey(NET_RETRY_TIMES_KEY)) {
			int retryTime = Integer.parseInt(requestParams.get(NET_RETRY_TIMES_KEY).toString());
			request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, retryTime, 1.0f));
		} else {
			// 设定超时后不重试
			request.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, 1.0f));
		}
		mQueue.add(request);
	}




	private void responseError(PaserJson pj,boolean isNetConnected, NetListener netListener,String response) {
		String msg = null;
		if (!TextUtils.isEmpty(response)) {
			try {
				JSONObject json = new JSONObject(response);
				msg = json.getString("msg").toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

//		if (!isNetConnected) {
//			netListener.requestResponse(pj
//					.getNetNotConnectData());
//		} else {
			netListener.requestResponse(pj
					.getErrorBeanData(msg));
//		}
	}


}
