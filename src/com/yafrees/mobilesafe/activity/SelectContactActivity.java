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
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectContactActivity extends Activity {

	private ListView lv_select_contact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_contact);

		lv_select_contact = (ListView) findViewById(R.id.lv_select_contact);
		final List<Map<String, String>> data = getAllContacts();
		lv_select_contact.setAdapter(new SimpleAdapter(this, data,R.layout.select_item , 
				new String [] {"name" , "number"}, new int []{R.id.tv_name , R.id.tv_number}));
		
		lv_select_contact.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String number = data.get(position).get("number");
				//1.回传数据（电话号码）
				Intent intent = new Intent();
				intent.putExtra("number", number);
				setResult(1, intent);
				//当前页面关闭
				finish();
			}
		});

	}

	//***********************************************************************************
	//得到手机里面的所有练习人
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

				Cursor datacursor = resolver.query(data_uri, new String []{"data1" , "mimetype"}, "raw_contact_id = ?", new String []{contact_id}, null);
				while(datacursor.moveToNext()){
					String data1 = datacursor.getString(0);
					String mimetype = datacursor.getString(1);
					System.out.println("data1：" + data1);
					System.out.println("mimetype：" + mimetype);

					if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
						//电话号码
						map.put("number", data1);
					}
					else if("vnd.android.cursor.item/name".equals(mimetype)){
						//姓名
						map.put("name", data1);
					}

				}
				datacursor.close();
				maps.add(map);

			}
		}

		return maps;
	}
//	********************************************************

}
