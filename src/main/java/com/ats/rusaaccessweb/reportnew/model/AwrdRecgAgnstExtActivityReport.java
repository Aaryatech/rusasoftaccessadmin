package com.ats.rusaaccessweb.reportnew.model;

public class AwrdRecgAgnstExtActivityReport {
	private int awrdRecgAgnstExtActId;
	private String actName;
	private String awardYear;
	private String nameAwardingBody;
	private String academicYear;
	private String instituteName;
	public int getAwrdRecgAgnstExtActId() {
		return awrdRecgAgnstExtActId;
	}
	public void setAwrdRecgAgnstExtActId(int awrdRecgAgnstExtActId) {
		this.awrdRecgAgnstExtActId = awrdRecgAgnstExtActId;
	}
	public String getActName() {
		return actName;
	}
	public void setActName(String actName) {
		this.actName = actName;
	}
	public String getAwardYear() {
		return awardYear;
	}
	public void setAwardYear(String awardYear) {
		this.awardYear = awardYear;
	}
	public String getNameAwardingBody() {
		return nameAwardingBody;
	}
	public void setNameAwardingBody(String nameAwardingBody) {
		this.nameAwardingBody = nameAwardingBody;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	@Override
	public String toString() {
		return "AwrdRecgAgnstExtActivityReport [awrdRecgAgnstExtActId=" + awrdRecgAgnstExtActId + ", actName=" + actName
				+ ", awardYear=" + awardYear + ", nameAwardingBody=" + nameAwardingBody + ", academicYear="
				+ academicYear + ", instituteName=" + instituteName + "]";
	}
	
	
}
