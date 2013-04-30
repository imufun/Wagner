package br.tonykley.marblesbrazil.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecordeSQLiteOpenHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "recorde";
	public static final int DATABASE_VERSION = 1;
	
	public RecordeSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String CREATE_TABLE_RECORD = "CREATE TABLE "+ Recorde.TABLE_NAME +
				" ("+Recorde.KEY_ID + " integer primary key, " +
				Recorde.KEY_RECORDE + " long); ";
		arg0.execSQL(CREATE_TABLE_RECORD);
		
		String INICIALIZA_TABELA_RECORD = "INSERT INTO "+Recorde.TABLE_NAME +
				"("+Recorde.KEY_ID+","+Recorde.KEY_RECORDE+")VALUES(1,0);";
		
		arg0.execSQL(INICIALIZA_TABELA_RECORD);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
