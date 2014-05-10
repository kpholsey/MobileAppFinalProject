package com.branddesk.branddeskmobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;

import android.content.Context;
import android.os.AsyncTask;

public class GetTrademarkInfoAsyncTask extends AsyncTask<String, Void, Integer> {
	Trademark activity;
	
	public GetTrademarkInfoAsyncTask(Trademark activity) {
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line = reader.readLine();
				while (line != null) {
					sb.append(line);
					line = reader.readLine();
				}
				return TradeMarkUtility.TrademarkJSONParser.parseTrademark(sb.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	protected void onPostExecute(Integer result) {
		activity.setupTrademarkData(result);
		super.onPostExecute(result);
		if (activity.checkAllFlags()) {
			activity.addNameToList();
			activity.resetAllFields();
		}

	}
	
	static public interface Trademark {
		public void setupTrademarkData(Integer result);
		public boolean checkAllFlags();
		public void resetAllFields();
		public void addNameToList();
		public Context getContext();
	}
}
