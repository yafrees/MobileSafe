package com.yafrees.mobilesafe.activity;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DragViewActivity extends Activity{

	private ImageView iv_drag_view;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_view);

		iv_drag_view = (ImageView) findViewById(R.id.iv_drag_view);
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
					break;
				case MotionEvent.ACTION_MOVE://手指在屏幕移动
					//2.来到一个新的坐标
					float newX = event.getRawX();
					float newY = event.getRawY();
					
					//3.计算偏移量
					int dX = (int) (newX - startX);
					int dY = (int) (newY - startY);
					
					//4.计算偏移量，更新控件的位置
					iv_drag_view.layout(iv_drag_view.getLeft() + dX, iv_drag_view.getTop() + dY,
							iv_drag_view.getRight() + dX, iv_drag_view.getBottom() + dY);
					
					//5.重新记录坐标
					startX = event.getRawX();
					startY = event.getRawY();

					break;

				case MotionEvent.ACTION_UP://手指离开屏幕

					break;

				default:
					break;
				}



				return true;
			}
		});
	}
}
