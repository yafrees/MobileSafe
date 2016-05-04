package com.yafrees.mobilesafe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;

import com.yafrees.mobilesafe.activity.HomeActivity;
import com.yafrees.mobilesafe.utils.StreamTools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

/**
 * ����ҳ��
 * 1.��ʾLogo
 * 2.�ж��Ƿ�������
 * 3.����
 * 4.�ж��Ƿ���sdcard
 * 5.�Ƿ�Ϸ�
 * 6.�ӳٽ���������
 * */

public class SplashActivity extends Activity {

	protected static final String TAG = "SplashActivity";

	protected static final int ENTER_HOME = 1;
	protected static final int SHOW_UPDATE_DIALOG = 2;
	protected static final int URL_ERROR = 3;
	protected static final int NETWORK_ERROR = 4;
	protected static final int JSON_ERROR = 5;

	private TextView tv_splash_version;
	private TextView tv_splash_updateinfo;

	/**
	 * ������������Ϣ
	 * */
	private String description;

	/**
	 * ���µ�apk�������ĵ�ַ
	 * */
	private String apkurl;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ENTER_HOME://������ҳ��
				enterHome();
				break;
			case SHOW_UPDATE_DIALOG://���������Ի���
				Log.e(TAG, "���°汾�����������Ի���");
				showUpdateDialog();
				break;
			case URL_ERROR://URL�쳣
				enterHome();
				Toast.makeText(SplashActivity.this, "URL�쳣", Toast.LENGTH_SHORT).show();
				break;
			case NETWORK_ERROR://�����쳣
				enterHome();
				Toast.makeText(getApplicationContext(), "�����쳣", Toast.LENGTH_SHORT).show();

				break;
			case JSON_ERROR://json�����쳣
				enterHome();
				Toast.makeText(getApplicationContext(), "JSON�����쳣", Toast.LENGTH_SHORT).show();

				break;

			default:
				break;
			}

		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_updateinfo = (TextView) findViewById(R.id.tv_splash_updateinfo);

		//��̬�õ��嵥�ļ��еİ汾�ţ�����ʾ������ҳ���TextView��
		tv_splash_version.setText("�汾����" + getVersionName());


		//���������
		checkVersion();

	}

	//	*******************************************************************************
	//������������Ի���
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("��ʾ");
		builder.setMessage(description);
		builder.setNegativeButton("�´���˵", new DialogInterface.OnClickListener() {
			//������ҳ��
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enterHome();
			}
		});
		builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//�ж�SDCard�Ƿ����
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

					//����APK----Final
					FinalHttp http = new FinalHttp();
					http.download(apkurl, Environment.getExternalStorageDirectory() + "/mobilesafe2.0.apk", new AjaxCallBack<File>() {

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							super.onFailure(t, errorNo, strMsg);
							t.printStackTrace();
							Toast.makeText(getApplicationContext(), "����ʧ�ܣ�", 0).show();
						}

						//���ص�SDCard��ҪȨ��
						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
							tv_splash_updateinfo.setVisibility(View.VISIBLE);
							int progress = (int) (current * 100 / count);
							tv_splash_updateinfo.setText("���ؽ��ȣ�" + progress + "%" );
						}

						@Override
						public void onSuccess(File t) {
							super.onSuccess(t);
//							Toast.makeText(getApplicationContext(), "���سɹ�...", 0).show();
							installAPK(t);
						}

						//��װApk
						private void installAPK(File t) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
							startActivity(intent);

						}

					});
					//�滻��װ
				}
				else {
					Toast.makeText(getApplicationContext(), "SDCard������", 0).show();
				}

			}
		});
		builder.show();
	}

	//	********************************************************
	//������ҳ��
	protected void enterHome() {
		Intent intent = new Intent(SplashActivity.this , HomeActivity.class);
		startActivity(intent);
		//�ر�����ҳ��
		finish();
	}
	//********************************************************************
	/**
	 * У���Ƿ����°汾������о�����
	 * */
	private void checkVersion() {
		//���������̣�
		new Thread(){
			public void run() {
				//��������õ��������µİ汾��Ϣ

				Message msg = Message.obtain();

				try {
					URL url = new URL(getString(R.string.serverurl));

					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");//�������󷽷�
					con.setConnectTimeout(4000);//���ó�ʱ

					int code = con.getResponseCode();
					if (code == 200) {
						//200��������ɹ�
						//����ת����String����
						InputStream is = con.getInputStream();
						String result = StreamTools.readFromStream(is);
						Log.e(TAG, "result:" + result);

						//����Json
						JSONObject obj = new JSONObject(result);
						//���������µİ汾
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");

						//�Ƚ��������еİ汾�ͷ������İ汾�Ƿ�һ�£����һ�¾�û���°汾
						if (getVersionName().equals(version)) {
							//û���°汾---������ҳ��
							msg.what = ENTER_HOME;
						}
						else {
							//�汾����һ�£����°汾�������Ի������û�����ѡ��
							msg.what = SHOW_UPDATE_DIALOG;
						}
					}
				} 
				catch (MalformedURLException e) {
					//URL��λ			
					e.printStackTrace();
					msg.what = URL_ERROR;
				}
				catch (IOException e) {
					//�����쳣
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
				}
				catch (JSONException e) {
					//����json�쳣
					e.printStackTrace();
					msg.what = JSON_ERROR;
				}
				finally {
					handler.sendMessage(msg);
				}
			};
		}.start();
	}

	//********************************************************************************
	/**
	 * �õ��汾����
	 * */
	private String getVersionName(){
		//��������
		PackageManager pm = getPackageManager();
		try {
			//�õ��嵥 �ļ���Ϣ
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
