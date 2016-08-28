/* 
 * @ProjectName VideoGoJar
 * @Copyright HangZhou Hikvision System Technology Co.,Ltd. All Right Reserved
 * 
 * @FileName EzvizApplication.java
 * @Description 这里对文件进行描述
 * 
 * @author chenxingyf1
 * @data 2014-7-12
 * 
 * @note 这里写本文件的详细功能描述和注释
 * @note 历史记录
 * 
 * @warning 这里写本文件的相关警告
 */
package com.videogo;

import android.app.Application;
import android.util.Log;

import com.videogo.constant.Config;
import com.videogo.openapi.EZOpenSDK;

/**
 * 自定义应用
 * 
 * @author xiaxingsuo
 * 
 */
public class EzvizApplication extends Application {


	@Override
	public void onCreate() {
		super.onCreate();
        Log.d("result",this.getPackageName() + "->hahah");


		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                throwable.printStackTrace();
            }
        });
	}
}
