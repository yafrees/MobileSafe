package com.yafrees.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * ���������㲥
 * �����Ƚ�sim���Ƿ���
 * */

public class BootCompleteReceiver extends BroadcastReceiver{

	private SharedPreferences sp;

	private TelephonyManager tm;

	@Override
	public void onReceive(Context context, Intent intent) {
		//1.�õ�֮ǰ��sim����Ϣ
		sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		if (sp.getBoolean("protect", false)) {

			String save_sim = sp.getString("sim", "");

			//2.�õ���ǰ�ֻ���sim����Ϣ
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String currentSIM = tm.getSimSerialNumber() + "yafree";

			//3.�Ƚ�sim����Ϣ�Ƿ�һ��
			if (save_sim.equals(currentSIM)) {
				//���һ�¾�ʲô��������
			}
			else {
				//4.�����һ�£��ͷ�����Ϣ����ȫ����
				System.out.println("SIM�������...");
				Toast.makeText(context, "SIM���...", 1).show();

				SmsManager.getDefault()
				.sendTextMessage(sp.getString("safenumber", ""), null, 
						"SIMCard changed from yafrees...", null, null);
			}
		}
	}
}
