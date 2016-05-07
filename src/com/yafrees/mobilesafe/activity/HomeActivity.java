package com.yafrees.mobilesafe.activity;

/**
 * ��ҳ��
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

	//�û������ֻ��������������
	private SharedPreferences sp;

	//	private ImageView iv_icon;
	//	private TextView tv_name;

	private static final String names [] = {
			"�ֻ�����" , 
			"ͨѶ��ʿ" ,
			"Ӧ�ù���" ,
			"���̹���" ,
			"����ͳ��" ,
			"�ֻ�ɱ��" ,
			"��������" , 
			"�߼�����" , 
	"��������"};

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

		//����������
		list_home.setAdapter(new HomeAdapter());
		//����¼�
		list_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
				case 0://�����ֻ�����
					showLostFindDialog();
					break;

				case 8://������������
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
	
	//�����ֻ��������浯������Ի���
	//���ݵ�ǰ���������ͬ�ĶԻ���
	//�������Ѿ������򣬵�����¼�Ի���
	//����Ϊ���þ͵�����������Ի���
	protected void showLostFindDialog() {
		//�ж��Ƿ����������룬���û�������򵯳����öԻ��򣬷���͵�������Ի���
		if (isSetUpPwd()) {
			//�Ѿ����������룬���������
			showEnterDialog();
		}
		else {
			//û���������룬������������ĶԻ���
			showSetUpDialog();
		}

	}

	//��������ĶԻ���
	private void showEnterDialog() {

		AlertDialog.Builder builder = new Builder(HomeActivity.this);
		View view = View.inflate(HomeActivity.this, R.layout.dialog_enter_pwd, null);

		final EditText et_password = (EditText) view.findViewById(R.id.et_password);
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//1�õ�����������
				String password = et_password.getText().toString().trim();
				String password_save = sp.getString("password", "");//�Ѿ����ܵ�����

				//2�ж������Ƿ�Ϊ��
				if (TextUtils.isEmpty(password)) {
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
					return ;
				}

				//3�ж����������Ƿ���ͬ������ͬ��ʾ
				if (MD5Utils.ecoder(password).equals(password_save)) {
					//���ȷ�����Ի���ȡ���������ֻ���������
					dialog.dismiss();
					Log.e(TAG, "������ȷ�������ֻ�����ҳ��");
					Intent intent = new Intent(HomeActivity.this ,LostAndFind.class );
					startActivity(intent);
				}
				else {
					//
					et_password.setText("");
					Toast.makeText(HomeActivity.this, "����������벻��ȷ", 0).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�Ի���ȡ��
				dialog.dismiss();
			}
		});
		//�Ѳ����ļ�ת����View
		//builder.setView(view);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
	}


	//��������ĶԻ���
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
				//1�õ�����������
				String password = et_password.getText().toString().trim();
				String password_confirm = et_password_confirm.getText().toString().trim();

				//2�ж������Ƿ�Ϊ��
				if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password_confirm)) {
					Toast.makeText(HomeActivity.this, "���벻��Ϊ��", 0).show();
					return ;
				}

				//3�ж����������Ƿ���ͬ������ͬ��ʾ
				if (password.equals(password_confirm)) {
					//4�������룬�����ֻ�����ҳ��
					Editor editor = sp.edit();
					editor.putString("password", MD5Utils.ecoder(password));//�����Ϊ���ܺ������
					editor.commit();

					dialog.dismiss();
					Log.e(TAG, "�������룬�����ֻ�����ҳ��");
					Intent intent = new Intent(HomeActivity.this ,LostAndFind.class );
					startActivity(intent);
				}
				else {
					//
					et_password.setText("");
					et_password_confirm.setText("");
					Toast.makeText(HomeActivity.this, "������������벻һ��", 0).show();
				}
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//�Ի���ȡ��
				dialog.dismiss();
			}
		});

		//�Ѳ����ļ�ת����View
		//		builder.setView(view);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();

	}
	//�ж��Ƿ�����������
	private boolean isSetUpPwd(){
		String password = sp.getString("password", null);
		//		if (TextUtils.isEmpty(password)) {
		//			return false;
		//		}
		//		else {
		//			return true;
		//		}
		//���õ�ͬ��if...else...�����
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
			//�Ѳ����ļ�ת��ΪView
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
