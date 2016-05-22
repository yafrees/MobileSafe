package com.yafrees.mobilesafe.activity;

import java.lang.reflect.Field;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class DragViewActivity extends Activity{

	protected static final String TAG = "DragViewActivity";
	
	private SharedPreferences sp;
	
	private ImageView iv_drag_view;
	
	//得到屏幕的宽度
	private WindowManager wm;
	private int mWidth;
	private int mHeight;
	
	private TextView tv_bottom , tv_top;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//获取屏幕的高和宽
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		mWidth = wm.getDefaultDisplay().getWidth();
		mHeight = wm.getDefaultDisplay().getHeight();
		
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_drag_view);

		iv_drag_view = (ImageView) findViewById(R.id.iv_drag_view);
		
		tv_top = (TextView) findViewById(R.id.tv_top);
		tv_bottom = (TextView) findViewById(R.id.tv_bottom);
		
		//取得已经保存的左上角的坐标位置，并移动到保存的位置
		int lastX = sp.getInt("lastX", 0);
		int lastY = sp.getInt("lastY", 0);
		Log.e(TAG, "取出保存的值(" + lastX + "," + lastY + ")");
//		*****************************************************************
		//当控件被拖动到屏幕上半部分的时候，顶部文本框隐藏，底部文本框显示
		if (lastY > mHeight / 2) {
			//当前控件在下半部分
			tv_top.setVisibility(View.VISIBLE);
			tv_bottom.setVisibility(View.INVISIBLE);
		}
		else {
			//当前控件在上半部分
			tv_top.setVisibility(View.INVISIBLE);
			tv_bottom.setVisibility(View.VISIBLE);
		}
//	***************************************************************	
		
//		iv_drag_view.layout(lastX, lastY,
//				lastX + iv_drag_view.getWidth(), lastY + iv_drag_view.getHeight());
		Log.e(TAG, "可移动控件的宽和高(" + iv_drag_view.getWidth() + "," + iv_drag_view.getHeight() + ")");
		
		LayoutParams params = (LayoutParams) iv_drag_view.getLayoutParams();
		params.leftMargin = lastX;
		params.topMargin = lastY;
		iv_drag_view.setLayoutParams(params);
		
		//设置触摸事件
		iv_drag_view.setOnTouchListener(new OnTouchListener() {
			
			float startX = 0;
			float startY = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://手指按下屏幕
					//1.记录手指第一次按下的坐标
					startX = event.getRawX();
					startY = event.getRawY();
					Log.e(TAG, "第一次按下的坐标(" + startX + "," + startY + ")");
					break;
				case MotionEvent.ACTION_MOVE://手指在屏幕移动
					//2.来到一个新的坐标
					float newX = event.getRawX();
					float newY = event.getRawY();
					
					//3.计算偏移量
					int dX = (int) (newX - startX);
					int dY = (int) (newY - startY);
					Log.e(TAG, "移动的偏移量(" + dX + "," + dY + ")");

//	******************************************************************				
					//屏蔽非法拖动
					int newL = iv_drag_view.getLeft() + dX;
					int newT = iv_drag_view.getTop() + dY;
					int newR = iv_drag_view.getRight() + dX;
					int newB = iv_drag_view.getBottom() + dY;
					if (newL < 0 || newT < 0 || newR > mWidth || newB > mHeight - getStatusBarHeight()) {
						break;
					}
					
//	*******************************************************************
					//当控件被拖动到屏幕上半部分的时候，顶部文本框隐藏，底部文本框显示
					if (newT > mHeight / 2) {
						//当前控件在下半部分
						tv_top.setVisibility(View.VISIBLE);
						tv_bottom.setVisibility(View.INVISIBLE);
					}
					else {
						//当前控件在上半部分
						tv_top.setVisibility(View.INVISIBLE);
						tv_bottom.setVisibility(View.VISIBLE);
					}
//**************************************************************************
					//4.计算偏移量，更新控件的位置
					iv_drag_view.layout(newL , newT , newR , newB);
					
					//5.重新记录坐标
					startX = event.getRawX();
					startY = event.getRawY();

					break;

				case MotionEvent.ACTION_UP://手指离开屏幕
					Log.e(TAG, "手指离开屏幕");
					Editor editor = sp.edit();
					//保存左上角的坐标位置
					editor.putInt("lastX", iv_drag_view.getLeft());
					editor.putInt("lastY", iv_drag_view.getTop());
					editor.commit();

					Log.e(TAG, "保存的坐标(" + iv_drag_view.getLeft() + "," + iv_drag_view.getTop() + ")");
					break;

				default:
					break;
				}

				return true;
			}
		});
	}
	
//**************************************************************
	//获取状态栏的高度
	private int statusBarHeight;
	private int getStatusBarHeight(){
		if (statusBarHeight == 0) {
			try {
				Class c = Class.forName("com.android.internal.R$dimen");
				Object o = c.newInstance();
				Field field = c.getField("status_bar_height");
				int x = (Integer) field.get(o);
				statusBarHeight = getResources().getDimensionPixelSize(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return statusBarHeight;
	}
	
	
}
