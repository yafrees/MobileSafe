package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class LostAndFind extends Activity {

	SharedPreferences sp ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("config", MODE_PRIVATE);

		super.onCreate(savedInstanceState);
		//判断是否进行过设置向导，如果没有就跳转到手机设置向导界面的第一个页面，
		//否则就加载手机防盗界面
		boolean configed = sp.getBoolean("configed", false);
		if (configed) {
			//如果配置过，加载手机防盗界面
			setContentView(R.layout.activity_lostandfind);
		}
		else {
			enterSetting();
		}

	}
//*****************************************************
	//方法的重构
	private void enterSetting() {
		//没有设置，跳转到手机防盗向导第一个界面
		Intent intent = new Intent();
		intent.setClass(this, Setup1Activity.class);
		startActivity(intent);
		//
		finish();
	}
	
//*****************************************************************
	//重新进入设置向导界面
	public void reEnterSetting(View v){
		enterSetting();
	}
	
//*****************************************************************
	
	
}
