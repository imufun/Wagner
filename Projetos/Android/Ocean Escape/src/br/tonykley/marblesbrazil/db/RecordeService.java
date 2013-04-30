package br.tonykley.marblesbrazil.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecordeService {
	private RecordeSQLiteOpenHelper recordSQLiteOpenHelper;
	private SQLiteDatabase database;
	
	public RecordeService(RecordeSQLiteOpenHelper precordSQLiteOpenHelper) {
		recordSQLiteOpenHelper = precordSQLiteOpenHelper;
	}
	public void open(){
		database = recordSQLiteOpenHelper.getWritableDatabase();
	}
	
	public Long getRecorde() {
		Cursor cursor;
		cursor = database.query(Recorde.TABLE_NAME, 
				new String[]{Recorde.KEY_ID+",MAX("+Recorde.KEY_RECORDE+") as "+Recorde.KEY_RECORDE}, 
			null, null, 
				null, null, null);
		cursor.moveToFirst();
		Long retorno = cursor.getLong(cursor.getColumnIndex(Recorde.KEY_RECORDE));
	    cursor.close();
	    return retorno;
	}
	
	public void novoRecorde(Long record){
		ContentValues values = new ContentValues();
		values.put(Recorde.KEY_RECORDE, record);
		database.update(Recorde.TABLE_NAME, values, "_id = ?", new String[]{Long.toString(1)});
	}

}
