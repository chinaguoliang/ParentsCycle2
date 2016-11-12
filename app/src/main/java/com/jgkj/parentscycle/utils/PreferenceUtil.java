package com.jgkj.parentscycle.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	public static final String LOCATION_INFO = "location_info_key";

	private static SharedPreferences mPreference;
	private static void init(Context mContext){
		mPreference = mContext.getSharedPreferences("BabyCyclePreference", Activity.MODE_PRIVATE);
	}

	public static void setStringKey(Context context,String key ,String value){
		if(mPreference == null){
			init(context);
		}
		mPreference.edit().putString(key, value).commit();
	}
	
	public static String getStringKey(Context context,String key){
		if(mPreference == null){
			init(context);
		}
		return mPreference.getString(key, "");
	}
	
	public static void setBooleanKey(Context context,String key ,boolean value){
		if(mPreference == null){
			init(context);
		}
		mPreference.edit().putBoolean(key, value).commit();
	}
	
	
	public static void setIntKey(Context context,String key ,int value){
		if(mPreference == null){
			init(context);
		}
		mPreference.edit().putInt(key, value).commit();
	}
	
	
	public static int getIntKey(Context context,String key){
		if(mPreference == null){
			init(context);
		}
		return mPreference.getInt(key, 0);
	}
	
	public static boolean getBooleanKey(Context context,String key){
		if(mPreference == null){
			init(context);
		}
		return mPreference.getBoolean(key, false);
	}
	
}
