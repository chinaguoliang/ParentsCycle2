package com.jgkj.parentscycle.application;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.easemob.redpacketsdk.RedPacket;
import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.chatuidemo.DemoHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.videogo.constant.Config;
import com.videogo.openapi.EZOpenSDK;

/**
 * Created by chen on 16/7/5.
 */
public class MyApplication extends Application {
    // 开放平台申请的APP key & secret key
    // open
    public static String YING_SHI_APP_KEY = "8ff0d3e7aab5485195fd7ddcb0a33934"; //荧石appkey

    private static MyApplication instance;
    public static MyApplication getInstance() {
        return instance;
    }
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();

        DemoApplication.applicationContext = this;
        DemoHelper.getInstance().init(this);
        //red packet code : 初始化红包上下文，开启日志输出开关
        RedPacket.getInstance().initContext(this);
        RedPacket.getInstance().setDebugMode(true);
        initYingShiVideo();
        instance = this;
        initImageLoader();
    }

    private void initYingShiVideo() {
        Config.LOGGING = true;
        EZOpenSDK.initLib(this, YING_SHI_APP_KEY, "");
    }

    private void initImageLoader() {


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(2000, 2000)
                        // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(1)
                        // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                        // You can pass your own memory cache
                        // implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                        // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)

                        // 缓存的文件数量
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
                .writeDebugLogs() // Remove for release app
                .build();// 开始构建
        ImageLoader.getInstance().init(config);// 全局初始化此配置
    }
}
