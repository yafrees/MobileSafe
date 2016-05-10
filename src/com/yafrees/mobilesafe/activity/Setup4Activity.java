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
	 * �ֻ��������򵼽���-----����ҳ
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup4);
		
		cb_protect = (CheckBox) findViewById(R.id.cb_protect);
		boolean protect = sp.getBoolean("protect", false);
		if (protect) {
			//�ֻ������ѿ���
			cb_protect.setText("��ǰ״̬���ֻ������Ѿ�������");
		}
		else {
			//�ֻ������Ѿ��ر�
			cb_protect.setText("��ǰ״̬���ֻ�����δ������");
		}
		cb_protect.setChecked(protect);
		cb_protect.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			
				Editor editor =sp.edit();
				editor.putBoolean("protect", isChecked);
				editor.commit();
				if (isChecked) {
					cb_protect.setText("��ǰ״̬���ֻ������Ѿ�������");
				}
				else {
					cb_protect.setText("��ǰ״̬���ֻ�����δ������");
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

		//��ɰ�ť�ĵ���¼�
	@Override
	public void showNext() {
//		Editor editor = sp.edit();
//		editor.putBoolean("configed", true);
//		editor.commit();
//		Intent intent = new Intent(this , LostAndFind.class);
//		startActivity(intent);
//		finish();		
	}

	//��һ����ť�ĵ���¼�
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup3Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}

}
