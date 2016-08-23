package com.jgkj.parentscycle.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 完成对Gson数据的解析
 *
 * @author 王力杨
 *
 */
public class GsonUtil {
    public GsonUtil() {
    }

    /**
     * 对单个javabean进行解析
     *
     * @param jsonString
     *            json字符串
     * @param cls
     *            类
     * @return
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对单个javabean进行解析</br>
     * 调用方法：Type type = new TypeToken<List<ErrorInfo>>() {}.getType();
     *
     * @param jsonString
     *            json字符串
     * @param type
     *            类
     * @return
     */
    public static <T> T getObject(String jsonString, Type type) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    /**
     * 对list类型进行解析
     *

     */
    public static <T> List<T> getListObject(String jsonString, Type type) {
        List<T> list = new ArrayList<T>();

        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, type);

        } catch (Exception e) {

        }
        return list;
    }

    /**
     * 对list类型进行解析

     */
    public static <T> LinkedList<T> getLinkedListObject(String jsonString,
                                                        Type type) {
        LinkedList<T> list = new LinkedList<T>();

        try {

            Gson gson = new Gson();
            list = gson.fromJson(jsonString, type);

        } catch (Exception e) {

        }
        return list;
    }

    /**
     * 对list类型进行解析
     *
     * @param
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getListObjectByCls(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
            }.getType());

        } catch (Exception e) {

        }
        return list;
    }

    public static List<String> getList(String jsonString) {
        List<String> list = new ArrayList<String>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;

    }

    public static List<Map<String, Object>> listKeyMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

}
