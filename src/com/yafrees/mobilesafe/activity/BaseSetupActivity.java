package com.yafrees.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 手势识别，调转下一个界面的基类
 * */

/**
 * 基类、父类、公共类
 * */

public abstract class BaseSetupActivity extends Activity {

	//1.定义手势识别器，实现设置向导界面的滑动切换
	private GestureDetector detector;
	
	//定义一个
	protected SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		

		//2.实例化手势识别器
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			//onFling，滑动
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

				//屏蔽误触(慢速滑动)，速度单位：像素每秒
				if (Math.abs(velocityX) < 100) {
					Toast.makeText(getApplicationContext(), "请快速的滑动...", 0).show();
					return true;
				}
				
				//屏蔽Y轴方向的滑动
				if (Math.abs(e2.getY() - e1.getY()) > 100) {
					Toast.makeText(getApplicationContext(), "请在水平方向上滑动...", 0).show();
					return true;
				}
				
				if (e2.getX() - e1.getX() > 200) {
					//显示上一个页面
					System.out.println("显示上一个页面");
					showPre();
					return true;
				}
				if (e1.getX() - e2.getX() > 200) {
					//显示下一个页面
					System.out.println("显示下一个页面");
					showNext();
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}

		});

	}

	//3.使用手势识别器
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
//	***********************************************************

	//显示下一个页面的抽象方法
	public abstract void showNext();
	//显示上一个页面的抽象方法
	public abstract void showPre();
	
//	*******************************************************

	//下一步按钮的点击事件
	public void next(View view){
		showNext();
	}
	//上一步按钮的点击事件
	public void previous(View view){
		showPre();
	}




}
