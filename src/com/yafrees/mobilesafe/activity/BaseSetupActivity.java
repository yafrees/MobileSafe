package com.yafrees.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * ����ʶ�𣬵�ת��һ������Ļ���
 * */

/**
 * ���ࡢ���ࡢ������
 * */

public abstract class BaseSetupActivity extends Activity {

	//1.��������ʶ������ʵ�������򵼽���Ļ����л�
	private GestureDetector detector;
	
	//����һ��
	protected SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
		

		//2.ʵ��������ʶ����
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			//onFling������
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

				//������(���ٻ���)���ٶȵ�λ������ÿ��
				if (Math.abs(velocityX) < 100) {
					Toast.makeText(getApplicationContext(), "����ٵĻ���...", 0).show();
					return true;
				}
				
				//����Y�᷽��Ļ���
				if (Math.abs(e2.getY() - e1.getY()) > 100) {
					Toast.makeText(getApplicationContext(), "����ˮƽ�����ϻ���...", 0).show();
					return true;
				}
				
				if (e2.getX() - e1.getX() > 200) {
					//��ʾ��һ��ҳ��
					System.out.println("��ʾ��һ��ҳ��");
					showPre();
					return true;
				}
				if (e1.getX() - e2.getX() > 200) {
					//��ʾ��һ��ҳ��
					System.out.println("��ʾ��һ��ҳ��");
					showNext();
					return true;
				}
				return super.onFling(e1, e2, velocityX, velocityY);
			}

		});

	}

	//3.ʹ������ʶ����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
//	***********************************************************

	//��ʾ��һ��ҳ��ĳ��󷽷�
	public abstract void showNext();
	//��ʾ��һ��ҳ��ĳ��󷽷�
	public abstract void showPre();
	
//	*******************************************************

	//��һ����ť�ĵ���¼�
	public void next(View view){
		showNext();
	}
	//��һ����ť�ĵ���¼�
	public void previous(View view){
		showPre();
	}




}
