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

	//�绰���񣬿��Զ�ȡsim����Ϣ���������磬�Ҷϵ�
	private TelephonyManager tm;

	/**
	 * �ֻ��������򵼽���-----�ڶ�ҳ
	 * ��SIM��
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
			//û�а�sim��
			siv_bind_sim.setChecked(false);
		}
		else {
			//�Ѿ���sim��
			siv_bind_sim.setChecked(true);
		}
		siv_bind_sim.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//�����ж����SharedPreferences
				Editor editor = sp.edit();

				//�жϸ�ѡ���Ƿ�ѡ��
				if (siv_bind_sim.isChecked()) {
					siv_bind_sim.setChecked(false);
					editor.putString("sim", null);
				}
				else {
					siv_bind_sim.setChecked(true);

					//��ȡSIM���Ĵ��ţ���ҪȨ��
					String sim = tm.getSimSerialNumber();
					editor.putString("sim", sim);
				}
				editor.commit();
			}
		});

	}



	//********************************************************************
	//��һ����ť�ĵ���¼�
	@Override
	public void showNext() {
		//���û�а�sim��������ִ����һ������
		String sim = sp.getString("sim", "");
		if (TextUtils.isEmpty(sim)) {
			Toast.makeText(getApplicationContext(), "û�а�SIM������󶨺�����һ����", 0).show();
			return;
		}
		
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
	//******************************************************************************

}
