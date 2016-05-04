package com.yafrees.mobilesafe;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * ����ҳ��
 * 1.��ʾLogo
 * 2.�ж��Ƿ�������
 * 3.����
 * 4.�ж��Ƿ���sdcard
 * 5.�Ƿ�Ϸ�
 * 6.�ӳٽ���������
 * */

public class SplashActivity extends Activity {

	private TextView tv_splash_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);

		//��̬�õ��嵥�ļ��еİ汾�ţ�����ʾ������ҳ����
		tv_splash_version.setText("�汾����" + getVersionName());
	}

	/**
	 * �õ��汾����
	 * */
	private String getVersionName(){
		//��������
		PackageManager pm = getPackageManager();
		try {
			//�õ��嵥 �ļ���Ϣ
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
