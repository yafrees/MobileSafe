package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.service.AddressService;
import com.yafrees.mobilesafe.service.ServiceStateUtils;
import com.yafrees.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	//�����Զ����� 
	private SettingItemView siv_update ;
	//��������������Զ���ʾ
	private SettingItemView siv_show_address;

	private SharedPreferences sp;

	//�����������ͼ----�����ѯ���������
	private Intent addressIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		setContentView(R.layout.activity_setting);

		//�����Զ�����
		siv_update = (SettingItemView) findViewById(R.id.siv_update);

		boolean update = sp.getBoolean("update", false);
		if (update) {
			//�Զ������Ѿ�����
			//			siv_update.setDescription("�Զ������Ѿ�����");
		}
		else {
			//�Զ������Ѿ��ر�
			//			siv_update.setDescription("�Զ������Ѿ��ر�");
		}
		siv_update.setChecked(update);
		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();

				//�õ��Ƿ�ѡ
				if (siv_update.isChecked()) {
					//��Ϊ�ǹ�ѡ
					siv_update.setChecked(false);
					//					siv_update.setDescription("�Զ������Ѿ��ر�");
					editor.putBoolean("update", false);
				}
				else {
					//��Ϊ��ѡ״̬
					siv_update.setChecked(true);
					//					siv_update.setDescription("�Զ������Ѿ�����");
					editor.putBoolean("update", true);

				}
				editor.commit();

			}
		});

//************************************************************************
		//���ú���������Զ���ʾ
		siv_show_address = (SettingItemView) findViewById(R.id.siv_show_address);
		addressIntent = new Intent(this , AddressService.class);
		//У������Ƿ���
		boolean addressService = ServiceStateUtils.isRunningService(this, "com.yafrees.mobilesafe.service.AddressService");
		if (addressService) {
			siv_show_address.setChecked(true);
		}
		else {
			siv_show_address.setChecked(false);
		}

		//��ͬ��if...else...
//		siv_show_address.setChecked(addressService);

		siv_show_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (siv_show_address.isChecked()) {
					//��Ϊ�ǹ�ѡ
					siv_show_address.setChecked(false);
					//�ǹ�ѡ״̬ʱ���رշ���
					stopService(addressIntent);
				}
				else {
					//��ѡ
					siv_show_address.setChecked(true);
					//��Ϊ��ѡ״̬ʱ����������
					startService(addressIntent);
				}
			}
		});

	}


}
