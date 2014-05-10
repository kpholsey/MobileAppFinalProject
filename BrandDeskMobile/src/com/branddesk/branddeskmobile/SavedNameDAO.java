package com.branddesk.branddeskmobile;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SavedNameDAO {
	private SQLiteDatabase db;

	public SavedNameDAO(SQLiteDatabase db) {
		this.db = db;
	}

	public long save(SavedName name) {
		ContentValues values = new ContentValues();
		values.put(SavedNameTable.COLUMN_NAME, name.getName());
		values.put(SavedNameTable.COLUMN_RATIONALE, name.getRationale());
		values.put(SavedNameTable.COLUMN_TRADEMARK, name.getTrademarkCount());
		values.put(SavedNameTable.COLUMN_DOTCOM, name.getDotComAvailability());
		values.put(SavedNameTable.COLUMN_DOTNET, name.getDotNetAvailability());
		values.put(SavedNameTable.COLUMN_TWITTER,
				name.getIsTwitterUsernameAvailable());
		values.put(SavedNameTable.COLUMN_FACEBOOK,
				name.getIsFacebookUrlAvailable());
		return db.insert(SavedNameTable.TABLENAME, null, values);
	}

	public boolean update(SavedName name) {
		ContentValues values = new ContentValues();
		values.put(SavedNameTable.COLUMN_NAME, name.getName());
		values.put(SavedNameTable.COLUMN_RATIONALE, name.getRationale());
		values.put(SavedNameTable.COLUMN_TRADEMARK, name.getTrademarkCount());
		values.put(SavedNameTable.COLUMN_DOTCOM, name.getDotComAvailability());
		values.put(SavedNameTable.COLUMN_DOTNET, name.getDotNetAvailability());
		values.put(SavedNameTable.COLUMN_TWITTER,
				name.getIsTwitterUsernameAvailable());
		values.put(SavedNameTable.COLUMN_FACEBOOK,
				name.getIsFacebookUrlAvailable());
		return db.update(SavedNameTable.TABLENAME, values,
				SavedNameTable.COLUMN_ID + "=?", new String[] { name.get_id()
						+ "" }) > 0;
	}

	public boolean delete(SavedName name) {
		return db.delete(SavedNameTable.TABLENAME, SavedNameTable.COLUMN_ID
				+ "=?", new String[] { name.get_id() + "" }) > 0;
	}

	@SuppressLint("NewApi")
	public SavedName get(long id) {
		SavedName name = null;
		Cursor c = db.query(true, SavedNameTable.TABLENAME, new String[] {
				SavedNameTable.COLUMN_ID, SavedNameTable.COLUMN_NAME,
				SavedNameTable.COLUMN_RATIONALE,
				SavedNameTable.COLUMN_TRADEMARK, SavedNameTable.COLUMN_DOTCOM,
				SavedNameTable.COLUMN_DOTNET, SavedNameTable.COLUMN_TWITTER,
				SavedNameTable.COLUMN_FACEBOOK }, SavedNameTable.COLUMN_ID
				+ "=?", new String[] { id + "" }, null, null, null, null, null);
		
		if (c != null && c.moveToFirst()) {
			name = buildSavedNameFromCursor(c);
			if (!c.isClosed()) {
				c.close();
			}
		}
		return name;
	}

	public List<SavedName> getAll() {
		
		List<SavedName> names = new ArrayList<SavedName>();
		Cursor c = db.query(SavedNameTable.TABLENAME, new String[] {
				SavedNameTable.COLUMN_ID, SavedNameTable.COLUMN_NAME,
				SavedNameTable.COLUMN_RATIONALE,
				SavedNameTable.COLUMN_TRADEMARK, SavedNameTable.COLUMN_DOTCOM,
				SavedNameTable.COLUMN_DOTNET, SavedNameTable.COLUMN_TWITTER,
				SavedNameTable.COLUMN_FACEBOOK }, null, null, null, null, null);
		
		if (c != null && c.moveToFirst()) {
			do {
				SavedName name = buildSavedNameFromCursor(c);
				if (name != null) {
					names.add(name);
				}
			} while (c.moveToNext());

			if (!c.isClosed()) {
				c.close();
			}
		}
		return names;
	}
	
	private SavedName buildSavedNameFromCursor(Cursor c) {
		SavedName name = null;
		if (c != null) {
			name = new SavedName();
			name.set_id(c.getLong(0));
			name.setName(c.getString(1));
			name.setRationale(c.getString(2));
			name.setTrademarkCount(c.getInt(3));
			name.setDotComAvailability(c.getString(4));
			name.setDotNetAvailability(c.getString(5));
			name.setIsTwitterUsernameAvailable(c.getString(6));
			name.setIsFacebookUrlAvailable(c.getString(7));
		}
		return name;
	}
	
	public boolean deleteAll() {
		return db.delete(SavedNameTable.TABLENAME, "1", null) > 0;
	}
}
