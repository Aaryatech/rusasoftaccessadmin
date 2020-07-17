package com.ats.rusaaccessweb.model;

public class InstituteMaster {

	private int mhInstId;
	private String aisheCode;
	private String instName;
	private String district;
	private String taluka;
	private String affUniversity;
	private String estYear;
	
	public int getMhInstId() {
		return mhInstId;
	}
	public void setMhInstId(int mhInstId) {
		this.mhInstId = mhInstId;
	}
	public String getAisheCode() {
		return aisheCode;
	}
	public void setAisheCode(String aisheCode) {
		this.aisheCode = aisheCode;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getTaluka() {
		return taluka;
	}
	public void setTaluka(String taluka) {
		this.taluka = taluka;
	}
	public String getAffUniversity() {
		return affUniversity;
	}
	public void setAffUniversity(String affUniversity) {
		this.affUniversity = affUniversity;
	}
	public String getEstYear() {
		return estYear;
	}
	public void setEstYear(String estYear) {
		this.estYear = estYear;
	}
	
	@Override
	public String toString() {
		return "InstituteMaster [mhInstId=" + mhInstId + ", aisheCode=" + aisheCode + ", instName=" + instName
				+ ", district=" + district + ", taluka=" + taluka + ", affUniversity=" + affUniversity + ", estYear="
				+ estYear + "]";
	}
	
}
