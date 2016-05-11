package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.db.dao.NumberAddressQueryDao;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberAddressQueryActivity extends Activity{

	private EditText et_number;
	private TextView tv_result;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);

		et_number = (EditText) findViewById(R.id.et_number);
		tv_result = (TextView) findViewById(R.id.tv_result);
	}

	//����¼�----��ѯ�绰���������
	public void query(View view){
		//1.�õ��绰����
		String number = et_number.getText().toString().trim();
		//2.�ж��Ƿ�Ϊ��
		if (TextUtils.isEmpty(number)) {
			//
			Toast.makeText(getApplicationContext(), "�绰���벻��Ϊ��", 0).show();
		}
		else {
			//3.��ʼ��ѯ������----�����ѯ���߱������ݿ��ѯ
			String address = NumberAddressQueryDao.getAddress(number);
			tv_result.setText(address);
			
			System.out.println("��Ҫ��ѯ�ĵ绰���룺" + number);
		}

	}


}
