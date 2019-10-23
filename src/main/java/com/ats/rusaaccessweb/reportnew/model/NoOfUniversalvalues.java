package com.ats.rusaaccessweb.reportnew.model;
 
public class NoOfUniversalvalues {
	
	 
	private int acYearId;
	
	private String academicYear;
	
	private String instituteName;
	
	private String actCount;

	public int getAcYearId() {
		return acYearId;
	}

	public void setAcYearId(int acYearId) {
		this.acYearId = acYearId;
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

	public String getActCount() {
		return actCount;
	}

	public void setActCount(String actCount) {
		this.actCount = actCount;
	}

	@Override
	public String toString() {
		return "NoOfUniversalvalues [acYearId=" + acYearId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", actCount=" + actCount + ", getAcYearId()=" + getAcYearId() + ", getAcademicYear()="
				+ getAcademicYear() + ", getInstituteName()=" + getInstituteName() + ", getActCount()=" + getActCount()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
	
	
	

}
