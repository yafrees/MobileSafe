package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Setup4Activity extends BaseSetupActivity {

	private CheckBox cb_protect;

	/**
	 * 手机防盗的向导界面-----第四页
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		
		cb_protect = (CheckBox) findViewById(R.id.cb_protect);
		boolean protect = sp.getBoolean("protect", false);
		if (protect) {
			//手机防盗已开启
			cb_protect.setText("当前状态：手机防盗已经开启。");
		}
		else {
			//手机防盗已经关闭
			cb_protect.setText("当前状态：手机防盗未开启。");
		}
		cb_protect.setChecked(protect);
		cb_protect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
				Editor editor =sp.edit();
				editor.putBoolean("protect", isChecked);
				editor.commit();
				if (isChecked) {
					cb_protect.setText("当前状态：手机防盗已经开启。");
				}
				else {
					cb_protect.setText("当前状态：手机防盗未开启。");
				}
			}
		});
	}


	public void next(View view){
		Editor editor = sp.edit();
		editor.putBoolean("configed", true);
		editor.commit();
		Intent intent = new Intent(this , LostAndFind.class);
		startActivity(intent);
		finish();

	}

		//完成按钮的点击事件
	@Override
	public void showNext() {
//		Editor editor = sp.edit();
//		editor.putBoolean("configed", true);
//		editor.commit();
//		Intent intent = new Intent(this , LostAndFind.class);
//		startActivity(intent);
//		finish();		
	}

	//上一步按钮的点击事件
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();

		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}

}
