package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.view.SettingItemView;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class Setup2Activity extends BaseSetupActivity {

	private SettingItemView siv_bind_sim;

	//电话服务，可以读取sim卡信息。监听来电，挂断等
	private TelephonyManager tm;

	/**
	 * 手机防盗的向导界面-----第二页
	 * 绑定SIM卡
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		tm =  (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

		setContentView(R.layout.activity_setup2);

		siv_bind_sim = (SettingItemView) findViewById(R.id.siv_bind_sim);
		String sim = sp.getString("sim", "");
		if (TextUtils.isEmpty(sim)) {
			//没有绑定sim卡
			siv_bind_sim.setChecked(false);
		}
		else {
			//已经绑定sim卡
			siv_bind_sim.setChecked(true);
		}
		siv_bind_sim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//基类中定义的SharedPreferences
				Editor editor = sp.edit();

				//判断复选框是否被选中
				if (siv_bind_sim.isChecked()) {
					siv_bind_sim.setChecked(false);
					editor.putString("sim", null);
				}
				else {
					siv_bind_sim.setChecked(true);

					//读取SIM卡的串号，需要权限
					String sim = tm.getSimSerialNumber();
					editor.putString("sim", sim);
				}
				editor.commit();
			}
		});

	}



	//********************************************************************
	//下一步按钮的点击事件
	@Override
	public void showNext() {
		//如果没有绑定sim卡，不能执行下一步操作
		String sim = sp.getString("sim", "");
		if (TextUtils.isEmpty(sim)) {
			Toast.makeText(getApplicationContext(), "没有绑定SIM卡，请绑定后点击下一步！", 0).show();
			return;
		}
		
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();
		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);		
	}

	//上一步按钮的点击事件
	@Override
	public void showPre() {
		Intent intent = new Intent(this , Setup1Activity.class);
		startActivity(intent);
		finish();
		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	//******************************************************************************

}
