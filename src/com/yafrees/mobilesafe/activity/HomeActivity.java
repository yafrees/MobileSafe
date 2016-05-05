package com.yafrees.mobilesafe.activity;

/**
 * 主页面
 * */

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends Activity {
	
	private GridView list_home;
	
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
		
		list_home = (GridView) findViewById(R.id.list_home);
		
		//设置适配器
		list_home.setAdapter(new HomeAdapter());
		
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
