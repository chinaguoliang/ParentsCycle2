package com.jgkj.parentscycle.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.hyphenate.chat.EMClient;
//import com.hyphenate.chatuidemo.DemoHelper;
//import com.hyphenate.chatuidemo.ui.LoginActivity;
//import com.hyphenate.chatuidemo.ui.MainActivity;
import com.jgkj.parentscycle.application.MyApplication;
//import com.videogo.openapi.EZOpenSDK;


public class UtilTools {
	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;

	public static void toChatModule(final Context context) {
//		new Thread(new Runnable() {
//			public void run() {
//				if (DemoHelper.getInstance().isLoggedIn()) {
//					// auto login mode, make sure all group and conversation is loaed before enter the main screen
//
//					EMClient.getInstance().groupManager().loadAllGroups();
//					EMClient.getInstance().chatManager().loadAllConversations();
//
//					context.startActivity(new Intent(context, MainActivity.class));
//
//				}else {
//					context.startActivity(new Intent(context, LoginActivity.class));
//				}
//			}
//		}).start();
	}

	public static void toVideoModule() {
//		EZOpenSDK.getInstance().openLoginPage();
	}

	public static String getRequestParams(ArrayList<String> data) {
		if (data.size() == 1) {
			return data.get(0);
		}

		String result = "";
		int count = data.size();
		for (int i = 0 ; i < count ; i++) {
			result = result + data.get(i);
			if (i < (count - 1)) {
				result = result + ",";
			}
		}
		return result;
	}
//
//	public static Bitmap getBitmap(String url) {
//
//		// httpGet连接对象
//		HttpGet httpRequest = new HttpGet(url);
//		// 取得HttpClient 对象
//		HttpClient httpclient = new DefaultHttpClient();
//		try {
//			// 请求httpClient ，取得HttpRestponse
//			HttpResponse httpResponse = httpclient.execute(httpRequest);
//			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//				// 取得相关信息 取得HttpEntiy
//				HttpEntity httpEntity = httpResponse.getEntity();
//				// 获得一个输入流
//				InputStream is = httpEntity.getContent();
//				System.out.println(is.available());
//				System.out.println("Get, Yes!");
//				Bitmap bitmap = BitmapFactory.decodeStream(is);
//				is.close();
//				return bitmap;
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
	
