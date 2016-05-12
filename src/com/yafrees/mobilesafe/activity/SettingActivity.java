package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.service.AddressService;
import com.yafrees.mobilesafe.service.ServiceStateUtils;
import com.yafrees.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	//设置自动更新 
	private SettingItemView siv_update ;
	//设置来电归属地自动显示
	private SettingItemView siv_show_address;

	private SharedPreferences sp;

	//开启服务的意图----来电查询号码归属地
	private Intent addressIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		setContentView(R.layout.activity_setting);

		//设置自动更新
		siv_update = (SettingItemView) findViewById(R.id.siv_update);

		boolean update = sp.getBoolean("update", false);
		if (update) {
			//自动升级已经开启
			//			siv_update.setDescription("自动升级已经开启");
		}
		else {
			//自动升级已经关闭
			//			siv_update.setDescription("自动升级已经关闭");
		}
		siv_update.setChecked(update);
		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();

				//得到是否勾选
				if (siv_update.isChecked()) {
					//变为非勾选
					siv_update.setChecked(false);
					//					siv_update.setDescription("自动升级已经关闭");
					editor.putBoolean("update", false);
				}
				else {
					//变为勾选状态
					siv_update.setChecked(true);
					//					siv_update.setDescription("自动升级已经开启");
					editor.putBoolean("update", true);

				}
				editor.commit();

			}
		});

//************************************************************************
		//设置号码归属地自动显示
		siv_show_address = (SettingItemView) findViewById(R.id.siv_show_address);
		addressIntent = new Intent(this , AddressService.class);
		//校验服务是否开启
		boolean addressService = ServiceStateUtils.isRunningService(this, "com.yafrees.mobilesafe.service.AddressService");
		if (addressService) {
			siv_show_address.setChecked(true);
		}
		else {
			siv_show_address.setChecked(false);
		}

		//等同于if...else...
//		siv_show_address.setChecked(addressService);

		siv_show_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_show_address.isChecked()) {
					//变为非勾选
					siv_show_address.setChecked(false);
					//非勾选状态时，关闭服务
					stopService(addressIntent);
				}
				else {
					//勾选
					siv_show_address.setChecked(true);
					//当为勾选状态时，开启服务
					startService(addressIntent);
				}
			}
		});

	}


}
