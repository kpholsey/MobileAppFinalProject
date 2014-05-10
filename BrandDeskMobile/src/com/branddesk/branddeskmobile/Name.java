package com.branddesk.branddeskmobile;

import java.io.Serializable;

public class Name implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6481272888893538429L;

	String name;
	String rationale;
	int trademarkCount;
	String dotComAvailability;
	String dotNetAvailability;
	boolean isTwitterUsernameAvailable;
	boolean isFacebookUrlAvailable;
	
	public Name() {
		
	}
	
	public Name(String name, int trademarkCount, String dotComAvailability,
			String dotNetAvailability, boolean isTwitterUsernameAvailable,
			boolean isFacebookUrlAvailable) {
		super();
		this.name = name;
		this.trademarkCount = trademarkCount;
		this.dotComAvailability = dotComAvailability;
		this.dotNetAvailability = dotNetAvailability;
		this.isTwitterUsernameAvailable = isTwitterUsernameAvailable;
		this.isFacebookUrlAvailable = isFacebookUrlAvailable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getRationale() {
		return rationale;
	}

	public void setRationale(String rationale) {
		this.rationale = rationale;
	}

	public int getTrademarkCount() {
		return trademarkCount;
	}

	public void setTrademarkCount(int trademarkCount) {
		this.trademarkCount = trademarkCount;
	}

	public String getDotComAvailability() {
		return dotComAvailability;
	}

	public void setDotComAvailability(String dotComAvailability) {
		this.dotComAvailability = dotComAvailability;
	}

	public String getDotNetAvailability() {
		return dotNetAvailability;
	}

	public void setDotNetAvailability(String dotNetAvailability) {
		this.dotNetAvailability = dotNetAvailability;
	}

	public boolean isTwitterUsernameAvailable() {
		return isTwitterUsernameAvailable;
	}

	public void setTwitterUsernameAvailable(boolean isTwitterUsernameAvailable) {
		this.isTwitterUsernameAvailable = isTwitterUsernameAvailable;
	}

	public boolean isFacebookUrlAvailable() {
		return isFacebookUrlAvailable;
	}

	public void setFacebookUrlAvailable(boolean isFacebookUrlAvailable) {
		this.isFacebookUrlAvailable = isFacebookUrlAvailable;
	}

	@Override
	public String toString() {
		return "Name [name=" + name + ", trademarkCount=" + trademarkCount
				+ ", dotComAvailability=" + dotComAvailability
				+ ", dotNetAvailability=" + dotNetAvailability
				+ ", isTwitterUsernameAvailable=" + isTwitterUsernameAvailable
				+ ", isFacebookUrlAvailable=" + isFacebookUrlAvailable + "]";
	}
}
