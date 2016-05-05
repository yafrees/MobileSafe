package com.yafrees.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义控件，实现主页面走字幕的效果
 * */

public class FocusedTextView extends TextView{

	//设置样式的时候
	public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	//在Android系统中，布局文件使用某个控件，默认会调用带有两个参数的构造方法
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	//代码实例化的时候用到
	public FocusedTextView(Context context) {
		super(context);
	}
	
	//当前控件不一定获得焦点，
	@Override
	public boolean isFocused() {
		return true;
	}



}
