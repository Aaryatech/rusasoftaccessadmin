package com.ats.rusaaccessweb.reportnew.model;

public class StudSupprtSchemGraph {
//Student Support Scheme
	
	private int sprtSchmId;
	private String schemeName;
	private int  noStudentBenifited;
	private String supportAgencyName;
	private int noCurrentAdmitedStnt;
	public int getSprtSchmId() {
		return sprtSchmId;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public int getNoStudentBenifited() {
		return noStudentBenifited;
	}
	public String getSupportAgencyName() {
		return supportAgencyName;
	}
	public int getNoCurrentAdmitedStnt() {
		return noCurrentAdmitedStnt;
	}
	public void setSprtSchmId(int sprtSchmId) {
		this.sprtSchmId = sprtSchmId;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public void setNoStudentBenifited(int noStudentBenifited) {
		this.noStudentBenifited = noStudentBenifited;
	}
	public void setSupportAgencyName(String supportAgencyName) {
		this.supportAgencyName = supportAgencyName;
	}
	public void setNoCurrentAdmitedStnt(int noCurrentAdmitedStnt) {
		this.noCurrentAdmitedStnt = noCurrentAdmitedStnt;
	}
	@Override
	public String toString() {
		return "StudSupprtSchemGraph [sprtSchmId=" + sprtSchmId + ", schemeName=" + schemeName + ", noStudentBenifited="
				+ noStudentBenifited + ", supportAgencyName=" + supportAgencyName + ", noCurrentAdmitedStnt="
				+ noCurrentAdmitedStnt + "]";
	}
	
}
