package com.ats.rusaaccessweb.reportnew.model;
 
public class IniToEngageLocComm {
	
 	private int yearId;
	
	private String academicYear;
	
	private String instituteName;
	
	private String  iniCount;

	public int getYearId() {
		return yearId;
	}

	public void setYearId(int yearId) {
		this.yearId = yearId;
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

	public String getIniCount() {
		return iniCount;
	}

	public void setIniCount(String iniCount) {
		this.iniCount = iniCount;
	}

	@Override
	public String toString() {
		return "IniToEngageLocComm [yearId=" + yearId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", iniCount=" + iniCount + ", getYearId()=" + getYearId() + ", getAcademicYear()="
				+ getAcademicYear() + ", getInstituteName()=" + getInstituteName() + ", getIniCount()=" + getIniCount()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
