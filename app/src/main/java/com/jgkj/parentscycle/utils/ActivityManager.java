package com.jgkj.parentscycle.utils;

import java.util.Stack;

import android.app.Activity;
import android.widget.RadioButton;

import com.jgkj.parentscycle.activity.MainActivity;
import com.jgkj.parentscycle.activity.SplashActivity;


public class ActivityManager {
	private static Stack<Activity> activityStack;
	private static ActivityManager instance;
	private ActivityManager() {
	}

	public static ActivityManager getInstance() {
		if (instance == null) {
			synchronized(ActivityManager.class) {
				if (instance == null) {
					instance = new ActivityManager();
				}
			}
		}
		return instance;
	}

	public boolean appHadCreate() {
		if (activityStack == null || activityStack.size() == 0) {
			return false;
		}

		int count = 0;
		for (int i = 0 ; i < activityStack.size() ; i++) {
			Activity activity = activityStack.get(i);
			if (activity == null) {
				break;
			}

			if (activity instanceof SplashActivity) {
			} else {
				count++;
			}

			if (count > 0) {
				break;
			}
		}

		if (count > 0) {
			return true;
		} else {
			return  false;
		}
	}
	
	public boolean mainHadCreate() {
		if (activityStack == null || activityStack.size() == 0) {
			return false;
		}

		int count = 0;
		for (int i = 0 ; i < activityStack.size() ; i++) {
			Activity activity = activityStack.get(i);
			if (activity == null) {
				break;
			}

			if (activity instanceof MainActivity) {
			} else {
				count++;
			}

			if (count > 0) {
				break;
			}
		}

		if (count > 0) {
			return true;
		} else {
			return  false;
		}
	}
	
	
	public void popActivity(Activity activity) {
		if (activity != null) {
			activity.finish();
			activityStack.remove(activity);
			activity = null;
		}
	}

	
	public Activity currentActivity() {
		try{
			Activity activity = activityStack.lastElement();
			return activity;
		} catch (Exception e) {
			return null;
		}
	}




	
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	public Activity getLastActivity() {
		if (activityStack == null) {
			return null;
		}

		if (activityStack.size() == 0) {
			return null;
		}

		return activityStack.get(activityStack.size() - 1);
	}
	
	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			popActivity(activity);
		}
	}
	
	public void popAllActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			popActivity(activity);
		}
	}

	public void toHomeActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity instanceof MainActivity) {

				break;
			}

			if (activity == null) {
				break;
			}
			popActivity(activity);
		}

	}
	
	public void returnToMainActivity() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
//			if (activity instanceof MainActivity) {
//				break;
//			}
			popActivity(activity);
		}
	}
}