package com.branddesk.branddeskmobile;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseDataManager {
	private Context mContext;
	private DatabaseOpenHelper dbOpenHelper;
	private SQLiteDatabase db;
	private SavedNameDAO savedNameDAO;
	
	public DatabaseDataManager(Context mContext) {
		this.mContext = mContext;
		dbOpenHelper = new DatabaseOpenHelper(this.mContext);
		db = dbOpenHelper.getWritableDatabase();
		savedNameDAO = new SavedNameDAO(db);
	}
	
	void close() {
		if(db != null) {
			db.close();
		}
	}
	
	public SavedNameDAO getSavedNameDAO() {
		return this.savedNameDAO;
	}
	
	public long saveSavedName(SavedName name) {
		return this.savedNameDAO.save(name);
	}

	public boolean updateSavedName(SavedName name) {
		return this.savedNameDAO.update(name);
	}

	public boolean deleteSavedName(SavedName name) {
		return this.savedNameDAO.delete(name);
	}

	public SavedName getSavedName(long id) {
		return this.savedNameDAO.get(id);
	}

	public List<SavedName> getAllSavedNames() {
		return this.savedNameDAO.getAll();
	}

	public boolean deleteAllSavedNames() {
		return this.savedNameDAO.deleteAll();
	}
}
