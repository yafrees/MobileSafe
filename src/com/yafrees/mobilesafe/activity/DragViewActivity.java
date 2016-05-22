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
	
	//�õ���Ļ�Ŀ��
	private WindowManager wm;
	private int mWidth;
	private int mHeight;
	
	private TextView tv_bottom , tv_top;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//��ȡ��Ļ�ĸߺͿ�
		wm = (WindowManager) getSystemService(WINDOW_SERVICE);
		mWidth = wm.getDefaultDisplay().getWidth();
		mHeight = wm.getDefaultDisplay().getHeight();
		
		sp = getSharedPreferences("config", MODE_PRIVATE);
		setContentView(R.layout.activity_drag_view);

		iv_drag_view = (ImageView) findViewById(R.id.iv_drag_view);
		
		tv_top = (TextView) findViewById(R.id.tv_top);
		tv_bottom = (TextView) findViewById(R.id.tv_bottom);
		
		//ȡ���Ѿ���������Ͻǵ�����λ�ã����ƶ��������λ��
		int lastX = sp.getInt("lastX", 0);
		int lastY = sp.getInt("lastY", 0);
		Log.e(TAG, "ȡ�������ֵ(" + lastX + "," + lastY + ")");
//		*****************************************************************
		//���ؼ����϶�����Ļ�ϰ벿�ֵ�ʱ�򣬶����ı������أ��ײ��ı�����ʾ
		if (lastY > mHeight / 2) {
			//��ǰ�ؼ����°벿��
			tv_top.setVisibility(View.VISIBLE);
			tv_bottom.setVisibility(View.INVISIBLE);
		}
		else {
			//��ǰ�ؼ����ϰ벿��
			tv_top.setVisibility(View.INVISIBLE);
			tv_bottom.setVisibility(View.VISIBLE);
		}
//	***************************************************************	
		
//		iv_drag_view.layout(lastX, lastY,
//				lastX + iv_drag_view.getWidth(), lastY + iv_drag_view.getHeight());
		Log.e(TAG, "���ƶ��ؼ��Ŀ�͸�(" + iv_drag_view.getWidth() + "," + iv_drag_view.getHeight() + ")");
		
		LayoutParams params = (LayoutParams) iv_drag_view.getLayoutParams();
		params.leftMargin = lastX;
		params.topMargin = lastY;
		iv_drag_view.setLayoutParams(params);
		
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
					Log.e(TAG, "��һ�ΰ��µ�����(" + startX + "," + startY + ")");
					break;
				case MotionEvent.ACTION_MOVE://��ָ����Ļ�ƶ�
					//2.����һ���µ�����
					float newX = event.getRawX();
					float newY = event.getRawY();
					
					//3.����ƫ����
					int dX = (int) (newX - startX);
					int dY = (int) (newY - startY);
					Log.e(TAG, "�ƶ���ƫ����(" + dX + "," + dY + ")");

//	******************************************************************				
					//���ηǷ��϶�
					int newL = iv_drag_view.getLeft() + dX;
					int newT = iv_drag_view.getTop() + dY;
					int newR = iv_drag_view.getRight() + dX;
					int newB = iv_drag_view.getBottom() + dY;
					if (newL < 0 || newT < 0 || newR > mWidth || newB > mHeight - getStatusBarHeight()) {
						break;
					}
					
//	*******************************************************************
					//���ؼ����϶�����Ļ�ϰ벿�ֵ�ʱ�򣬶����ı������أ��ײ��ı�����ʾ
					if (newT > mHeight / 2) {
						//��ǰ�ؼ����°벿��
						tv_top.setVisibility(View.VISIBLE);
						tv_bottom.setVisibility(View.INVISIBLE);
					}
					else {
						//��ǰ�ؼ����ϰ벿��
						tv_top.setVisibility(View.INVISIBLE);
						tv_bottom.setVisibility(View.VISIBLE);
					}
//**************************************************************************
					//4.����ƫ���������¿ؼ���λ��
					iv_drag_view.layout(newL , newT , newR , newB);
					
					//5.���¼�¼����
					startX = event.getRawX();
					startY = event.getRawY();

					break;

				case MotionEvent.ACTION_UP://��ָ�뿪��Ļ
					Log.e(TAG, "��ָ�뿪��Ļ");
					Editor editor = sp.edit();
					//�������Ͻǵ�����λ��
					editor.putInt("lastX", iv_drag_view.getLeft());
					editor.putInt("lastY", iv_drag_view.getTop());
					editor.commit();

					Log.e(TAG, "���������(" + iv_drag_view.getLeft() + "," + iv_drag_view.getTop() + ")");
					break;

				default:
					break;
				}

				return true;
			}
		});
	}
	
//**************************************************************
	//��ȡ״̬���ĸ߶�
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
