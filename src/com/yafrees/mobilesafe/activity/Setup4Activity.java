package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class Setup4Activity extends Activity {

	private SharedPreferences sp;
	
	/**
	 * 手机防盗的向导界面-----第四页
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_setup4);
	}

	//完成按钮的点击事件
	public void next(View view){
		Editor editor = sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent = new Intent(this , LostAndFind.class);
		startActivity(intent);
		finish();
	}

	//上一步按钮的点击事件
	public void previous(View view){
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();
	}

}
