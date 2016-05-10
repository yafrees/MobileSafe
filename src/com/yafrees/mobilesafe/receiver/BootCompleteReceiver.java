package com.yafrees.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * 监听开机广播
 * 用来比较sim卡是否变更
 * */

public class BootCompleteReceiver extends BroadcastReceiver{

	private SharedPreferences sp;

	private TelephonyManager tm;

	@Override
	public void onReceive(Context context, Intent intent) {
		//1.得到之前的sim卡信息
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String save_sim = sp.getString("sim", "");

		//2.得到当前手机的sim卡信息
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String currentSIM = tm.getSimSerialNumber();

		//3.比较sim卡信息是否一致
		if (save_sim.equals(currentSIM)) {
			//如果一致就什么都不用做
		}
		else {
			//如果不一致，就发送信息给安全号码
			Toast.makeText(context, "SIM变更...", 1).show();
		}

		//4.如果不一致就发短信给安全号码
	}

}
