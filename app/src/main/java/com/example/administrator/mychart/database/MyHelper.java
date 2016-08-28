package com.example.administrator.mychart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyHelper extends SQLiteOpenHelper {
	
	private static String DB_NAME = "mychartdata.db";
	public static String TABLE_NAME = "conversation";
	private static final int version = 1; //数据库版本
	/**super(参数1，参数2，参数3，参数4)，其中参数4是代表数据库的版本，
	 * 是一个大于等于1的整数，如果要修改（添加字段）表中的字段，则设置
	 * 一个比当前的 参数4大的整数 ，把更新的语句写在onUpgrade(),下一次
	 * 调用
	 */
	public MyHelper(Context context) {
		super(context, DB_NAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//Create table
		String sql = "create table "+TABLE_NAME+"(username TEXT ,nickname TEXT);";
		Log.d("table", "create table");
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.e("update", "update");
//		db.execSQL("ALTER TABLE "+ MyHelper.TABLE_NAME+" ADD sex TEXT"); //�޸��ֶ�

	}

}
