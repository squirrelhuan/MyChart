package com.example.administrator.mychart.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.administrator.mychart.model.Message;
import com.example.administrator.mychart.model.User;

import android.util.Log;

public class DatabaseUtil {
	private MyHelper helper;

	public DatabaseUtil(Context context) {
		super();
		helper = new MyHelper(context);
		Log.d("table", "create helper");
	}

	public boolean InsertMessage(Message message){
		SQLiteDatabase db = helper.getWritableDatabase();
		String sql = "insert into "+MyHelper.TABLE_NAME
				+"(username,nickname) values ("
				+ "'"+message.getSendUser().getUsername().toString()
				+ "'," +"'"+message.getSendUser().getNickname().toString()+"'"+ ")";
		try {
			db.execSQL(sql);
			return true;
		} catch (SQLException e){
			Log.e("err", "insert failed");
			return false;
		}finally{
			db.close();
		}

	}
	
	/**��������
	 * @param  message ,  id
	 * */
	
	public void Update(Message message ,int id){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("username", message.getSendUser().getUsername());
		values.put("nickname", message.getSendUser().getNickname());
		/*values.put("sex", person.getSex());*/
		int rows = db.update(MyHelper.TABLE_NAME, values, "_id=?", new String[] { id + "" });
		
		db.close();
	}
	
	/**ɾ������
	 * @param  id
	 * */
	
	public void Delete(int id){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		int raw = db.delete(MyHelper.TABLE_NAME, "_id=?", new String[]{id+""});
		db.close();
	}
	
	/**��ѯ��������
	 * 
	 * */
	public List<Message> queryAll(){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Message> list = new ArrayList<Message>();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, null, null,null, null, null, null);
		
		while(cursor.moveToNext()){
			Message message = new Message();
			message.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			User user = new User();
			user.setUsername(cursor.getString(cursor.getColumnIndex("username")));
			user.setNickname(cursor.getString(cursor.getColumnIndex("nickname")));
			message.setSendUser(user);
			 //person.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			 list.add(message);
		}
		db.close();
		return list;
	}

	public List<Message> queryByname(String name){
		SQLiteDatabase db = helper.getReadableDatabase();
		List<Message> list = new ArrayList<Message>();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, new String[]{"_id","name","nickname"}, "name like ? " ,new String[]{"%" +name + "%" }, null, null, "name asc");
//		Cursor cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
		while(cursor.moveToNext()){
			 Message message = new Message();
			 message.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			// message.setName(cursor.getString(cursor.getColumnIndex("username")));
			// message.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			 list.add(message);
		}
		db.close();
		return list;
	} 

	public Message queryByid(int id){
		
		SQLiteDatabase db = helper.getReadableDatabase();
		Message message = new Message();
		Cursor cursor = db.query(MyHelper.TABLE_NAME, new String[]{"username","nickname"}, "_id=?",new String[]{ id + ""}, null, null, null);
//		db.delete(table, whereClause, whereArgs)
		while(cursor.moveToNext()){
			message.setId(id);
			//message.setName(cursor.getString(cursor.getColumnIndex("name")));
			//message.setSex(cursor.getString(cursor.getColumnIndex("sex")));
		}
		db.close();
		return message;
	}
	

}