	/*
	 * 获取图片
	 */
	public static Bitmap getBitmap_(String path) {
		Bitmap bitmap = null;
		try {
			URL url = new URL(path);
			// 打开连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置请求方式
			conn.setRequestMethod("GET");
			// 设置超时
			conn.setConnectTimeout(5000);

			int count = conn.getResponseCode();

			if (count == 200) {
				bitmap = BitmapFactory.decodeStream(conn.getInputStream());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bitmap;
	}




//	public static void post(final Handler mHandler, final String actionUrl,
//			final Map<String, String> params, final File file) {
//		new Thread() {
//			@Override
//			public void run() {
//				try {
//					String BOUNDARY = java.util.UUID.randomUUID().toString();
//					String PREFIX = "--", LINEND = "\r\n";
//					String MULTIPART_FROM_DATA = "multipart/form-data";
//					String CHARSET = "UTF-8";
//					URL uri = new URL(actionUrl);
//					HttpURLConnection conn = (HttpURLConnection) uri
//							.openConnection();
//					conn.setReadTimeout(5 * 1000);
//					conn.setDoInput(true);// 允许输入
//					conn.setDoOutput(true);// 允许输出
//					conn.setUseCaches(false);
//					conn.setRequestMethod("POST"); // Post方式
//					conn.setRequestProperty("connection", "keep-alive");
//					conn.setRequestProperty("Charsert", "UTF-8");
//					conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
//							+ ";boundary=" + BOUNDARY);
//					// 首先组拼文本类型的参数
//					StringBuilder sb = new StringBuilder();
//					for (Map.Entry<String, String> entry : params.entrySet()) {
//						sb.append(PREFIX);
//						sb.append(BOUNDARY);
//						sb.append(LINEND);
//						sb.append("Content-Disposition: form-data; name=\""
//								+ entry.getKey() + "\"" + LINEND);
//						sb.append("Content-Type: text/plain; charset="
//								+ CHARSET + LINEND);
//						sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
//						sb.append(LINEND);
//						sb.append(entry.getValue());
//						sb.append(LINEND);
//					}
//					DataOutputStream outStream = new DataOutputStream(
//							conn.getOutputStream());
//					outStream.write(sb.toString().getBytes());
//					// 发送文件数据
//					if (file != null) {
//						StringBuilder sb1 = new StringBuilder();
//						sb1.append(PREFIX);
//						sb1.append(BOUNDARY);
//						sb1.append(LINEND);
//						sb1.append("Content-Disposition: form-data; name=\"icon\"; filename=\""
//								+ file.getName() + "\"" + LINEND);
//						sb1.append("Content-Type: multipart/form-data; charset="
//								+ CHARSET + LINEND);
//						sb1.append(LINEND);
//						outStream.write(sb1.toString().getBytes());
//						InputStream is = new FileInputStream(file);
//						byte[] buffer = new byte[1024];
//						int len = 0;
//						while ((len = is.read(buffer)) != -1) {
//							outStream.write(buffer, 0, len);
//						}
//						is.close();
//						outStream.write(LINEND.getBytes());
//					}
//
//					// 请求结束标志
//					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND)
//							.getBytes();
//					outStream.write(end_data);
//					outStream.flush();
//					// 得到响应码
//					boolean success = conn.getResponseCode() == 200;
//					InputStream in = conn.getInputStream();
//					InputStreamReader isReader = new InputStreamReader(in);
//					BufferedReader bufReader = new BufferedReader(isReader);
//					String line = null;
//					String data = "";
//					while ((line = bufReader.readLine()) != null)
//						data += line;
//
//					// Log.d(Environment.TAG, "data:  -- "+data);
//					outStream.close();
//					conn.disconnect();
//					Message msg = new Message();
//					JSONObject json = new JSONObject(data);
//					if (TextUtils.equals(json.getString("result"), "0")) {
//						msg.what = UserInfoActivity.UPLOAD_AVATAR_SUCCESS;
//						msg.obj = json.get("msg");
//					} else {
//						msg.what = UserInfoActivity.UPLOAD_AVATAR_ERROR;
//						msg.obj = "失败";
//					}
//					mHandler.sendMessage(msg);
//				} catch (Exception e) {
//					Message msg = new Message();
//					msg.what = UserInfoActivity.UPLOAD_AVATAR_ERROR;
//					msg.obj = "失败";
//					mHandler.sendMessage(msg);
//					e.printStackTrace();
//				}
//			};
//		}.start();
//
//	}

	public static void setRateLevel(LinearLayout ll, int rateLevel) {
		int willRemove = 5 - rateLevel;
		if (willRemove == 0) {
			return;
		}

		ArrayList<ImageView> tempIv = new ArrayList<ImageView>();

		int childCount = ll.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Object obj = ll.getChildAt(i);
			if (obj instanceof ImageView) {
				if (willRemove > 0) {
					// ll.removeView((View)obj);
					tempIv.add((ImageView) obj);
					willRemove--;
				}
			}
		}

		int count = tempIv.size();
		for (int i = 0; i < count; i++) {
			ll.removeView(tempIv.get(i));
		}
	}

	public static String getStatusStr(String temp) {
		if ("null".equals(temp) || TextUtils.isEmpty(temp)) {
			return "";
		}

		int status = Integer.parseInt(temp);
		switch (status) {
		case 1:
			return "新建";
		case 2:
			return "大咖接受";
		case 3:
			return "大咖拒绝";
		case 4:
			return "大咖确认超时";
		case 5:
			return "用户已付款";
		case 6:
			return "付款超时";
		case 7:
			return "已见面";
		case 8:
			return "已评论";
		case 9:
			return "用户取消";
		case 10:
			return "已聊天联系";
		case 11:
			return "新建未审核";
		case 12:
			return "审核拒绝";
		}
		return "";
	}

	public static int getViewY(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		return y;
	}

	public static int getViewX(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		return x;
	}

	public static Dialog dialog;

//	public static void showDialog(Context context, String msg) {
//		dialog = new Dialog(context,
//				android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
//		dialog.setContentView(R.layout.dialog_progress);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.show();
//	}

	public static void dissmissDilog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public static String formateTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd");
		Date allDate = new Date(time);
		String historyTime = format.format(allDate);

		long currTimeLong = System.currentTimeMillis();
		Date currDate = new Date(currTimeLong);
		String currTime = format.format(currDate);
		String dateSplit[] = historyTime.split(":");
		String currDateSplit[] = currTime.split(":");
		if (TextUtils.equals(historyTime, currTime)) {
			// 是同一天
			SimpleDateFormat tempformat = new SimpleDateFormat("HH:mm");
			Date oneDay = new Date(time);
			String t1 = tempformat.format(oneDay);
			return t1;
		} else if (TextUtils.equals(dateSplit[0], currDateSplit[0])
				&& !TextUtils.equals(dateSplit[2], currDateSplit[2])) {
			SimpleDateFormat tempformat = new SimpleDateFormat("MM月dd日 HH:mm");
			Date d1 = new Date(time);
			String t1 = tempformat.format(d1);
			return t1;
		} else {
			SimpleDateFormat tempformat = new SimpleDateFormat(
					"yyyy年MM月dd日 HH:mm");
			Date d1 = new Date(time);
			String t1 = tempformat.format(d1);
			return t1;
		}
	}

