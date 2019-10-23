package com.ats.rusaaccessweb.reportnew.model;


public class NoAwardRecogExtAct {

	private String uniqId;
	private String awardYear;
	private int noAward;
	private String instituteName;
	private String academicYear;
	public String getUniqId() {
		return uniqId;
	}
	public void setUniqId(String uniqId) {
		this.uniqId = uniqId;
	}
	public String getAwardYear() {
		return awardYear;
	}
	public void setAwardYear(String awardYear) {
		this.awardYear = awardYear;
	}
	public int getNoAward() {
		return noAward;
	}
	public void setNoAward(int noAward) {
		this.noAward = noAward;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	@Override
	public String toString() {
		return "NoAwardRecogExtAct [uniqId=" + uniqId + ", awardYear=" + awardYear + ", noAward=" + noAward
				+ ", instituteName=" + instituteName + ", academicYear=" + academicYear + "]";
	}
	
	
}
