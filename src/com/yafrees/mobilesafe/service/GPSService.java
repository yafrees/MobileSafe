package com.yafrees.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.TextView;

public class GPSService extends Service{

	/**
	 * 监听设备位置变化
	 * */

	//位置服务
	private LocationManager lm;

	private MyLocationListener listener;

	private SharedPreferences sp;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sp = getSharedPreferences("config", MODE_PRIVATE);

		lm = (LocationManager) getSystemService(LOCATION_SERVICE);

		//在服务中监听位置的变化
		listener = new MyLocationListener();
		//设置条件
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置精确度
		lm.getBestProvider(criteria , true);
		lm.requestLocationUpdates("gps", 0, 0, listener);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		//取消监听位置变化
		lm.removeUpdates(listener);
		listener = null;
	}

	//	**************************************************************
	private class MyLocationListener implements LocationListener{

		//当位置发生改变的时候回调
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			String longitude= "Longitude:" + location.getLongitude() + "\n";
			String latitude = "Latitude:" +  location.getLatitude() + "\n";
			String accuracy = "Accuracy:" +  location.getAccuracy() + "\n";

			//位置变化 --- > 发短信给安全号码
			Editor editor = sp.edit();
			editor.putString("lastlocation", longitude + latitude + accuracy);
			editor.commit();

		}

		//状态发生变化的时候回调
		//开启-->关闭
		//关闭-->开启
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		//当某个位置提供者可用的时候回调
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		//当某个位置提供者不可用的时候回调
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}



}
