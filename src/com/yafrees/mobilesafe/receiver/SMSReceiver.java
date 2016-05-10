package com.yafrees.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.telephony.SmsMessage;

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
					abortBroadcast();
				}
				else if ("#alarm#".equals(body)) {
					//���ű�������
					System.out.println("���ű�������");
					abortBroadcast();
				}
				else if ("#wipedate#".equals(body)) {
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
