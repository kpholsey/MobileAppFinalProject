package com.branddesk.branddeskmobile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;


public class GetFacebookInfoAsyncTask extends AsyncTask<String, Void, Integer>{
Facebook activity;
	
	public GetFacebookInfoAsyncTask(Facebook activity) {
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
				Integer facebookCheck = 1;
				return facebookCheck;
			} else {
				Integer facebookCheck = 2;
				return facebookCheck;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	protected void onPostExecute(Integer result) {
		activity.setupFacebookData(result);
		super.onPostExecute(result);
		if (activity.checkAllFlags()) {
			activity.addNameToList();
			activity.resetAllFields();
		}

	}
	
	static public interface Facebook {
		public void setupFacebookData(Integer result);
		public boolean checkAllFlags();
		public void resetAllFields();
		public void addNameToList();
		public Context getContext();
	}
}
