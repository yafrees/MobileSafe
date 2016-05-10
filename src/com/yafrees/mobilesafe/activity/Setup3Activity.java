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
	 * 手机防盗的向导界面-----第三页
	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup3);
		
		et_safenumber = (EditText) findViewById(R.id.et_safenumber);
		//从下一个界面回来也要保证号码显示在EditText中
		et_safenumber.setText(sp.getString("safenumber", ""));
	}


	//*********************************************************
	//下一步按钮的点击事件
	@Override
	public void showNext() {
//		***********************************************************************
		//校验是否设置了安全号码，如果没有设置不允许点击下一步
		String number = et_safenumber.getText().toString().trim();
		if (TextUtils.isEmpty(number)) {
			Toast.makeText(getApplicationContext(), "请设置安全号码！", 0).show();
			return;
		}
		//保存被选中的号码
		Editor editor =sp.edit();
		editor.putString("safenumber", number);
		editor.commit();
//		*******************************************************************************
		Intent intent = new Intent(this , Setup4Activity.class);
		startActivity(intent);
		finish();

		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_next_in, R.anim.tran_next_out);

	}

	//上一步按钮的点击事件
	@Override
	public void showPre() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this , Setup2Activity.class);
		startActivity(intent);
		finish();

		//设置向导界面切换的动画
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);

	}
	//**********************************************************************************
	
		//选择联系人按钮的点击事件，
		//进入练习人列表
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
