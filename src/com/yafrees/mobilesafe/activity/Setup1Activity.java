package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class Setup1Activity extends BaseSetupActivity {

	/**
	 * 手机防盗的向导界面------第一页
	 * */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);

	}
	

	@Override
	public void showNext() {
		Intent intent = new Intent(this , Setup2Activity.class);
		startActivity(intent);
		finish();
		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);
		
	}

	@Override
	public void showPre() {
		
	}


}
