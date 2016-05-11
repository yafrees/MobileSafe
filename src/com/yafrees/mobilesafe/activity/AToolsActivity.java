package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AToolsActivity extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
	}


	//自定义点击事件----进入号码归属地查询页面
	public void numberAddressQuery(View view){
		Intent intent = new Intent(this , NumberAddressQueryActivity.class);
		startActivity(intent);
	}


}
