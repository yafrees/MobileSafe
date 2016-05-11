package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.receiver.MyAdmin;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class LockScreenActivity extends Activity {

	//设备策略管理员----是一个服务
	private DevicePolicyManager dpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_main);

		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		//		dpm.lockNow();//锁屏
		openAdmin(null);
		finish();

	}

	//点击事件----一键锁屏
	public void lockscreen(View view){
		ComponentName who = new ComponentName(this, MyAdmin.class);
		if (dpm.isAdminActive(who)) {
			dpm.lockNow();//锁屏
			//dpm.resetPassword("1472", 0);//设置密码
			//dpm.wipeData(0);//恢复成出厂设置 --- 远程删除数据
			//dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//SDCard格式化
		}else {
			openAdmin(null);
		}
	}

	//一键开启设备管理员权限
	public void openAdmin(View view){
		//定义意图。添加设备管理员
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		//激活的组件
		ComponentName mDeviceAdminSample = new ComponentName(this, MyAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
		//激活的说明
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "激活设备管理员权限，可以一键锁屏。");
		startActivity(intent);
	}

	//软件卸载
	public void uninstall(View view){
		//1.使其失去管理员权限
		ComponentName who = new ComponentName(this, MyAdmin.class);
		dpm.removeActiveAdmin(who);
		//2.完成卸载
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:" + getPackageName()));
		startActivity(intent);
	}

}
