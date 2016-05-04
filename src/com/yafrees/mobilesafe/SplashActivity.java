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
 * 启动页面
 * 1.显示Logo
 * 2.判断是否有网络
 * 3.升级
 * 4.判断是否有sdcard
 * 5.是否合法
 * 6.延迟进入主界面
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
	 * 升级的描述信息
	 * */
	private String description;

	/**
	 * 最新的apk的升级的地址
	 * */
	private String apkurl;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ENTER_HOME://进入主页面
				enterHome();
				break;
			case SHOW_UPDATE_DIALOG://弹出升级对话框
				Log.e(TAG, "有新版本，弹出升级对话框！");
				showUpdateDialog();
				break;
			case URL_ERROR://URL异常
				enterHome();
				Toast.makeText(SplashActivity.this, "URL异常", Toast.LENGTH_SHORT).show();
				break;
			case NETWORK_ERROR://网络异常
				enterHome();
				Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();

				break;
			case JSON_ERROR://json解析异常
				enterHome();
				Toast.makeText(getApplicationContext(), "JSON解析异常", Toast.LENGTH_SHORT).show();

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

		//动态得到清单文件中的版本号，并显示在启动页面的TextView中
		tv_splash_version.setText("版本名：" + getVersionName());


		//软件的升级
		checkVersion();

	}

	//	*******************************************************************************
	//弹出软件升级对话框
	protected void showUpdateDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("提示");
		builder.setMessage(description);
		builder.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			//跳到主页面
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				enterHome();
			}
		});
		builder.setPositiveButton("立刻升级", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//判断SDCard是否可用
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

					//下载APK----Final
					FinalHttp http = new FinalHttp();
					http.download(apkurl, Environment.getExternalStorageDirectory() + "/mobilesafe2.0.apk", new AjaxCallBack<File>() {

						@Override
						public void onFailure(Throwable t, int errorNo, String strMsg) {
							super.onFailure(t, errorNo, strMsg);
							t.printStackTrace();
							Toast.makeText(getApplicationContext(), "下载失败！", 0).show();
						}

						//下载到SDCard需要权限
						@Override
						public void onLoading(long count, long current) {
							super.onLoading(count, current);
							tv_splash_updateinfo.setVisibility(View.VISIBLE);
							int progress = (int) (current * 100 / count);
							tv_splash_updateinfo.setText("下载进度：" + progress + "%" );
						}

						@Override
						public void onSuccess(File t) {
							super.onSuccess(t);
//							Toast.makeText(getApplicationContext(), "下载成功...", 0).show();
							installAPK(t);
						}

						//安装Apk
						private void installAPK(File t) {
							// TODO Auto-generated method stub
							Intent intent = new Intent();
							intent.setAction("android.intent.action.VIEW");
							intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
							startActivity(intent);

						}

					});
					//替换安装
				}
				else {
					Toast.makeText(getApplicationContext(), "SDCard不可用", 0).show();
				}

			}
		});
		builder.show();
	}

	//	********************************************************
	//进入主页面
	protected void enterHome() {
		Intent intent = new Intent(SplashActivity.this , HomeActivity.class);
		startActivity(intent);
		//关闭启动页面
		finish();
	}
	//********************************************************************
	/**
	 * 校验是否有新版本，如果有就升级
	 * */
	private void checkVersion() {
		//升级的流程，
		new Thread(){
			public void run() {
				//请求网络得到我们最新的版本信息

				Message msg = Message.obtain();

				try {
					URL url = new URL(getString(R.string.serverurl));

					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");//设置请求方法
					con.setConnectTimeout(4000);//设置超时

					int code = con.getResponseCode();
					if (code == 200) {
						//200代表请求成功
						//把流转换成String类型
						InputStream is = con.getInputStream();
						String result = StreamTools.readFromStream(is);
						Log.e(TAG, "result:" + result);

						//解析Json
						JSONObject obj = new JSONObject(result);
						//服务器最新的版本
						String version = (String) obj.get("version");
						description = (String) obj.get("description");
						apkurl = (String) obj.get("apkurl");

						//比较正在运行的版本和服务器的版本是否一致，如果一致就没有新版本
						if (getVersionName().equals(version)) {
							//没有新版本---进入主页面
							msg.what = ENTER_HOME;
						}
						else {
							//版本名不一致，有新版本，弹出对话框，让用户进行选择
							msg.what = SHOW_UPDATE_DIALOG;
						}
					}
				} 
				catch (MalformedURLException e) {
					//URL错位			
					e.printStackTrace();
					msg.what = URL_ERROR;
				}
				catch (IOException e) {
					//网络异常
					e.printStackTrace();
					msg.what = NETWORK_ERROR;
				}
				catch (JSONException e) {
					//解析json异常
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
	 * 得到版本名称
	 * */
	private String getVersionName(){
		//包管理器
		PackageManager pm = getPackageManager();
		try {
			//得到清单 文件信息
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

}
