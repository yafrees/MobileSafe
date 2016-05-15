package com.yafrees.mobilesafe.view;

import com.yafrees.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingClickView extends RelativeLayout {
	private TextView tv_title;
	private TextView tv_desc;

	private String update_off , update_on;

	//������ʽ��ʱ��ʹ��
	public SettingClickView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//��ʼ��View
		initView(context);
	}

	//�ڲ����ļ�ʵ������ʱ��ʹ��
	public SettingClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		String title = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yafrees.mobilesafe" , "title");
		update_off = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yafrees.mobilesafe" , "update_off");
		update_on = attrs.getAttributeValue("http://schemas.android.com/apk/res/com.yafrees.mobilesafe" , "update_on");

		tv_title.setText(title);
		//����������Ϣ
		setDescription(update_off);

		//				System.out.println(attrs.getAttributeValue(0));
		//				System.out.println(attrs.getAttributeValue(1));
		//				System.out.println(attrs.getAttributeValue(2));
		//				System.out.println(attrs.getAttributeValue(3));
		//				System.out.println(attrs.getAttributeValue(4));
		//				System.out.println(attrs.getAttributeValue(5));


	}

	//�ڴ�����ʵ������ʱ��ʹ��
	public SettingClickView(Context context) {
		super(context);
		initView(context);
	}

	//**************************************************************

	//��ʼ�������ļ�
	private void initView(Context context) {
		//�Ѳ����ļ�ת��ΪView 
		//root�����˭��������R.layout.setting_item_view�����ļ��ĸ��࣬Ҳ���ǰѲ����ļ������ڴ������Ŀؼ���
		View.inflate(context, R.layout.setting_click_view, this);

		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_title = (TextView) findViewById(R.id.tv_title);

	}
	
	//	******************************************************
	//������Ͽؼ���״̬��Ϣ
	public void setDescription(String text){
		tv_desc.setText(text);
	}
	
	//������Ͽؼ��ı���
	public void setTitle(String title){
		tv_title.setText(title);
	}

}
