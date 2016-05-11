package com.yafrees.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberAddressQueryDao {

	private static String path = "/data/data/com.yafrees.mobilesafe/files/address.db";

	/**
	 * ��������صĲ�ѯ
	 * */

	public static String getAddress(String number){
		String address = number;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		//������ֻ����룬------������ʽ
		//
		
		if (number.matches("^1[345678]\\d{9}$")) {
			Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)", 
					new String []{number.substring(0, 7)});
			while(cursor.moveToNext()){
				String location = cursor.getString(0);
				address = location;
			}
			cursor.close();
			db.close();
		}
		else {
			switch (number.length()) {
			case 3:
				address = "�����绰";
				break;
			case 4:
				address = "ģ����";
				break;
			case 5:
				address = "�ͷ��绰";
				break;
			case 6:
				address = "���غ���";
				break;
			case 7:
				address = "���غ���";
				break;
			case 8:
				address = "���غ���";
				break;

			default:
				if (number.startsWith("0") && number != null && number.length() > 10) {
					//010-xxxxxxx
					Cursor cursor = db.rawQuery("select location from data2 where area = ?", new String []{number.substring(1, 3)});
				while(cursor.moveToNext()){
					String location = cursor.getString(0);
					//�Ѻ������Ӫ��ȥ��
					address = location.substring(0, location.length() - 2);
				}
				//0855--xxxxxxx
				cursor = db.rawQuery("select location from data2 where area = ?", new String []{number.substring(1, 4)});
				while(cursor.moveToNext()){
					String location = cursor.getString(0);
					//�Ѻ������Ӫ��ȥ��
					address = location.substring(0, location.length() - 2);
				}
				cursor.close();
				db.close();
				
				}
				break;
			}
		}
		return address;

	}

}
