package com.yafrees.mobilesafe.service;

import com.yafrees.mobilesafe.db.dao.NumberAddressQueryDao;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

	private OutCallReceiver receiver;
	
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
		
		//ע�����ȥ��-----�㲥�����ߵĶ�̬ע��
		receiver = new OutCallReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");//����ȥ��Ķ���
		registerReceiver(receiver, filter);
		
	}
//*******************************************************************************
	//����������ڲ��࣬---����ȥ��
	public class OutCallReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String number = getResultData();
			String address = NumberAddressQueryDao.getAddress(number);
			Toast.makeText(context, address, Toast.LENGTH_LONG).show();
		}

	}
//**************************************************************************************

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
		
		//ȡ��ע�����ȥ��
		unregisterReceiver(receiver);
		receiver = null;
	}

}
