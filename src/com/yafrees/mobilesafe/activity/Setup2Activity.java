package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup2Activity extends BaseSetupActivity {

	/**
	 * �ֻ��������򵼽���-----�ڶ�ҳ
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup2);
	}


	//��һ����ť�ĵ���¼�
	@Override
	public void showNext() {
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();
		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);		
	}

	//��һ����ť�ĵ���¼�
	@Override
	public void showPre() {
		Intent intent = new Intent(this , Setup1Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}

}
