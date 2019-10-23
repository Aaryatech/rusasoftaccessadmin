package com.ats.rusaaccessweb.reportnew.model;
 
public class GovtScheme {

 	private int govtScholarId;

	private String academicYear;

	private String instituteName;

	private int acYearId;

	private String nameOfScheme;

	private float noOfStudBenftd;

	private float noCurrentAdmitedStnt;

	public int getGovtScholarId() {
		return govtScholarId;
	}

	public void setGovtScholarId(int govtScholarId) {
		this.govtScholarId = govtScholarId;
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

	public int getAcYearId() {
		return acYearId;
	}

	public void setAcYearId(int acYearId) {
		this.acYearId = acYearId;
	}

	public String getNameOfScheme() {
		return nameOfScheme;
	}

	public void setNameOfScheme(String nameOfScheme) {
		this.nameOfScheme = nameOfScheme;
	}

	public float getNoOfStudBenftd() {
		return noOfStudBenftd;
	}

	public void setNoOfStudBenftd(float noOfStudBenftd) {
		this.noOfStudBenftd = noOfStudBenftd;
	}

	public float getNoCurrentAdmitedStnt() {
		return noCurrentAdmitedStnt;
	}

	public void setNoCurrentAdmitedStnt(float noCurrentAdmitedStnt) {
		this.noCurrentAdmitedStnt = noCurrentAdmitedStnt;
	}

	@Override
	public String toString() {
		return "GovtScheme [govtScholarId=" + govtScholarId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", acYearId=" + acYearId + ", nameOfScheme=" + nameOfScheme + ", noOfStudBenftd="
				+ noOfStudBenftd + ", noCurrentAdmitedStnt=" + noCurrentAdmitedStnt + ", getGovtScholarId()="
				+ getGovtScholarId() + ", getAcademicYear()=" + getAcademicYear() + ", getInstituteName()="
				+ getInstituteName() + ", getAcYearId()=" + getAcYearId() + ", getNameOfScheme()=" + getNameOfScheme()
				+ ", getNoOfStudBenftd()=" + getNoOfStudBenftd() + ", getNoCurrentAdmitedStnt()="
				+ getNoCurrentAdmitedStnt() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
	

}
