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
 * 监听来电
 * */

public class AddressService extends Service{

	//电话服务
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
		
		//注册监听去电-----广播接收者的动态注册
		receiver = new OutCallReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.NEW_OUTGOING_CALL");//监听去电的动作
		registerReceiver(receiver, filter);
		
	}
//*******************************************************************************
	//服务里面的内部类，---监听去电
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
		 * state：呼叫状态
		 * incomingNumber：来电号码
		 * */
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);

			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING://来电---铃声响起
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
		//取消监听来电
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);
		listener = null;
		
		//取消注册监听去电
		unregisterReceiver(receiver);
		receiver = null;
	}

}
