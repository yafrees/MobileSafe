package com.yafrees.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * �Զ���ؼ���ʵ����ҳ������Ļ��Ч��
 * */

public class FocusedTextView extends TextView{

	//������ʽ��ʱ��
	public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	//��Androidϵͳ�У������ļ�ʹ��ĳ���ؼ���Ĭ�ϻ���ô������������Ĺ��췽��
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//����ʵ������ʱ���õ�
	public FocusedTextView(Context context) {
		super(context);
	}
	
	//��ǰ�ؼ���һ����ý��㣬
	@Override
	public boolean isFocused() {
		return true;
	}



}
