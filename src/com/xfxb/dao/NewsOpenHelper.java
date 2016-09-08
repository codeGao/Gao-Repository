package com.xfxb.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NewsOpenHelper extends SQLiteOpenHelper {

	public NewsOpenHelper(Context context) {
		super(context, "itheima", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL("create table news (_id integer  ,title varchar(200),des varchar(300),icon_url varchar(200),news_url varchar(200),"
				+ " type integer , time varchar(100),comment integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
}
