package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostAndFind extends Activity {

	private SharedPreferences sp ;
	private TextView tv_safenumber;
	private ImageView iv_safestatus;

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

			//更新手机防盗界面的状态信息
			tv_safenumber = (TextView) findViewById(R.id.tv_safenumber);
			iv_safestatus = (ImageView) findViewById(R.id.iv_safestatus);
			tv_safenumber.setText(sp.getString("safenumber", ""));
			boolean protect = sp.getBoolean("protect", false);
			if (protect) {
				//防盗保护已经开启
				iv_safestatus.setBackgroundResource(R.drawable.lock);
			}
			else {
				//防盗保护没有开启
				iv_safestatus.setBackgroundResource(R.drawable.unlock);
			}

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
