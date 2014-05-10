package com.branddesk.branddeskmobile;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DomainUtility {
	static public class DomainsJSONParser {
		static ArrayList<Domain> parseDomains(String in) throws JSONException {
			ArrayList<Domain> domainsList = new ArrayList<Domain>();
			
			JSONObject root = new JSONObject(in);
			JSONArray domainsJSONArray = root.getJSONArray("results");
			
			for(int i = 0; i < 2; i++) {
				JSONObject domainJSONObject = domainsJSONArray.getJSONObject(i);
				Domain domain = Domain.createDomain(domainJSONObject);
				domainsList.add(domain);
			}
			return domainsList;
		}
	}
}
