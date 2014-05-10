package com.branddesk.branddeskmobile;

import java.io.Serializable;

public class SavedName implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8475110814352394405L;
	
	private long _id;
	private String name;
	private String rationale;
	private int trademarkCount;
	private String dotComAvailability;
	private String dotNetAvailability;
	private String isTwitterUsernameAvailable;
	private String isFacebookUrlAvailable;

	public SavedName(String name, String rationale, int trademarkCount,
			String dotComAvailability, String dotNetAvailability,
			String isTwitterUsernameAvailable, String isFacebookUrlAvailable) {
		super();
		this.name = name;
		this.rationale = rationale;
		this.trademarkCount = trademarkCount;
		this.dotComAvailability = dotComAvailability;
		this.dotNetAvailability = dotNetAvailability;
		this.isTwitterUsernameAvailable = isTwitterUsernameAvailable;
		this.isFacebookUrlAvailable = isFacebookUrlAvailable;
	}

	public SavedName() {

	}

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
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

	public String getIsTwitterUsernameAvailable() {
		return isTwitterUsernameAvailable;
	}

	public void setIsTwitterUsernameAvailable(String isTwitterUsernameAvailable) {
		this.isTwitterUsernameAvailable = isTwitterUsernameAvailable;
	}

	public String getIsFacebookUrlAvailable() {
		return isFacebookUrlAvailable;
	}

	public void setIsFacebookUrlAvailable(String isFacebookUrlAvailable) {
		this.isFacebookUrlAvailable = isFacebookUrlAvailable;
	}

	@Override
	public String toString() {
		return "Name: " + name + ", Rationale: " + rationale
				+ ", Possible Conflicts: " + trademarkCount
				+ ", .COM Availability: " + dotComAvailability
				+ ", .NET Availability: " + dotNetAvailability
				+ ", Twitter Availability: " + isTwitterUsernameAvailable
				+ ", Facebook URL Availability: " + isFacebookUrlAvailable;
	}
}
