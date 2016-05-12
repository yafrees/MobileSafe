package com.yafrees.mobilesafe.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStateUtils {

	/**
	 * 校验某个服务是否是运行中
	 * 要校验服务的全类名
	 * 如果服务运行中，就返回true，否则返回false
	 * */
	public static boolean isRunningService( Context context , String serviceName){

		//ActivityManager
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> serviceInfos = am.getRunningServices(100);//允许同时运行的后台服务数量
		for(RunningServiceInfo service : serviceInfos){
			//得到全类名
			String name = service.service.getClassName();
			if (serviceName.equals(name)) {
				return true;
			}
		}

		return false;

	}

}
