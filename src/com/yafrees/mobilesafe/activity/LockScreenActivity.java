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

	//�豸���Թ���Ա----��һ������
	private DevicePolicyManager dpm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setContentView(R.layout.activity_main);

		dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		//		dpm.lockNow();//����
		openAdmin(null);
		finish();

	}

	//����¼�----һ������
	public void lockscreen(View view){
		ComponentName who = new ComponentName(this, MyAdmin.class);
		if (dpm.isAdminActive(who)) {
			dpm.lockNow();//����
			//dpm.resetPassword("1472", 0);//��������
			//dpm.wipeData(0);//�ָ��ɳ������� --- Զ��ɾ������
			//dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//SDCard��ʽ��
		}else {
			openAdmin(null);
		}
	}

	//һ�������豸����ԱȨ��
	public void openAdmin(View view){
		//������ͼ������豸����Ա
		Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
		//��������
		ComponentName mDeviceAdminSample = new ComponentName(this, MyAdmin.class);
		intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mDeviceAdminSample);
		//�����˵��
		intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "�����豸����ԱȨ�ޣ�����һ��������");
		startActivity(intent);
	}

	//���ж��
	public void uninstall(View view){
		//1.ʹ��ʧȥ����ԱȨ��
		ComponentName who = new ComponentName(this, MyAdmin.class);
		dpm.removeActiveAdmin(who);
		//2.���ж��
		Intent intent = new Intent();
		intent.setAction("android.intent.action.DELETE");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setData(Uri.parse("package:" + getPackageName()));
		startActivity(intent);
	}

}
