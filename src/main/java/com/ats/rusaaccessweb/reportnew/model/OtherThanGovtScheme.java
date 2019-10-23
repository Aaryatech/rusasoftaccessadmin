package com.ats.rusaaccessweb.reportnew.model;

 
public class OtherThanGovtScheme {
	
 	private int instSchemeId;
	
	private String instSchemeName;
	
	private float instStudentsBenefited;
	
	private String instSchmeOfferedby;
	
	private int yearId;
	
	private String academicYear;
	
	private String instituteName;
	
	private float noCurrentAdmitedStnt;

	public int getInstSchemeId() {
		return instSchemeId;
	}

	public void setInstSchemeId(int instSchemeId) {
		this.instSchemeId = instSchemeId;
	}

	public String getInstSchemeName() {
		return instSchemeName;
	}

	public void setInstSchemeName(String instSchemeName) {
		this.instSchemeName = instSchemeName;
	}

	public float getInstStudentsBenefited() {
		return instStudentsBenefited;
	}

	public void setInstStudentsBenefited(float instStudentsBenefited) {
		this.instStudentsBenefited = instStudentsBenefited;
	}

	public String getInstSchmeOfferedby() {
		return instSchmeOfferedby;
	}

	public void setInstSchmeOfferedby(String instSchmeOfferedby) {
		this.instSchmeOfferedby = instSchmeOfferedby;
	}

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

	public float getNoCurrentAdmitedStnt() {
		return noCurrentAdmitedStnt;
	}

	public void setNoCurrentAdmitedStnt(float noCurrentAdmitedStnt) {
		this.noCurrentAdmitedStnt = noCurrentAdmitedStnt;
	}

	@Override
	public String toString() {
		return "OtherThanGovtScheme [instSchemeId=" + instSchemeId + ", instSchemeName=" + instSchemeName
				+ ", instStudentsBenefited=" + instStudentsBenefited + ", instSchmeOfferedby=" + instSchmeOfferedby
				+ ", yearId=" + yearId + ", academicYear=" + academicYear + ", instituteName=" + instituteName
				+ ", noCurrentAdmitedStnt=" + noCurrentAdmitedStnt + ", getInstSchemeId()=" + getInstSchemeId()
				+ ", getInstSchemeName()=" + getInstSchemeName() + ", getInstStudentsBenefited()="
				+ getInstStudentsBenefited() + ", getInstSchmeOfferedby()=" + getInstSchmeOfferedby() + ", getYearId()="
				+ getYearId() + ", getAcademicYear()=" + getAcademicYear() + ", getInstituteName()="
				+ getInstituteName() + ", getNoCurrentAdmitedStnt()=" + getNoCurrentAdmitedStnt() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
