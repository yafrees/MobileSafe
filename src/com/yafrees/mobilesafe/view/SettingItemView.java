package com.yafrees.mobilesafe.view;

import com.yafrees.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemView extends RelativeLayout {

	private CheckBox cb_states;
	private TextView tv_desc;

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
	}

	//	******************************************************

	//�õ�checkBox�Ƿ�ѡ
	public boolean isChecked(){
		return cb_states.isChecked();
	}

	//���ù�ѡ״̬
	public void setChecked(boolean isChecked){
		cb_states.setChecked(isChecked);
	}

	//������Ͽؼ���״̬��Ϣ
	public void setDescription(String text){
		tv_desc.setText(text);
	}

}
