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


	//�Զ������¼�----�����������ز�ѯҳ��
	public void numberAddressQuery(View view){
		Intent intent = new Intent(this , NumberAddressQueryActivity.class);
		startActivity(intent);
	}


}
