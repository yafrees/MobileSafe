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
			//自动升级已经开启
			siv_update.setDescription("自动升级已经开启");
		}
		else {
			//自动升级已经关闭
			siv_update.setDescription("自动升级已经关闭");
		}
		siv_update.setChecked(update);
		siv_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Editor editor = sp.edit();

				//得到是否勾选
				if (siv_update.isChecked()) {
					//变为非勾选
					siv_update.setChecked(false);
					siv_update.setDescription("自动升级已经关闭");
					editor.putBoolean("update", false);
				}
				else {
					//变为勾选状态
					siv_update.setChecked(true);
					siv_update.setDescription("自动升级已经开启");
					editor.putBoolean("update", true);

				}
				editor.commit();

			}
		});
	}


}
