package com.yafrees.mobilesafe.receiver;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.service.GPSService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

/**
 * ���ն���
 * */

public class SMSReceiver extends BroadcastReceiver {

	SharedPreferences sp;

	@Override
	public void onReceive(Context context, Intent intent) {
		sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);

		Object [] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);

			//�õ�������
			String sender = sms.getOriginatingAddress();
			String safenumber = sp.getString("safenumber", "");

			//�õ���������
			String body = sms.getMessageBody();

			if (sender.contains(safenumber)) {
				if ("#location#".equals(body)) {
					//�õ��ֻ���Gpsλ��
					System.out.println("�õ��ֻ���Gpsλ��");
					Intent gpsServiceIntent = new Intent(context , GPSService.class);
					context.startService(gpsServiceIntent);
					String lastlocation = sp.getString("lastlocation", "");
					if (TextUtils.isEmpty(lastlocation)) {
						SmsManager.getDefault().sendTextMessage(sender, null, "getting location...from yafree", null, null);
					}
					else {
						SmsManager.getDefault().sendTextMessage(sender, null, lastlocation , null, null);

					}
					abortBroadcast();
				}
				else if ("#alarm#".equals(body)) {
					//���ű�������
					MediaPlayer player = MediaPlayer.create(context, R.raw.the_sun_also_rise);
					player.setVolume(1.0f, 1.0f);
					player.setLooping(true);
					player.start();
					System.out.println("���ű�������");
					abortBroadcast();
				}
				else if ("#wipedata#".equals(body)) {
					//Զ��ɾ������
					System.out.println("Զ��ɾ������");
					abortBroadcast();
				}
				else if ("#lockscreen#".equals(body)) {
					//Զ������
					System.out.println("Զ������");
					abortBroadcast();
				}
			}
		}

	}

}
