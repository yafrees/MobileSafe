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
 * 启动页面
 * 1.显示Logo
 * 2.判断是否有网络
 * 3.升级
 * 4.判断是否有sdcard
 * 5.是否合法
 * 6.延迟进入主界面
 * */

public class SplashActivity extends Activity {

	private TextView tv_splash_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);

		//动态得到清单文件中的版本号，并显示在启动页面上
		tv_splash_version.setText("版本名：" + getVersionName());
	}

	/**
	 * 得到版本名称
	 * */
	private String getVersionName(){
		//包管理器
		PackageManager pm = getPackageManager();
		try {
			//得到清单 文件信息
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
