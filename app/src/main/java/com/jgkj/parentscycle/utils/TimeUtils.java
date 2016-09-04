package com.jgkj.parentscycle.utils;

import java.text.SimpleDateFormat;

/**
 * Created by chen on 16/9/4.
 */
public class TimeUtils {
    private static long ONE_DAY_LONG = 86400000l;

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//可以方便地修改日期格式
        long timeLone = System.currentTimeMillis();
        String resultTime = dateFormat.format(timeLone);
        return resultTime;
    }


//    public static String getBeforeTime) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
//        long timeLone = System.currentTimeMillis();
//
//        String resultTime = dateFormat.format(timeLone);
//        return resultTime;
//    }
//
//
//    public static String getAfterTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
//        long timeLone = System.currentTimeMillis();
//        String resultTime = dateFormat.format(timeLone);
//        return resultTime;
//    }
}
