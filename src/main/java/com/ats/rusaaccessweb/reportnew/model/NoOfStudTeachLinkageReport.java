package com.ats.rusaaccessweb.reportnew.model;
 
public class NoOfStudTeachLinkageReport {
	
  	private int acYearId;
	
	private String linkageCount;
	
	private String academicYear;
	
 	private String  instituteName;

	public int getAcYearId() {
		return acYearId;
	}

	public void setAcYearId(int acYearId) {
		this.acYearId = acYearId;
	}

	public String getLinkageCount() {
		return linkageCount;
	}

	public void setLinkageCount(String linkageCount) {
		this.linkageCount = linkageCount;
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
		return "NoOfStudTeachLinkageReport [acYearId=" + acYearId + ", linkageCount=" + linkageCount + ", academicYear="
				+ academicYear + ", instituteName=" + instituteName + ", getAcYearId()=" + getAcYearId()
				+ ", getLinkageCount()=" + getLinkageCount() + ", getAcademicYear()=" + getAcademicYear()
				+ ", getInstituteName()=" + getInstituteName() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
 	
 	
 	
	
	
}