	/**
	 * 分享功能
	 * 
	 * @param context
	 *            上下文
	 * @param activityTitle
	 *            Activity的名字
	 * @param msgTitle
	 *            消息标题
	 * @param msgText
	 *            消息内容
	 * @param imgPath
	 *            图片路径，不分享图片则传null
	 */
	public void shareMsg(Context context, String activityTitle,
			String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(Intent.createChooser(intent, activityTitle));
	}

	public static String formatPhoneNum(String phoneNum) {
		if (phoneNum == null) {
			return "";
		}

		if ("".equals(phoneNum)) {
			return "";
		}
		String ss = phoneNum.substring(0,
				phoneNum.length() - (phoneNum.substring(3)).length())
				+ "****" + phoneNum.substring(7);
		return ss;

	}

	public static boolean isNetworkConnected() {
		Context context = MyApplication.getInstance();
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

//	public static ExpertAppointListItemData uoidToEalid(UserOrderInfoData uoid) {
//		ExpertAppointListItemData alId = new ExpertAppointListItemData();
//		alId.setCancel_time(uoid.getCancel_time());
//		alId.setChat_id(uoid.getChat_id());
//		alId.setChat_time(uoid.getChat_time());
//		alId.setCheck_time(uoid.getCheck_time());
//		alId.setComment(uoid.getComment());
//		alId.setComment_time(uoid.getComment_time());
//		alId.setConfirm_time(uoid.getConfirm_time());
//		alId.setCtime(uoid.getCtime());
//		alId.setExpert_id(uoid.getExpert_id());
//		alId.setExpert_status(uoid.getExpert_status());
//		alId.setExpert_uid(uoid.getExpert_uid());
//		alId.setHours(uoid.getHours());
//		// alId.setIcon(uoid.getIcon());
//		alId.setMeet_id(uoid.getMeet_id());
//		alId.setMeet_time(uoid.getMeet_time());
//		alId.setMinutes(uoid.getMinutes());
//		alId.setName(uoid.getUser_name());
//		alId.setPay_time(uoid.getPay_time());
//		alId.setPrice(uoid.getPrice());
//		alId.setQuestion(uoid.getQuestion());
//		alId.setRate(uoid.getRate());
//		alId.setStatus(uoid.getStatus());
//		alId.setTitle(uoid.getTitle());
//		alId.setTopic(uoid.getTopic());
//		alId.setTopic_id(uoid.getTopic_id());
//		alId.setUid(uoid.getUid());
//		alId.setUser_status(uoid.getUser_status());
//		alId.setUser_cancel_status(uoid.getUser_cancel_status());
//		alId.setExpert_cancel_status(uoid.getExpert_cancel_status());
//		alId.setOrigin_price(uoid.getOrigin_price());
//		alId.setFee(uoid.getFee());
//		alId.setCover(uoid.getCover());
//		alId.setMeet_people(uoid.getMeet_people());
//		alId.setExpert_name(uoid.getExpert_name());
//		alId.setFee_rate(uoid.getFee_rate());
//		alId.setExpert_title(uoid.getExpert_title());
//		alId.setUser_intro(uoid.getUser_intro());
//		alId.setUser_title(uoid.getUser_title());
//		alId.setUser_company(uoid.getUser_company());
//		alId.setIcon(uoid.getUser_icon());
//		alId.setIntro(uoid.getIntro());
//		return alId;
//	}
//
//	public static AppointmentListItemData uoidToAlid(UserOrderInfoData uoid) {
//		AppointmentListItemData alId = new AppointmentListItemData();
//		alId.setCancel_time(uoid.getCancel_time());
//		alId.setChat_id(uoid.getChat_id());
//		alId.setChat_time(uoid.getChat_time());
//		alId.setCheck_time(uoid.getCheck_time());
//		alId.setComment(uoid.getComment());
//		alId.setComment_time(uoid.getComment_time());
//		alId.setConfirm_time(uoid.getConfirm_time());
//		alId.setCtime(uoid.getCtime());
//		alId.setExpert_id(uoid.getExpert_id());
//		alId.setExpert_status(uoid.getExpert_status());
//		alId.setExpert_uid(uoid.getExpert_uid());
//		alId.setHours(uoid.getHours());
//		alId.setIcon(uoid.getIcon());
//		alId.setMeet_id(uoid.getMeet_id());
//		alId.setMeet_time(uoid.getMeet_time());
//		alId.setMinutes(uoid.getMinutes());
//		alId.setName(uoid.getUser_name());
//		alId.setBrief(uoid.getBrief());
//		alId.setPay_time(uoid.getPay_time());
//		alId.setPrice(uoid.getPrice());
//		alId.setQuestion(uoid.getQuestion());
//		alId.setRate(uoid.getRate());
//		alId.setStatus(uoid.getStatus());
//		alId.setTitle(uoid.getTitle());
//		alId.setTopic(uoid.getTopic());
//		alId.setTopic_id(uoid.getTopic_id());
//		alId.setUid(uoid.getUid());
//		alId.setUser_status(uoid.getUser_status());
//		alId.setUser_intro(uoid.getUser_intro());
//		alId.setIntro(uoid.getIntro());
//		alId.setUser_cancel_status(uoid.getUser_cancel_status());
//		alId.setExpert_cancel_status(uoid.getExpert_cancel_status());
//		alId.setCover(uoid.getCover());
//		alId.setMeet_people(uoid.getMeet_people());
//		alId.setExpert_name(uoid.getExpert_name());
//		return alId;
//	}

	// 转换成全角
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

//	/**
//	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//	 */
//	public static int px2dip(Context context, float pxValue) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (pxValue / scale + 0.5f);
//	}
//
//	public static void showLittleToastDialog(Context context) {
//		Dialog mLittleToastDialog = new Dialog(context, R.style.DialogTheme);
//		View contentView = LayoutInflater.from(context).inflate(
//				R.layout.dialog_limit_30_words, null);
//		mLittleToastDialog.setContentView(contentView);
//		// Button goOnBtn = (Button) contentView
//		// .findViewById(R.id.dialog_little_toast_go_on_btn);
//		// goOnBtn.setOnClickListener(hadKnowDialog);
//		// Button immReqBtn = (Button) contentView
//		// .findViewById(R.id.dialog_little_toast_request_btn);
//		// immReqBtn.setOnClickListener(hadKnowDialog);
//
//		Window dialogWindow = mLittleToastDialog.getWindow();
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		dialogWindow.setGravity(Gravity.CENTER);
//		lp.width = HjsGlobal.SCREEN_WIDTH / 5 * 4; // 宽度
//		// lp.height = HjsGlobal.SCREEN_HEIGHT / 2; // 高度
//		dialogWindow.setAttributes(lp);
//
//		mLittleToastDialog.setCanceledOnTouchOutside(true);
//		mLittleToastDialog.show();
//	}

//	public static void showRegisterSuccessDialog(final Context context) {
//		Dialog mLittleToastDialog = new Dialog(context, R.style.DialogTheme);
//		View contentView = LayoutInflater.from(context).inflate(
//				R.layout.dialog_register_success, null);
//		mLittleToastDialog.setContentView(contentView);
//		Button goOnBtn = (Button) contentView
//				.findViewById(R.id.dialog_register_success_had_kenow);
//		goOnBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				((Activity) context).finish();
//			}
//		});
//		// goOnBtn.setOnClickListener(hadKnowDialog);
//		// Button immReqBtn = (Button) contentView
//		// .findViewById(R.id.dialog_little_toast_request_btn);
//		// immReqBtn.setOnClickListener(hadKnowDialog);
//		ImageView closeIv = (ImageView) contentView
//				.findViewById(R.id.dialog_register_success_close_iv);
//		closeIv.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				((Activity) context).finish();
//			}
//		});
//
//		TextView secondText = (TextView) contentView
//				.findViewById(R.id.dialog_register_success_second_text_tv);
//		secondText
//				.setText(Html
//						.fromHtml("关注微信公众服务号“<font color=#ff5a60>大咖说</font>”，可以即时收到约见进度通知。"));
//		Window dialogWindow = mLittleToastDialog.getWindow();
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		dialogWindow.setGravity(Gravity.CENTER);
//		lp.width = HjsGlobal.SCREEN_WIDTH / 5 * 4; // 宽度
//		// lp.height = HjsGlobal.SCREEN_HEIGHT / 2; // 高度
//		dialogWindow.setAttributes(lp);
//
//		mLittleToastDialog.setCanceledOnTouchOutside(true);
//		mLittleToastDialog.show();
//	}
//
//	public static void showNoInputThanksLetterDialot(
//			final StudentAppointmentActivity context) {
//		final Dialog mLittleToastDialog = new Dialog(context,
//				R.style.DialogTheme);
//		View contentView = LayoutInflater.from(context).inflate(
//				R.layout.dialog_no_input_thanks_letter, null);
//		mLittleToastDialog.setContentView(contentView);
//		Button goOnBtn = (Button) contentView
//				.findViewById(R.id.dialog_no_input_thanks_letter_confirm_btn);
//		goOnBtn.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mLittleToastDialog.dismiss();
//				context.scrollToThanksEditText();
//			}
//		});
//
//		Window dialogWindow = mLittleToastDialog.getWindow();
//		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//		dialogWindow.setGravity(Gravity.CENTER);
//		lp.width = HjsGlobal.SCREEN_WIDTH / 5 * 4; // 宽度
//		// lp.height = HjsGlobal.SCREEN_HEIGHT / 2; // 高度
//		dialogWindow.setAttributes(lp);
//
//		mLittleToastDialog.setCanceledOnTouchOutside(true);
//		mLittleToastDialog.show();
//	}

	public static int getYpostion(View v) {
		int[] location = new int[2];
		v.getLocationOnScreen(location);
		int x = location[0];
		int y = location[1];
		return y;
	}

	public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        return statusBarHeight;
    }
	
