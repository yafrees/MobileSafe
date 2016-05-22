package com.yafrees.mobilesafe.service;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.db.dao.NumberAddressQueryDao;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 监听来电
 * */

public class AddressService extends Service{

	//电话服务
	private TelephonyManager tm;
	
	private MyPhoneStateListener listener;

	private OutCallReceiver receiver;
	
	//窗口服务
	private WindowManager wm;
	
	private View view;
	
	//
	private SharedPreferences sp;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		
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
			myToast(address);
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
//				Toast.makeText(getApplicationContext(), address, 1).show();
				myToast(address);
				break;
			case TelephonyManager.CALL_STATE_IDLE://电话挂断了
				if (view != null) {
					wm.removeView(view);
					view = null;
				}
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
	
//************************************************************
	/**
	 * 自定义Toast（显示号码归属地）
	 * */
	public void myToast(String address) {
//		view = new TextView(this);
//		view.setTextSize(18);
//		view.setTextColor(Color.GREEN);
//		view.setText(address);
		
		view = View.inflate(this, R.layout.show_address, null);
		TextView tv = (TextView) view.findViewById(R.id.tv_address);
		tv.setText(address);
		
		int which_choice = sp.getInt("which", 0);
//		{"半透明" , "活力橙" , "卫士蓝" , "金属灰" , "苹果绿"};
		int ids [] = {R.drawable.call_locate_white , R.drawable.call_locate_orange , R.drawable.call_locate_blue , 
				R.drawable.call_locate_gray , R.drawable.call_locate_green};
		
		//得到DragViewActivity中保存的位置
		int lastX = sp.getInt("lastX", 0);
		int lastY = sp.getInt("lastY", 0);
		
		//根据设置中心的设置值，动态设置自定义Toast的背景
		view.setBackgroundResource(ids[which_choice]);
		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
		
		//码号归属地显示对话框显示在制定的位置
		params.gravity = Gravity.TOP + Gravity.LEFT;
		params.x = lastX;
		params.y = lastY;
		
		params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                /*| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE*/;
		
		wm.addView(view, params);
		
	}


}
