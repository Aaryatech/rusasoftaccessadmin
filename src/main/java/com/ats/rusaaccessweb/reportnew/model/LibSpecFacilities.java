package com.ats.rusaaccessweb.reportnew.model;

 
public class LibSpecFacilities {
	
 	private int yesnoId;
	
	private String academicYear;

	private String instituteName;
	
	private int yesnoValue;
	
	private String yesnoTitle;
	
	private String instYesnoResponse;

	public int getYesnoId() {
		return yesnoId;
	}

	public void setYesnoId(int yesnoId) {
		this.yesnoId = yesnoId;
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

	public int getYesnoValue() {
		return yesnoValue;
	}

	public void setYesnoValue(int yesnoValue) {
		this.yesnoValue = yesnoValue;
	}

	public String getYesnoTitle() {
		return yesnoTitle;
	}

	public void setYesnoTitle(String yesnoTitle) {
		this.yesnoTitle = yesnoTitle;
	}

	public String getInstYesnoResponse() {
		return instYesnoResponse;
	}

	public void setInstYesnoResponse(String instYesnoResponse) {
		this.instYesnoResponse = instYesnoResponse;
	}

	@Override
	public String toString() {
		return "LibSpecFacilities [yesnoId=" + yesnoId + ", academicYear=" + academicYear + ", instituteName="
				+ instituteName + ", yesnoValue=" + yesnoValue + ", yesnoTitle=" + yesnoTitle + ", instYesnoResponse="
				+ instYesnoResponse + ", getYesnoId()=" + getYesnoId() + ", getAcademicYear()=" + getAcademicYear()
				+ ", getInstituteName()=" + getInstituteName() + ", getYesnoValue()=" + getYesnoValue()
				+ ", getYesnoTitle()=" + getYesnoTitle() + ", getInstYesnoResponse()=" + getInstYesnoResponse()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	

}
