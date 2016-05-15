package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.service.AddressService;
import com.yafrees.mobilesafe.service.ServiceStateUtils;
import com.yafrees.mobilesafe.view.SettingClickView;
import com.yafrees.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
	
	//设置自定义Toast(号码归属地显示框)的背景颜色
	private SettingClickView scv_change_bg;
	
	//设置号码归属地显示框的位置
	private SettingClickView scv_change_position;
	

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
		
//**************************************************************************
		//设置自定义Toast的背景颜色
		scv_change_bg = (SettingClickView) findViewById(R.id.scv_change_bg);
		final String items [] = {"半透明" , "活力橙" , "卫士蓝" , "金属灰" , "苹果绿"};
		int which = sp.getInt("which", 0);
		scv_change_bg.setDescription(items[which]);
		scv_change_bg.setTitle("归属地提示框风格");
		scv_change_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int which_choice = sp.getInt("which", 0);
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				builder.setTitle("归属地提示框风格");
				builder.setSingleChoiceItems(items, which_choice, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//保存起来
						Editor editor = sp.edit();
						editor.putInt("which", which);
						editor.commit();
						//设置描述信息
						scv_change_bg.setDescription(items[which]);
						
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消", null);
				builder.show();
			}
		});
		
//**********************************************************************
		//设置号码归属地显示框的位置
		scv_change_position = (SettingClickView) findViewById(R.id.scv_change_position);
		scv_change_position.setTitle("归属地提示框位置");
		scv_change_position.setDescription("设置归属地提示框显示位置");
		scv_change_position.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//跳转到拖动的Activity
				Intent intent = new Intent(SettingActivity.this , DragViewActivity.class);
				startActivity(intent);
			}
		});

	}
//*****************************************************************
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//校验服务是否开启
		boolean addressService = ServiceStateUtils.isRunningService(this, "com.yafrees.mobilesafe.service.AddressService");
		if (addressService) {
			siv_show_address.setChecked(true);
		}
		else {
			siv_show_address.setChecked(false);
		}

		//等同于if...else...
		//siv_show_address.setChecked(addressService);
	}


}
