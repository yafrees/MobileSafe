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

	//������ʽ��ʱ��ʹ��
	public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//��ʼ��View
		initView(context);
	}

	//�ڲ����ļ�ʵ������ʱ��ʹ��
	public SettingItemView(Context context, AttributeSet attrs) {
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
	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}

	//**************************************************************

	//��ʼ�������ļ�
	private void initView(Context context) {
		//�Ѳ����ļ�ת��ΪView 
		//root�����˭��������R.layout.setting_item_view�����ļ��ĸ��࣬Ҳ���ǰѲ����ļ������ڴ������Ŀؼ���
		View.inflate(context, R.layout.setting_item_view, SettingItemView.this);

		cb_states = (CheckBox) findViewById(R.id.cb_states);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
		tv_title = (TextView) findViewById(R.id.tv_title);

	}

	//	******************************************************

	//�õ�checkBox�Ƿ�ѡ
	public boolean isChecked(){
		return cb_states.isChecked();
	}

	//���ù�ѡ״̬
	public void setChecked(boolean isChecked){
		cb_states.setChecked(isChecked);
		if (isChecked) {
			//�Զ���������
			setDescription(update_on);
		}
		else {
			//�Զ������Ѿ��ر�
			setDescription(update_off);
		}
	}

	//������Ͽؼ���״̬��Ϣ
	public void setDescription(String text){
		tv_desc.setText(text);
	}

}
