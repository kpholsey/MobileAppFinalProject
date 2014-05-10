package com.branddesk.branddeskmobile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class GetTwitterInfoAsyncTask extends AsyncTask<String, Void, Integer> {
Twitter activity;
	
	public GetTwitterInfoAsyncTask(Twitter activity) {
		this.activity = activity;
	}
	
	protected Integer doInBackground(String...params) {
		String urlString = params[0];
		try {
			URL url = new URL(urlString);			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.connect();			
			int statusCode = con.getResponseCode();
			if (statusCode == HttpURLConnection.HTTP_OK) {		
				Integer twitterCheck = 1;
				return twitterCheck;
			} else {
				Integer twitterCheck = 2;
				return twitterCheck;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Integer result) {
		activity.setupTwitterData(result);
		super.onPostExecute(result);
		if (activity.checkAllFlags()) {
			activity.addNameToList();
			activity.resetAllFields();
		}
	}
	
	static public interface Twitter {
		public void setupTwitterData(Integer result);
		public boolean checkAllFlags();
		public void resetAllFields();
		public void addNameToList();
		public Context getContext();
	}
}
