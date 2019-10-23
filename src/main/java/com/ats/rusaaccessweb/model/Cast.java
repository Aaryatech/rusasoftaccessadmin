package com.ats.rusaaccessweb.model;

public class Cast {

	private int castId;
	
	private String castName;
	private String castRemark;

	private int delStatus;
	private int isActive;
	public int getCastId() {
		return castId;
	}
	public void setCastId(int castId) {
		this.castId = castId;
	}
	public String getCastName() {
		return castName;
	}
	public void setCastName(String castName) {
		this.castName = castName;
	}
	public String getCastRemark() {
		return castRemark;
	}
	public void setCastRemark(String castRemark) {
		this.castRemark = castRemark;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
	@Override
	public String toString() {
		return "Cast [castId=" + castId + ", castName=" + castName + ", castRemark=" + castRemark + ", delStatus="
				+ delStatus + ", isActive=" + isActive + "]";
	}
	
}
