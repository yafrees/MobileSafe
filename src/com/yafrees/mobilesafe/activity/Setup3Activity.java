package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup3Activity extends BaseSetupActivity {

	/**
	 * 手机防盗的向导界面-----第三页
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}


	//下一步按钮的点击事件
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup4Activity.class);
		startActivity(intent);
		finish();

		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);

	}

	//上一步按钮的点击事件
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup2Activity.class);
		startActivity(intent);
		finish();

		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}


}
