package com.yafrees.mobilesafe.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yafrees.mobilesafe.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectContactActivity extends Activity {

	private ListView lv_select_contact;
	private  List<Map<String, String>> data;

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			lv_select_contact.setAdapter(new SimpleAdapter(SelectContactActivity.this, data , R.layout.select_item , 
					new String [] {"name" , "number"}, new int []{R.id.tv_name , R.id.tv_number}));
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);

		lv_select_contact = (ListView) findViewById(R.id.lv_select_contact);

		fillData();


		lv_select_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String number = data.get(position).get("number");
				//1.�ش����ݣ��绰���룩
				Intent intent = new Intent();
				intent.putExtra("number", number);
				setResult(1, intent);
				//��ǰҳ��ر�
				finish();
			}
		});

	}
	//***********************************************************
	/**
	 * ��������
	 * */
	private void fillData() {
		new Thread(){
			public void run() {

				data = getAllContacts();
				handler.sendEmptyMessage(0);
			};
		}.start();
	}

	//***********************************************************************************
	//�õ��ֻ������������ϰ��
	private List<Map<String, String>> getAllContacts() {
		List<Map<String, String>> maps = new ArrayList<Map<String,String>>();

		ContentResolver resolver = getContentResolver();
		Uri raw_contacts_uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri data_uri = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = resolver.query(raw_contacts_uri, new String [] {"contact_id"}, null, null, null);
		while(cursor.moveToNext()){
			String contact_id = cursor.getString(0);
			if (contact_id != null) {
				Map<String, String> map = new HashMap<String, String>();

				Cursor datacursor = resolver.query(data_uri, new String []{"data1" , "mimetype"}, "contact_id = ?", new String []{contact_id}, null);
				while(datacursor.moveToNext()){
					String data1 = datacursor.getString(0);
					String mimetype = datacursor.getString(1);

					System.out.println("data1��" + data1);
					System.out.println("mimetype��" + mimetype);

					if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
						//�绰����
						map.put("number", data1);
					}
					else if("vnd.android.cursor.item/name".equals(mimetype)){
						//����
						map.put("name", data1);
					}

				}
				datacursor.close();
				
				if (map.get("name") != null && map.get("number") != null) {
					maps.add(map);
				}

			}
		}

		return maps;
	}
	//	********************************************************

}