	public static boolean checkApkInstalled(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	public static String formatMoney(int data) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(data);
	}

	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					LogUtil.i("后台", appProcess.processName);
					return true;
				}else{
					LogUtil.i("前台", appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}

	public static boolean isRunningForeground (Context context)
	{
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		String currentPackageName = cn.getPackageName();
		if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName()))
		{
			return true ;
		}

		return false ;
	}

	public static boolean isNotificationSwitchOpen(Context context) {
		if (Build.VERSION.SDK_INT == 19) {
			// 4.4版本才去查询开关状态
			return isNotificationEnabled(context);
		} else {
			return true;
		}
	}

	private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
	private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private static boolean isNotificationEnabled(Context context) {
		try {
			AppOpsManager mAppOps = (AppOpsManager) context
					.getSystemService(Context.APP_OPS_SERVICE);
			ApplicationInfo appInfo = context.getApplicationInfo();
			String pkg = context.getApplicationContext().getPackageName();
			int uid = appInfo.uid;
			Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
			try {
				appOpsClass = Class.forName(AppOpsManager.class.getName());
				Method checkOpNoThrowMethod = appOpsClass.getMethod(
						CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
						String.class);
				Field opPostNotificationValue = appOpsClass
						.getDeclaredField(OP_POST_NOTIFICATION);
				int value = Integer.parseInt(opPostNotificationValue.get(
						Integer.class).toString());
				return (Integer.parseInt(checkOpNoThrowMethod.invoke(mAppOps,
						value, uid, pkg).toString()) == AppOpsManager.MODE_ALLOWED);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			// 出现异常就当成是开启了
			return true;
		}

	}

	public static String getApplicationName(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext()
					.getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(
					context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		String applicationName = (String) packageManager
				.getApplicationLabel(applicationInfo);
		return applicationName;
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}
	
//	public static int countBadgeResult(Context context) {
//		int result = 0;
//		int badgeCount = 0;
//		String epxerNew = PreferenceUtil.getStringKey(context,
//				PreferenceUtil.EXPERT_NEW);
//		String userNew = PreferenceUtil.getStringKey(context,
//				PreferenceUtil.USER_NEW);
//		String experRun = PreferenceUtil.getStringKey(context,
//				PreferenceUtil.EXPERT_RUN);
//
//		if (!TextUtils.isEmpty(epxerNew)) {
//			result = result + Integer.parseInt(epxerNew);
//		}
//
//		if (!TextUtils.isEmpty(userNew)) {
//			result = result + Integer.parseInt(userNew);
//		}
//
//		if (!TextUtils.isEmpty(experRun)) {
//			result = result + Integer.parseInt(experRun);
//		}
//
//		String uid = PreferenceUtil.getStringKey(context, PreferenceKey.USER_UID);
//
//		if (!TextUtils.isEmpty(uid)) {
//			int chatCount = PreferenceUtil.getIntKey(
//					HjsApplication.getInstance(), uid);
//			badgeCount = result + chatCount;
//		}
//
//		return badgeCount;
//	}
}
