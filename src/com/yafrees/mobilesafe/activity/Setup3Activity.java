package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Setup3Activity extends BaseSetupActivity {
	
	private EditText et_safenumber;

	/**
	 * �ֻ��������򵼽���-----����ҳ
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		
		et_safenumber = (EditText) findViewById(R.id.et_safenumber);
		//����һ���������ҲҪ��֤������ʾ��EditText��
		et_safenumber.setText(sp.getString("safenumber", ""));
	}


	//*********************************************************
	//��һ����ť�ĵ���¼�
	@Override
	public void showNext() {
//		***********************************************************************
		//У���Ƿ������˰�ȫ���룬���û�����ò���������һ��
		String number = et_safenumber.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(getApplicationContext(), "�����ð�ȫ���룡", 0).show();
			return;
		}
		//���汻ѡ�еĺ���
		Editor editor =sp.edit();
		editor.putString("safenumber", number);
		editor.commit();
//		*******************************************************************************
		Intent intent = new Intent(this , Setup4Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);

	}

	//��һ����ť�ĵ���¼�
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup2Activity.class);
		startActivity(intent);
		finish();

		//�����򵼽����л��Ķ���
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}
	//**********************************************************************************
	
		//ѡ����ϵ�˰�ť�ĵ���¼���
		//������ϰ���б�
		public void selectContact(View view){
			Intent intent = new Intent(this , SelectContactActivity.class);
			startActivityForResult(intent, 0);
		}

		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);

			if (data == null) 
				return;
			
			String number = data.getStringExtra("number").replace("-", "");
			et_safenumber.setText(number);
			
		}

}
