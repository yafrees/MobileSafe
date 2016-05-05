package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.view.SettingItemView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

	private SettingItemView siv_update;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		setContentView(R.layout.activity_setting);

		siv_update = (SettingItemView) findViewById(R.id.siv_update);

		boolean update = sp.getBoolean("update", false);
		if (update) {
			//�Զ������Ѿ�����
			siv_update.setDescription("�Զ������Ѿ�����");
		}
		else {
			//�Զ������Ѿ��ر�
			siv_update.setDescription("�Զ������Ѿ��ر�");
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
					siv_update.setDescription("�Զ������Ѿ��ر�");
					editor.putBoolean("update", false);
				}
				else {
					//��Ϊ��ѡ״̬
					siv_update.setChecked(true);
					siv_update.setDescription("�Զ������Ѿ�����");
					editor.putBoolean("update", true);

				}
				editor.commit();

			}
		});
	}


}
