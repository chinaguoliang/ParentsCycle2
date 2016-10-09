package com.videogo;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by chen on 16/10/9.
 */
public class RequestAccessToken {
    private static final String TAG = "RequestAccessToken";
    public static final String GET_ACCESS_TOKEN = "https://open.ys7.com/api/lapp/token/get";
    public static final String APP_KEY = "8ff0d3e7aab5485195fd7ddcb0a33934";
    public static final String Secret = "6741c50a996dd8a185a2ceaf06f28be2";




    // Get方式请求
    public static void getAccessToken() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = GET_ACCESS_TOKEN + "?appKey=8ff0d3e7aab5485195fd7ddcb0a33934&appSecret=6741c50a996dd8a185a2ceaf06f28be2";
                    // 新建一个URL对象
                    URL url = new URL(path);
                    // 打开一个HttpURLConnection连接
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    // 设置连接超时时间
                    urlConn.setConnectTimeout(5 * 1000);
                    // 开始连接
                    urlConn.connect();
                    // 判断请求是否成功
                    if (urlConn.getResponseCode() == 200) {

                        // 获取返回的数据
                        byte[] data = readBytes3(urlConn.getInputStream());
                        Log.i(TAG, "Get方式请求成功，返回数据如下：");
                        Log.i(TAG, new String(data, "UTF-8"));
                    } else {
                        Log.i(TAG, "Get方式请求失败");
                    }
                    // 关闭连接
                    urlConn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }


    public static byte[] readBytes3(InputStream in) throws IOException {
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

        // System.out.println("Available bytes:" + in.available());

        byte[] temp = new byte[buffSize];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();
        return content;
    }
}
