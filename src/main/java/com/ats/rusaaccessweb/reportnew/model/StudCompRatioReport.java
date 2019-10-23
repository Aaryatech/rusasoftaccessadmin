package com.ats.rusaaccessweb.reportnew.model;

public class StudCompRatioReport {

	private int instItInfraInfoId;
	private int noOfComputers;
	private String purchaseDate;
	private int purchaseAmt;
	private int noOfStudUtilizing;
	private String instituteName;
	public int getInstItInfraInfoId() {
		return instItInfraInfoId;
	}
	public void setInstItInfraInfoId(int instItInfraInfoId) {
		this.instItInfraInfoId = instItInfraInfoId;
	}
	public int getNoOfComputers() {
		return noOfComputers;
	}
	public void setNoOfComputers(int noOfComputers) {
		this.noOfComputers = noOfComputers;
	}
	public String getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(String purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public int getPurchaseAmt() {
		return purchaseAmt;
	}
	public void setPurchaseAmt(int purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}
	public int getNoOfStudUtilizing() {
		return noOfStudUtilizing;
	}
	public void setNoOfStudUtilizing(int noOfStudUtilizing) {
		this.noOfStudUtilizing = noOfStudUtilizing;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "StudCompRatioReport [instItInfraInfoId=" + instItInfraInfoId + ", noOfComputers=" + noOfComputers
				+ ", purchaseDate=" + purchaseDate + ", purchaseAmt=" + purchaseAmt + ", noOfStudUtilizing="
				+ noOfStudUtilizing + ", instituteName=" + instituteName + "]";
	}
	
	
}
