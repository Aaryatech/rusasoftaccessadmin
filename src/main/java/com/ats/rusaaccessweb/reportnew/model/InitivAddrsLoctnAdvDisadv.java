package com.ats.rusaaccessweb.reportnew.model;

public class InitivAddrsLoctnAdvDisadv {

	private int spciAdvId;
	private String nameOfInitiatives;
	private int noStudPart;
	private String academicYear; 
	private String instituteName;
	public int getSpciAdvId() {
		return spciAdvId;
	}
	public void setSpciAdvId(int spciAdvId) {
		this.spciAdvId = spciAdvId;
	}
	public String getNameOfInitiatives() {
		return nameOfInitiatives;
	}
	public void setNameOfInitiatives(String nameOfInitiatives) {
		this.nameOfInitiatives = nameOfInitiatives;
	}
	public int getNoStudPart() {
		return noStudPart;
	}
	public void setNoStudPart(int noStudPart) {
		this.noStudPart = noStudPart;
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
		return "InitivAddrsLoctnAdvDisadv [spciAdvId=" + spciAdvId + ", nameOfInitiatives=" + nameOfInitiatives
				+ ", noStudPart=" + noStudPart + ", academicYear=" + academicYear + ", instituteName=" + instituteName
				+ "]";
	}
	
}
