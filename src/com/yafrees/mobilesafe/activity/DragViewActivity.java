package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class DragViewActivity extends Activity{

	protected static final String TAG = "DragViewActivity";
	
	private SharedPreferences sp;
	
	private ImageView iv_drag_view;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_drag_view);

		iv_drag_view = (ImageView) findViewById(R.id.iv_drag_view);
		
		//取得已经保存的左上角的坐标位置，并移动到保存的位置
		int lastX = sp.getInt("lastX", 0);
		int lastY = sp.getInt("lastY", 0);
		Log.e(TAG, "取出保存的值(" + lastX + "," + lastY + ")");
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

					//4.计算偏移量，更新控件的位置
					iv_drag_view.layout(iv_drag_view.getLeft() + dX, iv_drag_view.getTop() + dY,
							iv_drag_view.getRight() + dX, iv_drag_view.getBottom() + dY);
					
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
}
