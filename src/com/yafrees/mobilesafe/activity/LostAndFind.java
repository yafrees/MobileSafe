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
		//�ж��Ƿ���й������򵼣����û�о���ת���ֻ������򵼽���ĵ�һ��ҳ�棬
		//����ͼ����ֻ���������
		boolean configed = sp.getBoolean("configed", false);
		if (configed) {
			//������ù��������ֻ���������
			setContentView(R.layout.activity_lostandfind);
		}
		else {
			enterSetting();
		}

	}
//*****************************************************
	//�������ع�
	private void enterSetting() {
		//û�����ã���ת���ֻ������򵼵�һ������
		Intent intent = new Intent();
		intent.setClass(this, Setup1Activity.class);
		startActivity(intent);
		//
		finish();
	}
	
//*****************************************************************
	//���½��������򵼽���
	public void reEnterSetting(View v){
		enterSetting();
	}
	
//*****************************************************************
	
	
}
