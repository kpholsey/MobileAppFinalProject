package com.branddesk.branddeskmobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseOpenHelper extends SQLiteOpenHelper{
	
	static final String DB_NAME = "savedname.db";
	static final int DB_VERSION = 1;
	
	public DatabaseOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		SavedNameTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		SavedNameTable.onUpgrade(db, oldVersion, newVersion);
	}
}
