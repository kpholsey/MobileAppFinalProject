package com.branddesk.branddeskmobile;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SavedNameTable {
	
	static final String TABLENAME = "names";
	static final String COLUMN_ID = "_id";
	static final String COLUMN_NAME = "name";
	static final String COLUMN_RATIONALE = "rationale";
	static final String COLUMN_TRADEMARK = "trademark";
	static final String COLUMN_DOTCOM = "dotcom";
	static final String COLUMN_DOTNET = "dotnet";
	static final String COLUMN_TWITTER = "twitter";
	static final String COLUMN_FACEBOOK = "facebook";
	
	static public void onCreate(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLENAME + " (");
		sb.append(COLUMN_ID + " integer primary key autoincrement, ");
		sb.append(COLUMN_NAME + " text not null, ");
		sb.append(COLUMN_RATIONALE + " text, ");
		sb.append(COLUMN_TRADEMARK + " integer not null, ");
		sb.append(COLUMN_DOTCOM + " text not null, ");
		sb.append(COLUMN_DOTNET + " text not null, ");
		sb.append(COLUMN_TWITTER + " text not null, ");
		sb.append(COLUMN_FACEBOOK + " text not null);");

		try {
			db.execSQL(sb.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
		static public void onUpgrade(SQLiteDatabase db, int oldVersion,
				int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
			SavedNameTable.onCreate(db);
		}
}
