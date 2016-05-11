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
	 * �����豸λ�ñ仯
	 * */

	//λ�÷���
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

		//�ڷ����м���λ�õı仯
		listener = new MyLocationListener();
		//��������
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//���þ�ȷ��
		lm.getBestProvider(criteria , true);
		lm.requestLocationUpdates("gps", 0, 0, listener);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		//ȡ������λ�ñ仯
		lm.removeUpdates(listener);
		listener = null;
	}

	//	**************************************************************
	private class MyLocationListener implements LocationListener{

		//��λ�÷����ı��ʱ��ص�
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			String longitude= "Longitude:" + location.getLongitude() + "\n";
			String latitude = "Latitude:" +  location.getLatitude() + "\n";
			String accuracy = "Accuracy:" +  location.getAccuracy() + "\n";

			//λ�ñ仯 --- > �����Ÿ���ȫ����
			Editor editor = sp.edit();
			editor.putString("lastlocation", longitude + latitude + accuracy);
			editor.commit();

		}

		//״̬�����仯��ʱ��ص�
		//����-->�ر�
		//�ر�-->����
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		//��ĳ��λ���ṩ�߿��õ�ʱ��ص�
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		//��ĳ��λ���ṩ�߲����õ�ʱ��ص�
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

	}



}
