package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Setup2Activity extends Activity {

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
	public void next(View view){
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();
		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);

	}

	//��һ����ť�ĵ���¼�
	public void previous(View view){
		Intent intent = new Intent(this , Setup1Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

}
