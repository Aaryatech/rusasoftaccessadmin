package com.ats.rusaaccessweb.reportnew.model;

public class FildeProjectInternReport {
	private int fieldProjectInternId;
	private String programType;
	private String provisionForUndertaking;
	private int noOfStudUndertaking;
	private String document;
	private String instituteName;
	private String academicYear;
	
	public int getFieldProjectInternId() {
		return fieldProjectInternId;
	}
	public void setFieldProjectInternId(int fieldProjectInternId) {
		this.fieldProjectInternId = fieldProjectInternId;
	}
	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public String getProvisionForUndertaking() {
		return provisionForUndertaking;
	}
	public void setProvisionForUndertaking(String provisionForUndertaking) {
		this.provisionForUndertaking = provisionForUndertaking;
	}
	public int getNoOfStudUndertaking() {
		return noOfStudUndertaking;
	}
	public void setNoOfStudUndertaking(int noOfStudUndertaking) {
		this.noOfStudUndertaking = noOfStudUndertaking;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	
	public String getInstituteName() {
		return instituteName;
	}
	public String getAcademicYear() {
		return academicYear;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	@Override
	public String toString() {
		return "FildeProjectInternReport [fieldProjectInternId=" + fieldProjectInternId + ", programType=" + programType
				+ ", provisionForUndertaking=" + provisionForUndertaking + ", noOfStudUndertaking="
				+ noOfStudUndertaking + ", document=" + document + ", instituteName=" + instituteName
				+ ", academicYear=" + academicYear + "]";
	}
	
}
