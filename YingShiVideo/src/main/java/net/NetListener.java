package net;

public interface NetListener {
	public static final int NEED_TO_LOGIN = 1;
	public static final int REQUEST_NET_ERROR_CODE = -1000;
	public static final String REQUEST_NET_ERROR_MSG = "通讯异常,请稍后再试";

	public static final int REQUEST_NET_NOT_CONNECT_CODE = -1001;
	public static final String REQUEST_NOT_NET_ERROR_MSG = "通讯异常,请稍后再试";
	public void requestResponse(Object obj);
}
