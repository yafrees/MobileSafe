package com.yafrees.mobilesafe.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NumberAddressQueryDao {

	private static String path = "/data/data/com.yafrees.mobilesafe/files/address.db";

	/**
	 * ∫≈¬ÎπÈ Ùµÿµƒ≤È—Ø
	 * */

	public static String getAddress(String number){
		String address = number;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id = ?)", 
				new String []{number.substring(0, 7)});

		while(cursor.moveToNext()){
			String location = cursor.getString(0);
			address = location;
		}
		cursor.close();
		db.close();
		return address;

	}

}
