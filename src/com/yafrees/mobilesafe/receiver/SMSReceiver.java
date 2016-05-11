package com.yafrees.mobilesafe.receiver;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.activity.LockScreenActivity;
import com.yafrees.mobilesafe.service.GPSService;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;

/**
 * 接收短信
 * */

public class SMSReceiver extends BroadcastReceiver {

	SharedPreferences sp;

	private DevicePolicyManager dpm;

	@Override
	public void onReceive(Context context, Intent intent) {
		sp = context.getSharedPreferences("config",Context.MODE_PRIVATE);

		dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

		Object [] pdus = (Object[]) intent.getExtras().get("pdus");
		for (Object pdu : pdus) {
			SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu);

			//得到发送者
			String sender = sms.getOriginatingAddress();
			String safenumber = sp.getString("safenumber", "");

			//得到短信内容
			String body = sms.getMessageBody();

			if (sender.contains(safenumber)) {
				if ("#location#".equals(body)) {
					//得到手机的Gps位置
					System.out.println("得到手机的Gps位置");
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
					//播放报警音乐
					MediaPlayer player = MediaPlayer.create(context, R.raw.the_sun_also_rise);
					player.setVolume(1.0f, 1.0f);
					player.setLooping(true);
					player.start();
					System.out.println("播放报警音乐");
					abortBroadcast();
				}
				else if ("#wipedata#".equals(body)) {
					//远程删除数据
					System.out.println("远程删除数据");
					ComponentName who = new ComponentName(context, MyAdmin.class);
					if (dpm.isAdminActive(who)) {
						dpm.wipeData(0);//远程删除数据
					}
					else {
						Intent openAdmin = new Intent(context , LockScreenActivity.class);
						//广播接收者不能通过意图直接启动活动，和服务。
						openAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(openAdmin);
					}
					abortBroadcast();
				}
				else if ("#lockscreen#".equals(body)) {
					//远程锁屏
					System.out.println("远程锁屏");
					ComponentName who = new ComponentName(context, MyAdmin.class);
					if (dpm.isAdminActive(who)) {
						dpm.lockNow();//远程锁屏
						dpm.resetPassword("1472s", 0);//设置锁屏密码
					}
					else {
						Intent openAdmin = new Intent(context , LockScreenActivity.class);
						//广播接收者不能通过意图直接启动活动，和服务。
						openAdmin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(openAdmin);
					}
					abortBroadcast();
				}
			}
		}

	}

}
