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
		//���ô����¼�
		iv_drag_view.setOnTouchListener(new OnTouchListener() {
			
			float startX = 0;
			float startY = 0;
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN://��ָ������Ļ
					//1.��¼��ָ��һ�ΰ��µ�����
					startX = event.getRawX();
					startY = event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE://��ָ����Ļ�ƶ�
					//2.����һ���µ�����
					float newX = event.getRawX();
					float newY = event.getRawY();
					
					//3.����ƫ����
					int dX = (int) (newX - startX);
					int dY = (int) (newY - startY);
					
					//4.����ƫ���������¿ؼ���λ��
					iv_drag_view.layout(iv_drag_view.getLeft() + dX, iv_drag_view.getTop() + dY,
							iv_drag_view.getRight() + dX, iv_drag_view.getBottom() + dY);
					
					//5.���¼�¼����
					startX = event.getRawX();
					startY = event.getRawY();

					break;

				case MotionEvent.ACTION_UP://��ָ�뿪��Ļ

					break;

				default:
					break;
				}



				return true;
			}
		});
	}
}
