package com.yafrees.mobilesafe.view;

import com.yafrees.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {
	private TextView tv_title;
	private CheckBox cb_states;
	private TextView tv_desc;

	private String update_off , update_on;

	//设置样式的时候使用
	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//初始化View
		initView(context);
	}

	//在布局文件实例化的时候使用
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yafrees.mobilesafe" , "title");
		update_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yafrees.mobilesafe" , "update_off");
		update_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yafrees.mobilesafe" , "update_on");

		tv_title.setText(title);
		//设置描述信息
		setDescription(update_off);

		//				System.out.println(attrs.getAttributeValue(0));
		//				System.out.println(attrs.getAttributeValue(1));
		//				System.out.println(attrs.getAttributeValue(2));
		//				System.out.println(attrs.getAttributeValue(3));
		//				System.out.println(attrs.getAttributeValue(4));
		//				System.out.println(attrs.getAttributeValue(5));


	}

	//在代码中实例化的时候使用
	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

	//**************************************************************

	//初始化布局文件
	private void initView(Context context) {
		//把布局文件转换为View 
		//root：添加谁进来就是R.layout.setting_item_view布局文件的父类，也就是把布局文件挂载在传进来的控件上
		View.inflate(context, R.layout.setting_item_view, SettingItemView.this);

		cb_states = (CheckBox) findViewById(R.id.cb_states);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_title = (TextView) findViewById(R.id.tv_title);

	}

	//	******************************************************

	//得到checkBox是否勾选
	public boolean isChecked(){
		return cb_states.isChecked();
	}

	//设置勾选状态
	public void setChecked(boolean isChecked){
		cb_states.setChecked(isChecked);
		if (isChecked) {
			//自动升级开启
			setDescription(update_on);
		}
		else {
			//自动升级已经关闭
			setDescription(update_off);
		}
	}

	//设置组合控件的状态信息
	public void setDescription(String text){
		tv_desc.setText(text);
	}

}
