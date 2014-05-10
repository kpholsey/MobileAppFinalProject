package com.branddesk.branddeskmobile;


import org.json.JSONException;
import org.json.JSONObject;

public class TradeMarkUtility {
	static public class TrademarkJSONParser{		
		static Integer parseTrademark(String in) throws JSONException {	
			Integer trademarkCount;
			JSONObject root = new JSONObject(in);
			trademarkCount = root.getInt("count");
			return trademarkCount;
		}
	}
}
