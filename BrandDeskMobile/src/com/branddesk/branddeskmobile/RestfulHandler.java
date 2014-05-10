package com.branddesk.branddeskmobile;


public class RestfulHandler {
	private String nameTerm;
	
	public RestfulHandler(String name) {
		this.nameTerm = name.toLowerCase();
	}
	
	public String getMarkerUrl() {
		String puncRemoval = nameTerm.replaceAll("\\W", "");
		String replaced = puncRemoval.replace(" ", "%20");
		String url = String.format("http://www.markerapi.com/api/v1/trademark/search/%s/username/keller2/password/BnjydVXGWT", replaced);
		return url;
	}
	
	public String getDomainUrl() {
		String puncRemoval = nameTerm.replaceAll("\\W", "");
		String replaced = puncRemoval.replace(" ", "");
		String url = String.format("https://domai.nr/api/json/search?q=%s", replaced);
		return url;
	}
	
	public String getTwitterUrl() {
		String puncRemoval = nameTerm.replaceAll("\\W", "");
		String replaced = puncRemoval.replace(" ", "");
		String url = String.format("https://twitter.com/%s", replaced);
		return url;
	}
	
	public String getFacebookUrl() {
		String puncRemoval = nameTerm.replaceAll("\\W", "");
		String replaced = puncRemoval.replace(" ", "");
		String url = String.format("https://facebook.com/%s", replaced);
		return url;
	}
	
	public String getNameCheapUrl() {
		String puncRemoval = nameTerm.replaceAll("\\W", "");
		String replaced = puncRemoval.replace(" ", "%20");
		String url = String.format("https://www.namecheap.com/domains/registration/results.aspx?domain=%s", replaced);
		return url;
	}
}
