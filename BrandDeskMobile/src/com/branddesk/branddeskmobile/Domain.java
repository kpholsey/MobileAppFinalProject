package com.branddesk.branddeskmobile;

import org.json.JSONException;
import org.json.JSONObject;

public class Domain {
	String domain;
	String availability;
	
	public static Domain createDomain(JSONObject js) throws JSONException {
		Domain domain = new Domain();
		
		domain.setDomain(js.getString("domain"));
		domain.setAvailability(js.getString("availability"));
		
		return domain;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	@Override
	public String toString() {
		return "Domain [domain=" + domain + ", availability=" + availability
				+ "]";
	}
}
