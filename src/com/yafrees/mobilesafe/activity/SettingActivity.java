package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.service.AddressService;
import com.yafrees.mobilesafe.service.ServiceStateUtils;
import com.yafrees.mobilesafe.view.SettingClickView;
import com.yafrees.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
	
	//�����Զ���Toast(�����������ʾ��)�ı�����ɫ
	private SettingClickView scv_change_bg;
	
	//���ú����������ʾ���λ��
	private SettingClickView scv_change_position;
	

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
		
//**************************************************************************
		//�����Զ���Toast�ı�����ɫ
		scv_change_bg = (SettingClickView) findViewById(R.id.scv_change_bg);
		final String items [] = {"��͸��" , "������" , "��ʿ��" , "������" , "ƻ����"};
		int which = sp.getInt("which", 0);
		scv_change_bg.setDescription(items[which]);
		scv_change_bg.setTitle("��������ʾ����");
		scv_change_bg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int which_choice = sp.getInt("which", 0);
				AlertDialog.Builder builder = new Builder(SettingActivity.this);
				builder.setTitle("��������ʾ����");
				builder.setSingleChoiceItems(items, which_choice, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//��������
						Editor editor = sp.edit();
						editor.putInt("which", which);
						editor.commit();
						//����������Ϣ
						scv_change_bg.setDescription(items[which]);
						
						dialog.dismiss();
					}
				});
				builder.setNegativeButton("ȡ��", null);
				builder.show();
			}
		});
		
//**********************************************************************
		//���ú����������ʾ���λ��
		scv_change_position = (SettingClickView) findViewById(R.id.scv_change_position);
		scv_change_position.setTitle("��������ʾ��λ��");
		scv_change_position.setDescription("���ù�������ʾ����ʾλ��");
		scv_change_position.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//��ת���϶���Activity
				Intent intent = new Intent(SettingActivity.this , DragViewActivity.class);
				startActivity(intent);
			}
		});

	}
//*****************************************************************
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		//У������Ƿ���
		boolean addressService = ServiceStateUtils.isRunningService(this, "com.yafrees.mobilesafe.service.AddressService");
		if (addressService) {
			siv_show_address.setChecked(true);
		}
		else {
			siv_show_address.setChecked(false);
		}

		//��ͬ��if...else...
		//siv_show_address.setChecked(addressService);
	}


}
