package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.db.dao.NumberAddressQueryDao;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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

		//监听文本框文字改变的时候回调
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

	//点击事件----查询电话号码归属地
	public void query(View view){
		//1.得到电话号码
		String number = et_number.getText().toString().trim();
		//2.判断是否为空
		if (TextUtils.isEmpty(number)) {
			//
			Toast.makeText(getApplicationContext(), "电话号码不能为空", 0).show();
		}
		else {
			//3.开始查询归属地----网络查询或者本地数据库查询
			String address = NumberAddressQueryDao.getAddress(number);
			tv_result.setText(address);

			System.out.println("您要查询的电话号码：" + number);
		}

	}


}
