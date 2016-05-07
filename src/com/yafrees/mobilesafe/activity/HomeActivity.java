package com.yafrees.mobilesafe.activity;

/**
 * 主页面
 * */

import com.yafrees.mobilesafe.R;
import com.yafrees.mobilesafe.utils.MD5Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.utils.Utils;

public class HomeActivity extends Activity {
	protected static final String TAG = "HomeActivity";

	private GridView list_home;

	//用户保存手机防盗界面的密码
	private SharedPreferences sp;

	//	private ImageView iv_icon;
	//	private TextView tv_name;

	private static final String names [] = {
			"手机防盗" , 
			"通讯卫士" ,
			"应用管理" ,
			"进程管理" ,
			"流量统计" ,
			"手机杀毒" ,
			"缓存清理" , 
			"高级工具" , 
	"设置中心"};

	private static final int ids [] = {
			R.drawable.home_safe,
			R.drawable.home_callmsgsafe,
			R.drawable.home_apps,
			R.drawable.home_taskmanager,
			R.drawable.home_netmanager,
			R.drawable.home_trojan,
			R.drawable.home_sysoptimize,
			R.drawable.home_tools,
			R.drawable.home_settings};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		list_home = (GridView) findViewById(R.id.list_home);

		//设置适配器
		list_home.setAdapter(new HomeAdapter());
		//点击事件
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0://进入手机防盗
					showLostFindDialog();
					break;

				case 8://进入设置中心
					Intent intent = new Intent(HomeActivity.this , SettingActivity.class);
					startActivity(intent);
					break;

				default:
					break;
				}
			}
		});

	}
	//**********************************************************************
	
	private AlertDialog dialog;
	
	//进入手机防盗界面弹出密码对话框
	//根据当前情况弹出不同的对话框。
	//当密码已经设置则，弹出登录对话框
	//密码为设置就弹出设置密码对话框
	protected void showLostFindDialog() {
		//判断是否设置了密码，如果没有设置则弹出设置对话框，否则就弹出输入对话框
		if (isSetUpPwd()) {
			//已经设置了密码，弹出输入框
			showEnterDialog();
		}
		else {
			//没有设置密码，弹出设置密码的对话框
			showSetUpDialog();
		}

	}

	//输入密码的对话框
	private void showEnterDialog() {

		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_pwd, null);

		final EditText et_password = (EditText) view.findViewById(R.id.et_password);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1得到输入框的密码
				String password = et_password.getText().toString().trim();
				String password_save = sp.getString("password", "");//已经加密的密文

				//2判断密码是否为空
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
					return ;
				}

				//3判断两个密码是否相同，不相同提示
				if (MD5Utils.ecoder(password).equals(password_save)) {
					//点击确定，对话框取消，进入手机防盗界面
					dialog.dismiss();
					Log.e(TAG, "密码正确，进入手机防盗页面");
					Intent intent = new Intent(HomeActivity.this ,LostAndFind.class );
					startActivity(intent);
				}
				else {
					//
					et_password.setText("");
					Toast.makeText(HomeActivity.this, "您输入的密码不正确", 0).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//对话框取消
				dialog.dismiss();
			}
		});
		//把布局文件转换成View
		//builder.setView(view);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}


	//设置密码的对话框
	private void showSetUpDialog() {
		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.dialog_setuppwd, null);

		final EditText et_password = (EditText) view.findViewById(R.id.et_password);
		final EditText et_password_confirm = (EditText) view.findViewById(R.id.et_password_confirm);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1得到输入框的密码
				String password = et_password.getText().toString().trim();
				String password_confirm = et_password_confirm.getText().toString().trim();

				//2判断密码是否为空
				if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
					Toast.makeText(HomeActivity.this, "密码不能为空", 0).show();
					return ;
				}

				//3判断两个密码是否相同，不相同提示
				if (password.equals(password_confirm)) {
					//4保存密码，进入手机防盗页面
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.ecoder(password));//保存的为加密后的密文
					editor.commit();

					dialog.dismiss();
					Log.e(TAG, "保存密码，进入手机防盗页面");
					Intent intent = new Intent(HomeActivity.this ,LostAndFind.class );
					startActivity(intent);
				}
				else {
					//
					et_password.setText("");
					et_password_confirm.setText("");
					Toast.makeText(HomeActivity.this, "两次输入的密码不一致", 0).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//对话框取消
				dialog.dismiss();
			}
		});

		//把布局文件转换成View
		//		builder.setView(view);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}
	//判断是否设置了密码
	private boolean isSetUpPwd(){
		String password = sp.getString("password", null);
		//		if (TextUtils.isEmpty(password)) {
		//			return false;
		//		}
		//		else {
		//			return true;
		//		}
		//作用等同与if...else...代码快
		return !TextUtils.isEmpty(password);
	}

	//	*******************************************************************
	private class HomeAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//把布局文件转化为View
			View view = View.inflate(HomeActivity.this, R.layout.home_item, null);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);

			tv_name.setText(names[position]);
			iv_icon.setImageResource(ids[position]);

			return view;
		}

	}

	//	***********************************************************************

}
