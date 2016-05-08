package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup2Activity extends BaseSetupActivity {

	/**
	 * 手机防盗的向导界面-----第二页
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
	}


	//下一步按钮的点击事件
	@Override
	public void showNext() {
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();
		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);		
	}

	//上一步按钮的点击事件
	@Override
	public void showPre() {
		Intent intent = new Intent(this , Setup1Activity.class);
		startActivity(intent);
		finish();

		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}

}
