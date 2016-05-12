package com.yafrees.mobilesafe.service;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStateUtils {

	/**
	 * У��ĳ�������Ƿ���������
	 * ҪУ������ȫ����
	 * ������������У��ͷ���true�����򷵻�false
	 * */
	public static boolean isRunningService( Context context , String serviceName){

		//ActivityManager
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> serviceInfos = am.getRunningServices(100);//����ͬʱ���еĺ�̨��������
		for(RunningServiceInfo service : serviceInfos){
			//�õ�ȫ����
			String name = service.service.getClassName();
			if (serviceName.equals(name)) {
				return true;
			}
		}

		return false;

	}

}
