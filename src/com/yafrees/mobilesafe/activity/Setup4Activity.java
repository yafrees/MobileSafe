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
	 * �ֻ��������򵼽���-----����ҳ
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_setup4);
	}

	//��ɰ�ť�ĵ���¼�
	public void next(View view){
		Editor editor = sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent = new Intent(this , LostAndFind.class);
		startActivity(intent);
		finish();
	}

	//��һ����ť�ĵ���¼�
	public void previous(View view){
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();
	}

}
