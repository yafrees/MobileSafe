package com.yafrees.mobilesafe.service;

import com.yafrees.mobilesafe.db.dao.NumberAddressQueryDao;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * ��������
 * */

public class AddressService extends Service{

	//�绰����
	private TelephonyManager tm;
	
	private MyPhoneStateListener listener;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		
		listener = new MyPhoneStateListener();
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	private class MyPhoneStateListener extends PhoneStateListener{

		/**
		 * state������״̬
		 * incomingNumber���������
		 * */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://����---��������
				String address = NumberAddressQueryDao.getAddress(incomingNumber);
				Toast.makeText(getApplicationContext(), address, 1).show();
				break;

			default:
				break;
			}
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		//ȡ����������
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
	}

}
