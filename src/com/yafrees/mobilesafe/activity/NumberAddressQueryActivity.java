package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.db.dao.NumberAddressQueryDao;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NumberAddressQueryActivity extends Activity{

	private EditText et_number;
	private TextView tv_result;
	
	//�𶯷���
	private Vibrator vibrator;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_number_address_query);

		et_number = (EditText) findViewById(R.id.et_number);
		tv_result = (TextView) findViewById(R.id.tv_result);
		
		vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		//�����ı������ָı��ʱ��ص�
		et_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (s == null) {
					tv_result.setText("");
				}
				if (s != null && s.length() >= 3) {
					String address = NumberAddressQueryDao.getAddress(s.toString());
					tv_result.setText(address);
				}

			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	//����¼�----��ѯ�绰���������
	public void query(View view){
		//1.�õ��绰����
		String number = et_number.getText().toString().trim();
		//2.�ж��Ƿ�Ϊ��
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(getApplicationContext(), "�绰���벻��Ϊ��", 0).show();
			//����Ϊ�յ�ʱ���ı��򶶶��Ķ���Ч��
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			et_number.startAnimation(shake);
			
			//�ֻ���Ч��
//			vibrator.vibrate(2000);//������
			
			long pattern [] = {500,500,1000,1000};
			vibrator.vibrate(pattern, -1);
			
		}
		else {
			//3.��ʼ��ѯ������----�����ѯ���߱������ݿ��ѯ
			String address = NumberAddressQueryDao.getAddress(number);
			tv_result.setText(address);

			System.out.println("��Ҫ��ѯ�ĵ绰���룺" + number);
		}

	}


}
