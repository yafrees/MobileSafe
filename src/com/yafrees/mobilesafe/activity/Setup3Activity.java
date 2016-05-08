package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup3Activity extends BaseSetupActivity {

	/**
	 * �ֻ��������򵼽���-----����ҳ
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
	}


	//��һ����ť�ĵ���¼�
	@Override
	public void showNext() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup4Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);

	}

	//��һ����ť�ĵ���¼�
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup2Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}


}
