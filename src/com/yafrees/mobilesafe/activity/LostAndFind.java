package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostAndFind extends Activity {

	private SharedPreferences sp ;
	private TextView tv_safenumber;
	private ImageView iv_safestatus;

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

			//�����ֻ����������״̬��Ϣ
			tv_safenumber = (TextView) findViewById(R.id.tv_safenumber);
			iv_safestatus = (ImageView) findViewById(R.id.iv_safestatus);
			tv_safenumber.setText(sp.getString("safenumber", ""));
			boolean protect = sp.getBoolean("protect", false);
			if (protect) {
				//���������Ѿ�����
				iv_safestatus.setBackgroundResource(R.drawable.lock);
			}
			else {
				//��������û�п���
				iv_safestatus.setBackgroundResource(R.drawable.unlock);
			}

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
