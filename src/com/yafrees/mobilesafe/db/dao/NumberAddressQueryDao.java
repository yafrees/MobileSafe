package com.yafrees.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberAddressQueryDao {

	private static String path = "/data/data/com.yafrees.mobilesafe/files/address.db";

	/**
	 * 号码归属地的查询
	 * */

	public static String getAddress(String number){
		String address = number;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		//如果是手机号码，------正则表达式
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
				address = "报警电话";
				break;
			case 4:
				address = "模拟器";
				break;
			case 5:
				address = "客服电话";
				break;
			case 6:
				address = "本地号码";
				break;
			case 7:
				address = "本地号码";
				break;
			case 8:
				address = "本地号码";
				break;

			default:
				if (number.startsWith("0") && number != null && number.length() > 10) {
					//010-xxxxxxx
					Cursor cursor = db.rawQuery("select location from data2 where area = ?", new String []{number.substring(1, 3)});
				while(cursor.moveToNext()){
					String location = cursor.getString(0);
					//把后面的运营商去掉
					address = location.substring(0, location.length() - 2);
				}
				//0855--xxxxxxx
				cursor = db.rawQuery("select location from data2 where area = ?", new String []{number.substring(1, 4)});
				while(cursor.moveToNext()){
					String location = cursor.getString(0);
					//把后面的运营商去掉
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
